package teachhub.com.TeachHub.model.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query("SELECT r FROM Roles r WHERE r.nomeFuncao = :nome")
    Optional<Roles> findByNomeFuncao(@Param("nome") String nomeFuncao);
}