package teachhub.com.TeachHub.model.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author adersonbertim
 * @since 27/12/2025
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_funcao", nullable = false)
    private String nomeFuncao;

    @Column(name = "nivel_acesso", nullable = false)
    private int nivelAcesso;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private boolean ativo;

    public String getAuthority(){
        return "ROLE_" + this.nomeFuncao.toUpperCase();
    }
}
