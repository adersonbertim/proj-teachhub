package teachhub.com.TeachHub.model.comentarios;

import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.Optional;

public interface ComentarioLikeRepository extends JpaRepository<ComentarioLike, Long> {
    Optional<ComentarioLike> findByUsuarioAndComentario(Usuario usuario, Comentarios comentario);
    long countByComentario(Comentarios comentario);
}