package teachhub.com.TeachHub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teachhub.com.TeachHub.model.roles.Roles;
import teachhub.com.TeachHub.model.roles.RolesRepository;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

import java.util.Arrays;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(RolesRepository rolesRepository, UsuarioRepository usuarioRepository,
                                   @Value("${ADMIN_EMAILS:}") String adminEmails) {
        return args -> {
            criarRoleSeNaoExistir(rolesRepository, "professor", 1, "Professor da plataforma");
            Roles admin = criarRoleSeNaoExistir(rolesRepository, "administrador", 2, "Gerencia a plataforma e a curadoria de cursos");

            // promove a administrador os e-mails listados no .env (separados por vírgula)
            Arrays.stream(adminEmails.split(","))
                    .map(String::trim)
                    .filter(email -> !email.isEmpty())
                    .forEach(email -> usuarioRepository.findByEmail(email).ifPresent(usuario -> {
                        if (!usuario.getRole().getId().equals(admin.getId())) {
                            usuario.setRole(admin);
                            usuarioRepository.save(usuario);
                        }
                    }));
        };
    }

    private Roles criarRoleSeNaoExistir(RolesRepository repository, String nome, int nivel, String descricao) {
        return repository.findByNomeFuncao(nome).orElseGet(() -> {
            Roles role = new Roles();
            role.setNomeFuncao(nome);
            role.setNivelAcesso(nivel);
            role.setDescricao(descricao);
            role.setAtivo(true);
            return repository.save(role);
        });
    }
}
