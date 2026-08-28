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

@Service
public class ArquivoService {

    // Cai dentro do /uploads/** que o WebConfig já serve como recurso estático
    private static final String PASTA_AVATARES = "uploads/avatars";

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private final UsuarioRepository usuarioRepository;

    public ArquivoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String salvarImagemPerfil(Long usuarioId, MultipartFile arquivo, String imagemAntiga) {
        validarArquivo(arquivo);

        try {
            byte[] bytes = arquivo.getBytes();
            String hash = calcularHash(bytes);
            String extensao = extrairExtensao(arquivo.getOriginalFilename());
            String nomeArquivo = hash + extensao;

            Path pastaDestino = Paths.get(PASTA_AVATARES);
            Files.createDirectories(pastaDestino);
            Path destino = pastaDestino.resolve(nomeArquivo);

            // Conteúdo idêntico gera o mesmo hash — se o arquivo já existe, nem precisa regravar
            if (!Files.exists(destino)) {
                Files.write(destino, bytes);
            }

            String urlNova = baseUrl + "/uploads/avatars/" + nomeArquivo;
            removerImagemAntigaSeOrfa(imagemAntiga, urlNova, pastaDestino);

            return urlNova;
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
            // SHA-256 sempre existe na JVM padrão — isso nunca deveria disparar de verdade
            throw new RuntimeException("Erro ao calcular hash da imagem", e);
        }
    }

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new RuntimeException("Nenhum arquivo enviado");
        }
        String contentType = arquivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("O arquivo precisa ser uma imagem");
        }
    }

    // Como o nome do arquivo agora é o hash do conteúdo, duas pessoas com a mesma
    // foto compartilham o mesmo arquivo em disco. Antes de apagar a foto antiga,
    // confere se mais alguém ainda está usando ela — senão quebraria a foto de outro usuário.
    private void removerImagemAntigaSeOrfa(String urlAntiga, String urlNova, Path pastaDestino) {
        if (urlAntiga == null || urlAntiga.equals(urlNova) || !urlAntiga.contains("/uploads/avatars/")) {
            return;
        }
        long aindaEmUso = usuarioRepository.countByImagemPerfil(urlAntiga);
        if (aindaEmUso > 0) {
            return; // outro usuário ainda usa essa mesma foto — não apaga
        }
        try {
            String nomeAntigo = urlAntiga.substring(urlAntiga.lastIndexOf('/') + 1);
            Files.deleteIfExists(pastaDestino.resolve(nomeAntigo));
        } catch (IOException ignored) {
            // não conseguir apagar o arquivo antigo não é motivo pra falhar o upload novo
        }
    }

    private String extrairExtensao(String nomeOriginal) {
        if (!StringUtils.hasText(nomeOriginal) || !nomeOriginal.contains(".")) {
            return "";
        }
        return nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
    }
}