package teachhub.com.TeachHub.model.inscricoes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscricoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int progresso;

    @Column(nullable = false)
    private LocalDateTime data_inicio;

    @Column(nullable = false)
    private LocalDateTime data_fim;

    @ManyToOne
    @JoinColumn (name = "user_idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn (name = "curso_idcurso", nullable = false)
    private Curso curso;
}
