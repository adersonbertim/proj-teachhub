package teachhub.com.TeachHub.model.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.favorito.Favorito;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.roles.Roles;

import java.time.LocalDate;
import java.util.List;

/**
 * @author adersonbertim
 * @since 27/12/2025
 */


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column
    private LocalDate dataCadastro;

    @Column
    private String telefone;

    @Column
    private String areaEnsino;

    @Column
    private int score;

    @ManyToOne
    @JoinColumn (name = "roles_idrole", nullable = false)
    private Roles roles;

    @OneToMany
    private List<Postagem> postagensCriadas;

    @OneToMany (mappedBy = "usuario")
    private List<Favorito> favoritos;

}
