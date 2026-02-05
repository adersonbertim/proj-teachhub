package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.postagem.PostagemRepository;

import java.time.LocalDateTime;

@Service
public class PostagemService extends AService<Postagem, PostagemRepository> {
    public PostagemService(PostagemRepository repository) {
        super(repository);
    }

    @Override
    public Postagem salvar(Postagem novaPostagem) {
        if (novaPostagem.getId() == null) {
            novaPostagem.setDataCriacao(LocalDateTime.now());
        }
        novaPostagem.setDataAtualizacao(LocalDateTime.now());

        return super.salvar(novaPostagem);
    }
}
