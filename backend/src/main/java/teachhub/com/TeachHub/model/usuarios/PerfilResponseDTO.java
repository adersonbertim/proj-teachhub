package teachhub.com.TeachHub.model.usuarios;

import teachhub.com.TeachHub.model.postagem.PostagemDTO;

import java.util.List;

public record PerfilResponseDTO(
        Long id,
        String nome,
        String descricao,
        String imagemPerfil,
        String visibilidade,
        RedesSociais redesSociais,
        List<PostagemDTO> postagens,
        boolean souDono
) {
    public static PerfilResponseDTO fromEntity(Usuario usuario, List<PostagemDTO> postagens, boolean souDono) {
        return new PerfilResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getDescricao(),
                usuario.getImagemPerfil(),
                usuario.getVisibilidade(),
                usuario.getRedesSociais(),
                postagens,
                souDono
        );
    }
}