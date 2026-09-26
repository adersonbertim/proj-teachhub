package teachhub.com.TeachHub.model.postagem;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.List;

public interface PostagemRepository extends JpaRepository<Postagem,Long> {

    List<Postagem> findByAutor(Usuario autor);
    void deleteByAutor(Usuario autor);

    List<Postagem> findByCategoriaOrMateria(String categoria, String materia);
}


