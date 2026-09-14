package teachhub.com.TeachHub.model.comentarios;

import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;


import java.util.List;

public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
    List<Comentarios> findByPostagem(Postagem postagem);
}
