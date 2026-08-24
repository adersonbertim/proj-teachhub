package teachhub.com.TeachHub.model.Perfil;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column
    private String imagemPerfil;

    @Column(nullable = false)
    private String visibilidadePerfil;

    @Column(nullable = false)
    private String redesSociais;

}
