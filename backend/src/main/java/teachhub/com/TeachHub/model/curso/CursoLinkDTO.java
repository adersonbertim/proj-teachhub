package teachhub.com.TeachHub.model.curso;

import jakarta.validation.constraints.NotBlank;

public record CursoLinkDTO(
        @NotBlank String link,
        @NotBlank String categoria
) {
}
