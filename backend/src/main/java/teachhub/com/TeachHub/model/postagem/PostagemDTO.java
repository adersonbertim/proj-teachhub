package teachhub.com.TeachHub.model.postagem;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teachhub.com.TeachHub.model.tag.Tag;
import teachhub.com.TeachHub.model.usuarios.UsuarioDTO;

import java.util.List;


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
    private List<String> tags;
    public boolean visibilidade;
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
                .autor(postagem.getAutor().getNome()) // ou getEmail()
                .idPostagem(postagem.getId())
                .titulo(postagem.getTitulo())
                .descricao(postagem.getTexto())
                .categoria(postagem.getCategoria())
                .tags(postagem.getTags().stream()
                        .map(Tag::getNome)
                        .sorted()
                        .toList())
                .visibilidade(postagem.getVisibilidade())
                .planoAula(postagem.getConteudo())
                .planoAula(postagem.getTarefa())
                .nota(postagem.getNota())
                .materia(postagem.getMateria())
                .resumo(postagem.getResumo())
                .usuarioDTO(UsuarioDTO.fromEntity(postagem.getAutor()))
                .build();
    }

    public Boolean visibilidade() {
        return this.visibilidade;
    }

    public record PostagemRequestDTO(
            @NotBlank String titulo,
            @NotBlank String texto,
            @NotBlank String categoria,
            @NotBlank String tags
    ) {}
}
