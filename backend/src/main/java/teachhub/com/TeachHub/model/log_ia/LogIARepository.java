package teachhub.com.TeachHub.model.log_ia;

import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.List;

public interface LogIARepository extends JpaRepository<LogIA, Long> {

    List<LogIA> findByUserOrderByDataDesc(Usuario usuario);
}
