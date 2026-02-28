package teachhub.com.TeachHub.model.usuarios;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroDTO (
        @NotEmpty(message = "Este campo não pode estar vazio!")
        String nome,

        @NotEmpty(message = "Este campo não pode estar vazio!")
        @Email(message = "Formato de e-mail invalido!")
        String email,

        @NotEmpty(message = "Este campo não pode estar vazio!")
        @Size(message = "Este campo deve ter no minimo 8 caracteres!")
        String senha
){

}
