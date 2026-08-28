package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.postagem.PostagemDTO;
import teachhub.com.TeachHub.model.postagem.PostagemRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        postagem.setTexto(dto.texto());
        postagem.setCategoria(dto.categoria());
        postagem.setTag(dto.tag());
        postagem.setDataCriacao(LocalDateTime.now());
        postagem.setDataAtualizacao(LocalDateTime.now());
        postagem.setAutor(usuario);

        if(postagem.getResumo() == null){
            String texto = postagem.getTexto();
            postagem.setResumo(texto.length() > 150 ? texto.substring(0, 147) + "..." : texto);
        }
        return repository.save(postagem);


    }


    public List<PostagemDTO> listarTodas() {
        return repository.findAll().stream()
                .map(PostagemDTO :: fromEntity)
                .toList();
    }

    public List<PostagemDTO> listarMinhas(Usuario autor) {
        return repository.findByAutor(autor).stream()
                .map(PostagemDTO :: fromEntity)
                .toList();
    }

    public void deletarPostagem(Long id, Usuario usuarioLogado) {
        Postagem postagem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        if (!postagem.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new RuntimeException("Você não tem permissão para excluir essa postagem");
        }

        repository.delete(postagem);
    }

    public Optional<Postagem> buscarPorId(Long id) {
        return repository.findById(id);
    }
}
