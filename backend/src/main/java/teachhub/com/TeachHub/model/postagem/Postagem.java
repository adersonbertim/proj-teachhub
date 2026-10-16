package teachhub.com.TeachHub.model.postagem;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.midia.Midia;
import teachhub.com.TeachHub.model.tag.Tag;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    @Column(nullable = false, columnDefinition = "text")
    private String texto;

    @Column(nullable = false)
    private String resumo;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "postagem_tag",
            joinColumns = @JoinColumn(name = "postagem_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"postagem_id", "tag_id"})
    )
    private Set<Tag> tags = new HashSet<>();

    @Column
    private Boolean visibilidade = true;

    @Column
    private Boolean conteudo = false;

    @Column
    private Boolean tarefa  = false;

    @Column
    private int nota;

    @Column
    private String materia;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_idusuario", nullable = false)
    private Usuario autor;

    @OneToMany(mappedBy = "postagem", fetch = FetchType.LAZY)
    private List<Midia> midias;

    private int likes;

    private int dislikes;
}
