package teachhub.com.TeachHub.model.comentarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentariosDTO {
    private Long id;
    private String texto;
    private LocalDateTime data;
    private String nomeUsuario;
    private Long idPostagem;
    private String fotoUsuario;
    private Long idComentarioPai;
    private long qtdLikes;
    private boolean curtidoPeloUsuario;

    @Builder.Default
    private List<ComentariosDTO> respostas = new ArrayList<>();

    public static ComentariosDTO fromEntity(Comentarios comentario) {
        if (comentario == null) {
            return null;
        }
        return ComentariosDTO.builder()
                .id(comentario.getId_comentarios())
                .texto(comentario.getTexto())
                .data(comentario.getData())
                .nomeUsuario(comentario.getUsuario() != null ? comentario.getUsuario().getNome() : null)
                .idPostagem(comentario.getPostagem() != null ? comentario.getPostagem().getId() : null)
                .build();
    }

    //Mesmo padrao do favoritos pq funcionou, ainda nao mexer
    public record ComentarioRequestDTO(String texto) {}
}
