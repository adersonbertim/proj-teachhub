package teachhub.com.TeachHub.model.comentarios;

import jakarta.persistence.*;
import lombok.*;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comentarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comentarios;

    @Column (nullable = false)
    private String titulo;

    @Column (nullable = false)
    private String texto;

    @Column
    private int avaliacao;

    @Column
    private int likes;

    @Column (nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn (name = "user_idusuario", nullable = false)
    private Usuario usuario;
}
