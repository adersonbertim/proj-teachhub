package teachhub.com.TeachHub.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.model.roles.Roles;
import teachhub.com.TeachHub.model.roles.RolesRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GoogleAuthService {

    private final GoogleIdTokenVerifier verifier;
    private final UsuarioRepository usuarioRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public GoogleAuthService(UsuarioRepository usuarioRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String clientId = dotenv.get("GOOGLE_CLIENT_ID", null);

        if (clientId == null || clientId.isBlank()) {
            throw new IllegalStateException("GOOGLE_CLIENT_ID não configurado, conferir a .env");
        }

        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(List.of(clientId))
                .build();
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario autenticar(String credential) {
        GoogleIdToken.Payload payload = validarToken(credential);

        if (!Boolean.TRUE.equals(payload.getEmailVerified())) {
            throw new RuntimeException("E-mail do Google não verificado");
        }

        return usuarioRepository.findByEmail(payload.getEmail())
                .orElseGet(() -> cadastrar(payload));
    }

    private GoogleIdToken.Payload validarToken(String credential) {
        try {
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken == null) {
                throw new RuntimeException("Token do Google inválido");
            }
            return idToken.getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível validar o login com Google", e);
        }
    }

    private Usuario cadastrar(GoogleIdToken.Payload payload) {
        Usuario usuario = new Usuario();
        usuario.setNome((String) payload.get("name"));
        usuario.setEmail(payload.getEmail());
        usuario.setImagemPerfil((String) payload.get("picture"));
        usuario.setScore(0);
        usuario.setDataCadastro(LocalDateTime.now());

        usuario.setSenha(passwordEncoder.encode(UUID.randomUUID().toString()));

        Roles roleProfessor = rolesRepository.findByNomeFuncao("professor")
                .orElseThrow(() -> new RuntimeException("Role não encontrado"));
        usuario.setRole(roleProfessor);

        return usuarioRepository.save(usuario);
    }
}