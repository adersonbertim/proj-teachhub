package teachhub.com.TeachHub.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.core.UsuarioRegistroDTO;
import teachhub.com.TeachHub.model.roles.RolesRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UsuarioService extends AService <Usuario, UsuarioRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final RolesRepository rolesRepository;

    public UsuarioService(UsuarioRepository repository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }
//
//    @Override
//    public Usuario salvar(Usuario novaEntidade) {
//        if (novaEntidade.getRoles() == null) {

//    }

    public Usuario registrar(UsuarioRegistroDTO dto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());

        String criptografia = passwordEncoder.encode(dto.senha());
        novoUsuario.setSenha(criptografia);

        rolesRepository.findById(2L).ifPresentOrElse(
                novoUsuario::setRoles,
                () ->{ throw new RuntimeException("ERRO: Role padrão não encontrada!");}
        );

        novoUsuario.setScore(0);
        novoUsuario.setDataCadastro(LocalDateTime.now());

        return repository.save(novoUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado!"));
    }

    public Usuario buscarPorId(Long id) {
        return  repository.findById(id).orElse(null);
    }
}
