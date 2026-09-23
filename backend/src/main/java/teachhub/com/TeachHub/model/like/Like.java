package teachhub.com.TeachHub.model.like;

import jakarta.persistence.*;
import lombok.*;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "user_idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "postagem_idpostagem", nullable = false)
    private Postagem postagem;
}