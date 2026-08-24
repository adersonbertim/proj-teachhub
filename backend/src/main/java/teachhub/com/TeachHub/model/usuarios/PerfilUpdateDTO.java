package teachhub.com.TeachHub.model.usuarios;

public record PerfilUpdateDTO(
        String descricao,
        String imagemPerfil,
        String visibilidade,
        RedesSociais redesSociais
) {}