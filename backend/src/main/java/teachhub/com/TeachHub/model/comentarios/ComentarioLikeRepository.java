package teachhub.com.TeachHub.model.comentarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ComentarioLikeRepository extends JpaRepository<ComentarioLike, Long> {
    Optional<ComentarioLike> findByUsuarioAndComentario(Usuario usuario, Comentarios comentario);

    long countByComentario(Comentarios comentario);

    @Query("""
        SELECT cl.comentario.id_comentarios,
               COUNT(cl.id)
        FROM ComentarioLike cl
        WHERE cl.comentario.id_comentarios IN :ids
        GROUP BY cl.comentario.id_comentarios
    """)
    List<Object[]> contarLikesPorComentarios(
            @Param("ids") Collection<Long> ids
    );

    @Query("""
        SELECT cl.comentario.id_comentarios
        FROM ComentarioLike cl
        WHERE cl.usuario = :usuario
          AND cl.comentario.id_comentarios IN :ids
    """)
    List<Long> buscarComentariosCurtidos(
            @Param("usuario") Usuario usuario,
            @Param("ids") Collection<Long> ids
    );

}