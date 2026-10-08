package teachhub.com.TeachHub.model.curso;

import jakarta.validation.constraints.NotBlank;

public record CursoCadastroDTO(
        @NotBlank String idExterno,
        @NotBlank String tipo,
        @NotBlank String titulo,
        String descricao,
        String imagem,
        String canal,
        @NotBlank String categoria
) {
}
