package teachhub.com.TeachHub.model.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de resposta simples: não precisa expor a entidade Like inteira,
// o front-end só precisa saber a contagem e se o usuário logado já curtiu
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeStatusDTO {
    private long qtdLikes;
    private boolean curtidoPeloUsuario;
}