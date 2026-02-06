package teachhub.com.TeachHub.model.notificacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {
    private Long idNotificacao;
    private String tipo;
    private String mensagem;

    public static NotificacaoDTO fromEntity(Notificacao notificacao) {
        if (notificacao == null) {
            return null;
        }
        return NotificacaoDTO.builder()
                .idNotificacao(notificacao.getId())
                .tipo(notificacao.getTipo())
                .mensagem(notificacao.getMensagem())
                .build();
    }

}
