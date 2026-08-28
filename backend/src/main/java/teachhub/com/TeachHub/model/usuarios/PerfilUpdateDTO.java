package teachhub.com.TeachHub.model.usuarios;

public record PerfilUpdateDTO(
        String descricao,
        String visibilidade,
        RedesSociais redesSociais
) {}