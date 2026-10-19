package teachhub.com.TeachHub.controller;

import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioDTO;
import teachhub.com.TeachHub.service.UsuarioService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends AController <Usuario, UsuarioDTO, Long, UsuarioService>{

    public UsuarioController(UsuarioService service) {
        super(service);
    }

    protected UsuarioDTO toDTO(Usuario entity) {
        return UsuarioDTO.fromEntity(entity);
    }
}