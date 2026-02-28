package teachhub.com.TeachHub.core;

import jakarta.validation.constraints.NotEmpty;
import teachhub.com.TeachHub.model.usuarios.Usuario;

public record UsuarioLoginDTO(
        @NotEmpty String email,
        @NotEmpty String senha
){

}


