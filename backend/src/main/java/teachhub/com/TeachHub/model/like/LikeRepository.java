package teachhub.com.TeachHub.model.like;

import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // Verifica se um usuário específico já curtiu essa postagem
    Optional<Like> findByUsuarioAndPostagem(Usuario usuario, Postagem postagem);

    // Conta o total de likes de uma postagem
    long countByPostagem(Postagem postagem);
}