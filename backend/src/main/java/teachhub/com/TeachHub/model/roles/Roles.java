package teachhub.com.TeachHub.model.roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author adersonbertim
 * @since 27/12/2025
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome_funcao;

    @Column(nullable = false)
    private int nivel_acesso;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private boolean ativo;
}
