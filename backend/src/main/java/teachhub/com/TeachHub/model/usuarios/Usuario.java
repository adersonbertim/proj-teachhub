package teachhub.com.TeachHub.model.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import teachhub.com.TeachHub.model.favorito.Favorito;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.roles.Roles;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
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
public class Usuario implements UserDetails {
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
    private LocalDateTime dataCadastro;

    @Column
    private String telefone;

    @Column
    private String areaEnsino;

    @Column
    private Integer score;

    @ManyToOne
    @JoinColumn (name = "roles_idrole", nullable = false)
    private Roles roles;

    @OneToMany
    private List<Postagem> postagensCriadas;

    @OneToMany (mappedBy = "usuario")
    private List<Favorito> favoritos;

    public Collection<? extends GrantedAuthority>  getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return this.senha;
    }
    public String getUsername() {
        return this.email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }
    public boolean isAccountNonLocked() {
        return true;
    }
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public boolean isEnabled() {
        return true;
    }
}
