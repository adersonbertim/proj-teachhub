package teachhub.com.TeachHub.model.curso;

public record ResultadoYoutubeDTO(
        String idExterno,
        String tipo,
        String titulo,
        String descricao,
        String imagem,
        String canal,
        boolean jaAdicionado
) {
    public ResultadoYoutubeDTO comJaAdicionado(boolean valor) {
        return new ResultadoYoutubeDTO(idExterno, tipo, titulo, descricao, imagem, canal, valor);
    }
}
