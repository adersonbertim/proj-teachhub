package teachhub.com.TeachHub.model.favorito;

import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    Optional<Favorito> findByUsuarioAndPostagem(Usuario usuario, Postagem postagem);

    List<Favorito> findByUsuarioOrderByDataDesc(Usuario usuario);

    boolean existsByUsuarioIdAndPostagemId(Long usuarioId, Long postagemId);

    void deleteByUsuario(Usuario usuario);

    void deleteByPostagem(Postagem postagem);
}
