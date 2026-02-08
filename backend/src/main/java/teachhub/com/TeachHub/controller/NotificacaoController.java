package teachhub.com.TeachHub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.model.notificacao.Notificacao;
import teachhub.com.TeachHub.model.notificacao.NotificacaoDTO;
import teachhub.com.TeachHub.service.NotificacaoService;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController extends AController<Notificacao, NotificacaoDTO, Long, NotificacaoService> {
    public NotificacaoController(NotificacaoService service) {
        super(service);
    }

    @Override
    protected NotificacaoDTO toDTO(Notificacao entity) {
        return NotificacaoDTO.fromEntity(entity);
    }
}
