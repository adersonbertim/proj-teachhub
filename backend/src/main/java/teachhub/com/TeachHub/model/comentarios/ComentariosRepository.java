package teachhub.com.TeachHub.model.comentarios;

import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;


import java.util.List;

public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
    // Comentários raiz (sem pai) de uma postagem
    List<Comentarios> findByPostagemAndComentarioPaiIsNullOrderByDataDesc(Postagem postagem);

    // Respostas de um comentário específico
    List<Comentarios> findByComentarioPaiOrderByDataAsc(Comentarios comentarioPai);
}
