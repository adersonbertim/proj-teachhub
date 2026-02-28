package teachhub.com.TeachHub.model.midia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.postagem.Postagem;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Midia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String caminho;

    @Column
    private Integer lim_tamanho;

    @ManyToOne
    @JoinColumn (name = "postagem_idpostagem", nullable = false)
    private Postagem postagem;
}
