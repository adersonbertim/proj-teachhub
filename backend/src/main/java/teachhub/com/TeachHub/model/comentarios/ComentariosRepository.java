package teachhub.com.TeachHub.model.comentarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;


import java.util.Collection;
import java.util.List;

public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {

    // EntityGraph soluciona o problema do N + 1, por isso deve ser usado nesse caso, ja que seria recursivo
    //Isso reduz a chance de uma consulta extra pra cada usuario
    @EntityGraph(attributePaths = "usuario")
    Page<Comentarios> findByPostagemAndComentarioPaiIsNullOrderByDataDesc(Postagem postagem, Pageable pageable);

    @EntityGraph(attributePaths = "usuario")
    List<Comentarios> findByPostagemAndComentarioPaiIsNullOrderByDataDesc(
            Postagem postagem
    );

    // Respostas de um comentário específico
    @EntityGraph(attributePaths = "usuario")
    List<Comentarios> findByComentarioPaiOrderByDataAsc(Comentarios comentarioPai);

    @EntityGraph()
    List<Comentarios> findByComentarioPaiInOrderByDataAsc(
            Collection<Comentarios> comentarioPai
    );



}
