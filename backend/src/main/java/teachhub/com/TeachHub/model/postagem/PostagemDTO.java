package teachhub.com.TeachHub.model.postagem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teachhub.com.TeachHub.model.curso.Curso;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostagemDTO {
    private Long idPostagem;
    private String titulo;
    private String descricao;
    private String categoria;


    public static PostagemDTO fromEntity(Postagem postagem) {
        if (postagem == null) {
            return null;
        }
        return PostagemDTO.builder()
                .idPostagem(postagem.getId())
                .titulo(postagem.getTitulo())
                .descricao(postagem.getDescricao())
                .categoria(postagem.getCategoria())
                .build();
    }
}