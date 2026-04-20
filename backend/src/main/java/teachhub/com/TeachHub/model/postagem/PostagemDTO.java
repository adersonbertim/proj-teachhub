package teachhub.com.TeachHub.model.postagem;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teachhub.com.TeachHub.model.usuarios.UsuarioDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostagemDTO {
    private Long idPostagem;
    private String titulo;
    private String descricao;
    private String categoria;
    private String autor;
    private String tag;
    private boolean visibilidade;
    private boolean planoAula;
    private int nota;
    private int likes;
    private int deslikes;
    private String materia;
    private String resumo;


    private UsuarioDTO usuarioDTO;


    public static PostagemDTO fromEntity(Postagem postagem) {
        if (postagem == null) {
            return null;
        }
        return PostagemDTO.builder()
                .idPostagem(postagem.getId())
                .titulo(postagem.getTitulo())
                .descricao(postagem.getDescricao())
                .categoria(postagem.getCategoria())
                .tag(postagem.getTag())
                .visibilidade(postagem.getVisibilidade())
                .planoAula(postagem.isPlanoAula())
                .nota(postagem.getNota())
                .likes(postagem.getLikes())
                .deslikes(postagem.getDislikes())
                .materia(postagem.getMateria())
                .resumo(postagem.getResumo())
                .usuarioDTO(UsuarioDTO.fromEntity(postagem.getAutor()))
                .build();
    }
    public record PostagemRequestDTO(
            @NotBlank String titulo,
            @NotBlank String descricao,
            @NotBlank String categoria,
            @NotBlank String usuario,
            String tag
    ) {}
}