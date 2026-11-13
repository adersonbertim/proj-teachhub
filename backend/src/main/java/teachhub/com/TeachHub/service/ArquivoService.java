package teachhub.com.TeachHub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ArquivoService {

    private static final String PASTA_POSTAGENS = "uploads/postagens";
    private static final String PASTA_AVATARES = "uploads/avatars";

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private final UsuarioRepository usuarioRepository;

    public ArquivoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String salvarImagemPerfil(Long usuarioId, MultipartFile arquivo, String imagemAntiga) {
        String nomeArquivo = salvarComHash(arquivo, PASTA_AVATARES);
        String urlNova = baseUrl + "/uploads/avatars/" + nomeArquivo;
        removerImagemAntigaSeOrfa(imagemAntiga, urlNova, Paths.get(PASTA_AVATARES));
        return urlNova;
    }

    public String salvarImagemPostagem(MultipartFile arquivo) {
        String nomeArquivo = salvarComHash(arquivo, PASTA_POSTAGENS);
        return baseUrl + "/uploads/postagens/" + nomeArquivo;
    }

    private String salvarComHash(MultipartFile arquivo, String pasta) {
        validarArquivo(arquivo);

        try {
            byte[] bytes = arquivo.getBytes();
            String nomeArquivo = calcularHash(bytes) + extensaoParaTipo(arquivo.getContentType());

            Path pastaDestino = Paths.get(pasta);
            Files.createDirectories(pastaDestino);
            Path destino = pastaDestino.resolve(nomeArquivo);

            if (!Files.exists(destino)) {
                Files.write(destino, bytes);
            }
            return nomeArquivo;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage(), e);
        }
    }

    private String calcularHash(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao calcular hash da imagem", e);
        }
    }


    private static final List<String> TIPOS_PERMITIDOS = List.of("image/jpeg", "image/png", "image/webp");

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Nenhum arquivo enviado");
        }
        String contentType = arquivo.getContentType();
        if (contentType == null || !TIPOS_PERMITIDOS.contains(contentType)) {
            throw new IllegalArgumentException("Envie uma imagem JPG, PNG ou WEBP");
        }
        if (arquivo.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("A imagem precisa ter no máximo 5MB");
        }
    }

    private String extensaoParaTipo(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }

    private void removerImagemAntigaSeOrfa(String urlAntiga, String urlNova, Path pastaDestino) {
        if (urlAntiga == null || urlAntiga.equals(urlNova) || !urlAntiga.contains("/uploads/avatars/")) {
            return;
        }
        long aindaEmUso = usuarioRepository.countByImagemPerfil(urlAntiga);
        if (aindaEmUso > 0) {
            return;
        }
        try {
            String nomeAntigo = urlAntiga.substring(urlAntiga.lastIndexOf('/') + 1);
            Files.deleteIfExists(pastaDestino.resolve(nomeAntigo));
        } catch (IOException ignored) {
        }
    }

}
