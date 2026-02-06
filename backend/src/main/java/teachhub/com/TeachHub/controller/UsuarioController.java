package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.model.usuarios.Usuario;
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
        return UsuarioDTO.usuario(entity);
    }


}
