package teachhub.com.TeachHub.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import teachhub.com.TeachHub.model.curso.ResultadoYoutubeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YoutubeService {

    public static final String PLAYLIST = "PLAYLIST";
    public static final String VIDEO = "VIDEO";

    private static final Pattern PADRAO_PLAYLIST = Pattern.compile("[?&]list=([\\w-]+)");
    private static final Pattern PADRAO_VIDEO = Pattern.compile("(?:v=|youtu\\.be/|/embed/|/shorts/)([\\w-]{11})");

    private final RestClient restClient = RestClient.create("https://www.googleapis.com/youtube/v3");
    private final String apiKey;

    public YoutubeService(@Value("${YOUTUBE_API_KEY:}") String apiKey) {
        this.apiKey = apiKey;
    }

    public List<ResultadoYoutubeDTO> pesquisar(String termo) {
        JsonNode resposta = restClient.get()
                .uri(uri -> uri.path("/search")
                        .queryParam("part", "snippet")
                        .queryParam("type", "playlist,video")
                        .queryParam("q", termo)
                        .queryParam("relevanceLanguage", "pt")
                        .queryParam("maxResults", 20)
                        .queryParam("key", chave())
                        .build())
                .retrieve()
                .body(JsonNode.class);

        List<ResultadoYoutubeDTO> resultados = new ArrayList<>();
        if (resposta == null) {
            return resultados;
        }

        for (JsonNode item : resposta.path("items")) {
            JsonNode id = item.path("id");
            boolean playlist = id.has("playlistId");
            String idExterno = playlist ? id.path("playlistId").asText() : id.path("videoId").asText();
            resultados.add(montarResultado(idExterno, playlist ? PLAYLIST : VIDEO, item.path("snippet")));
        }
        return resultados;
    }

    // aceita link de playlist ou de vídeo e busca os dados dele no YouTube
    public ResultadoYoutubeDTO buscarPorLink(String link) {
        Optional<String> playlistId = extrair(PADRAO_PLAYLIST, link);
        if (playlistId.isPresent()) {
            return buscarPorId("/playlists", playlistId.get(), PLAYLIST);
        }

        String videoId = extrair(PADRAO_VIDEO, link)
                .orElseThrow(() -> new RuntimeException("Link do YouTube inválido"));
        return buscarPorId("/videos", videoId, VIDEO);
    }

    public static String montarLink(String idExterno, String tipo) {
        return PLAYLIST.equals(tipo)
                ? "https://www.youtube.com/playlist?list=" + idExterno
                : "https://www.youtube.com/watch?v=" + idExterno;
    }

    private ResultadoYoutubeDTO buscarPorId(String recurso, String idExterno, String tipo) {
        JsonNode resposta = restClient.get()
                .uri(uri -> uri.path(recurso)
                        .queryParam("part", "snippet")
                        .queryParam("id", idExterno)
                        .queryParam("key", chave())
                        .build())
                .retrieve()
                .body(JsonNode.class);

        JsonNode item = resposta == null ? null : resposta.path("items").path(0);
        if (item == null || item.isMissingNode()) {
            throw new RuntimeException("Conteúdo não encontrado no YouTube");
        }
        return montarResultado(idExterno, tipo, item.path("snippet"));
    }

    private ResultadoYoutubeDTO montarResultado(String idExterno, String tipo, JsonNode snippet) {
        JsonNode thumbs = snippet.path("thumbnails");
        String imagem = thumbs.path("high").path("url").asText(thumbs.path("medium").path("url").asText(null));

        return new ResultadoYoutubeDTO(
                idExterno,
                tipo,
                snippet.path("title").asText(),
                snippet.path("description").asText(),
                imagem,
                snippet.path("channelTitle").asText(),
                false
        );
    }

    private Optional<String> extrair(Pattern padrao, String texto) {
        Matcher matcher = padrao.matcher(texto);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    private String chave() {
        if (apiKey.isBlank()) {
            throw new IllegalStateException("YOUTUBE_API_KEY não configurada, conferir a .env");
        }
        return apiKey;
    }
}
