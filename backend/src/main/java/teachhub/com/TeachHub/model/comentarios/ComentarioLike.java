package teachhub.com.TeachHub.model.comentarios;

import jakarta.persistence.*;
import lombok.*;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "user_idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "comentario_id", nullable = false)
    private Comentarios comentario;
}