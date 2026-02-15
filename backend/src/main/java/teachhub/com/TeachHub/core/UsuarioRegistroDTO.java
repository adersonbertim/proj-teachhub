package teachhub.com.TeachHub.core;

import jakarta.validation.constraints.NotEmpty;

public record UsuarioRegistroDTO (
    @NotEmpty String nome,
    @NotEmpty String email,
    @NotEmpty String senha
){}
