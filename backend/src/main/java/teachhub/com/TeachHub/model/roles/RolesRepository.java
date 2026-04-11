package teachhub.com.TeachHub.model.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    // Usamos JPQL para apontar direto para o atributo da sua classe
    @Query("SELECT r FROM Roles r WHERE r.nome_funcao = :nome")
    Optional<Roles> findByNomeFuncao(@Param("nome") String nome);
}