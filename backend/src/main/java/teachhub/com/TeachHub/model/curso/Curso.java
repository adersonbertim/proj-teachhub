package teachhub.com.TeachHub.model.curso;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teachhub.com.TeachHub.model.inscricoes.Inscricoes;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String plataforma;

    @Column
    private String categoria;

    @Column
    private Boolean ativo;

    @Column(nullable = false)
    private String link;

    @Column
    private String anexo;

    @OneToMany(mappedBy = "curso")
    private List<Inscricoes>  inscritos;
}
