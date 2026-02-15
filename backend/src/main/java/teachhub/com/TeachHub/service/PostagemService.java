package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.postagem.PostagemDTO;
import teachhub.com.TeachHub.model.postagem.PostagemRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

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

    public Postagem criarPostagem(PostagemDTO.PostagemRequestDTO dto, Usuario usuario) {
        Postagem postagem = new Postagem();
        postagem.setTitulo(dto.titulo());
        postagem.setDescricao(dto.descricao());
        postagem.setCategoria(dto.categoria());
        postagem.setTag(dto.tag());
        postagem.setDataCriacao(LocalDateTime.now());
        postagem.setDataAtualizacao(LocalDateTime.now());
        postagem.setAutor(usuario);
        return repository.save(postagem);


    }
}
