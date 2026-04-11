package teachhub.com.TeachHub.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.core.UsuarioRegistroDTO;
import teachhub.com.TeachHub.model.roles.Roles;
import teachhub.com.TeachHub.model.roles.RolesRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

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

        // 1. Mapeia dados básicos
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setScore(0);
        novoUsuario.setDataCadastro(LocalDateTime.now());

        // 2. Criptografa a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        novoUsuario.setSenha(senhaCriptografada);

        // 3. Define a Role baseada no que o usuário escolheu no Frontend
        // Aqui assumimos que no seu banco existe uma Role com o nome "ALUNO" ou "PROFESSOR"
// No UsuarioService.java, dentro do register
        Roles roleEncontrada = rolesRepository.findByNomeFuncao(dto.role().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Tipo não encontrada: " + dto.role()));

        novoUsuario.setRoles(roleEncontrada);

        // 4. Salva de fato no banco de dados
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
