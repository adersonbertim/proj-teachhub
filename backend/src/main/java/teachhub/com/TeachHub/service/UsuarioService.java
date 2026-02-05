package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.roles.RolesRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;

@Service
public class UsuarioService extends AService <Usuario, UsuarioRepository> {

    private final RolesRepository rolesRepository;

    public UsuarioService(UsuarioRepository repository, RolesRepository rolesRepository) {
        super(repository);
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Usuario salvar(Usuario novaEntidade) {
        if (novaEntidade.getRoles() == null) {
        rolesRepository.findById(2L).ifPresent(novaEntidade::setRoles);
        }
        return repository.save(novaEntidade);
    }

    public Usuario buscarPorId(Long id) {
        return  repository.findById(id).orElse(null);
    }
}
