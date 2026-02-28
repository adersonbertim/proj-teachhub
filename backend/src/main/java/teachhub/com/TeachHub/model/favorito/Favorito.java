package teachhub.com.TeachHub.model.favorito;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn (name = "user_idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn (name = "postagem_idpostagem", nullable = false)
    private Postagem postagem;
}
