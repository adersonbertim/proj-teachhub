package teachhub.com.TeachHub.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioCadastroDTO;
import teachhub.com.TeachHub.model.usuarios.UsuarioDTO;
import teachhub.com.TeachHub.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends AController <Usuario, UsuarioDTO, Long, UsuarioService>{

    public UsuarioController(UsuarioService service) {
        super(service);
    }

    protected UsuarioDTO toDTO(Usuario entity) {
        return UsuarioDTO.fromEntity(entity);
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrar(@Valid @RequestBody UsuarioCadastroDTO dto) {
        Usuario novoUsuario = new  Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setSenha(dto.senha());

        Usuario usuarioSalvo = service.salvar(novoUsuario);

        UsuarioDTO usuarioDTO = toDTO(usuarioSalvo);

        return ResponseEntity.ok(ApiResponse.success(usuarioDTO));
    }
}
