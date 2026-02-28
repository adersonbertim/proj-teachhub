package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.notificacao.Notificacao;
import teachhub.com.TeachHub.model.notificacao.NotificacaoRepository;

import java.time.LocalDateTime;

@Service
public class NotificacaoService extends AService<Notificacao, NotificacaoRepository> {
    public NotificacaoService(NotificacaoRepository repository) {
        super(repository);
    }
    @Override
    public Notificacao salvar(Notificacao entity) {
        if (entity.getId() == null) {
            // Se a entidade Notificacao tiver o campo chamado 'data'
            entity.setData(LocalDateTime.now());
        }
        return super.salvar(entity);
    }
}
