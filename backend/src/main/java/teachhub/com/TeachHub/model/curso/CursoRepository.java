package teachhub.com.TeachHub.model.curso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    List<Curso> findByAtivoTrueOrderByIdDesc();

    List<Curso> findAllByOrderByIdDesc();

    Optional<Curso> findByIdExterno(String idExterno);
}
