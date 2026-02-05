package teachhub.com.TeachHub.model.postagem;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.midia.Midia;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
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
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column
    private String tag;

    @Column
    private Boolean visibilidade = true;

    @ManyToOne
    @JoinColumn (name = "user_idusuario", nullable = false)
    private Usuario autor;

    @OneToMany(mappedBy = "postagem")
    private List<Midia> midias;


}
