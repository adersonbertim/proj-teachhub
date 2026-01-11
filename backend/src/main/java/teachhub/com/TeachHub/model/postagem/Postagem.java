package teachhub.com.TeachHub.model.postagem;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.midia.Midia;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Date dataCriacao;

    @Column(nullable = false)
    private Date dataAtualizacao;

    @Column
    private String tag;

    @Column
    private boolean visibilidade;

    @ManyToOne
    @JoinColumn (name = "user_idusuario", nullable = false)
    private Usuario autor;

    @OneToMany(mappedBy = "postagem")
    private List<Midia> midias;


}
