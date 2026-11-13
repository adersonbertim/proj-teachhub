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
    private boolean conteudo;
    private boolean tarefa;
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
                .conteudo(Boolean.TRUE.equals(postagem.getConteudo()))
                .tarefa(Boolean.TRUE.equals(postagem.getTarefa()))
                .nota(postagem.getNota())
                .materia(postagem.getMateria())
                .resumo(gerarResumo(postagem.getTexto()))
                .usuarioDTO(UsuarioDTO.fromEntity(postagem.getAutor()))
                .build();
    }

    public Boolean visibilidade() {
        return this.visibilidade;
    }

    public record PostagemRequestDTO(
            @NotBlank(message = "Informe o título") String titulo,
            @NotBlank(message = "Escreva o texto da postagem") String texto,
            @NotBlank(message = "Escolha a categoria") String categoria,
            @NotBlank(message = "Informe de 1 a 10 tags no formato #tag") String tags,
            boolean conteudo,
            boolean tarefa
    ) {}

    private static String gerarResumo(String html) {
        if (html == null) {
            return "";
        }
        String texto = html
                .replaceAll("<[^>]*>", " ")
                .replace("&nbsp;", " ")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&amp;", "&")
                .replaceAll("\\s+", " ")
                .trim();
        return texto.length() > 150 ? texto.substring(0, 147) + "..." : texto;
    }
}
