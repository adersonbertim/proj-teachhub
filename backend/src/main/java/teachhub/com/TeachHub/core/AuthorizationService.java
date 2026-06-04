package teachhub.com.TeachHub.core;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    public AuthorizationService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
