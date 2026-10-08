package teachhub.com.TeachHub.model.curso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long idCurso;
    private String titulo;
    private String plataforma;
    private String categoria;
    private String link;
    private String descricao;
    private String imagem;
    private String canal;
    private String idExterno;
    private Boolean ativo;

    public static CursoDTO fromEntity(Curso curso) {
        if (curso == null) {
            return null;
        }
        return CursoDTO.builder()
                .idCurso(curso.getId())
                .titulo(curso.getTitulo())
                .plataforma(curso.getPlataforma())
                .categoria(curso.getCategoria())
                .link(curso.getLink())
                .descricao(curso.getDescricao())
                .imagem(curso.getImagem())
                .canal(curso.getCanal())
                .idExterno(curso.getIdExterno())
                .ativo(curso.getAtivo())
                .build();
    }
}
