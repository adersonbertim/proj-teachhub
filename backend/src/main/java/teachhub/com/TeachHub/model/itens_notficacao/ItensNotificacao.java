package teachhub.com.TeachHub.model.itens_notficacao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.notificacao.Notificacao;
import teachhub.com.TeachHub.model.usuarios.Usuario;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItensNotificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "user_idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn (name = "notificao_idnotificacao", nullable = false)
    private Notificacao notificacao;
}
