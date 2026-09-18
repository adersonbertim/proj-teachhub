package teachhub.com.TeachHub.model.usuarios;

public record PerfilUpdateDTO(
        String nome,
        String descricao,
        String visibilidade,
        RedesSociais redesSociais
) {}