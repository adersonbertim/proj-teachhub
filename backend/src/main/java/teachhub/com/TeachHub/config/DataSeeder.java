package teachhub.com.TeachHub.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.curso.CursoRepository;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(CursoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Curso(null, "Java Completo", "Udemy", "Programação", true, "https://www.udemy.com/course/java-curso-completo/", null, null));
                repository.save(new Curso(null, "Angular Pro", "Hotmart", "Frontend", true, "https://www.udemy.com/course/java-curso-completo/", null, null));
                repository.save(new Curso(null, "Spring Expert", "Udemy", "Backend", true, "https://www.udemy.com/course/java-curso-completo/", null, null));
                System.out.println("✅ Banco populado com cursos de exemplo!");
            }
        };
    }
}
