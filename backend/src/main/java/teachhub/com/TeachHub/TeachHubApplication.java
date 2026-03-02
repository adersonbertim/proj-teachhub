package teachhub.com.TeachHub;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeachHubApplication {

	public static void main(String[] args) {
		// 1. Carrega as variáveis do arquivo .env
		Dotenv dotenv = Dotenv.configure()
				.directory("./") // Procura na raiz do projeto
				.ignoreIfMissing()
				.load();

		// 2. Injeta cada variável do .env no sistema para o Spring encontrar
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		// 3. Agora sim, inicia o Spring
		SpringApplication.run(TeachHubApplication.class, args);
	}
}