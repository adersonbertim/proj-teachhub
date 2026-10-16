package teachhub.com.TeachHub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.favorito.FavoritoRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.postagem.PostagemDTO;
import teachhub.com.TeachHub.model.postagem.PostagemRepository;
import teachhub.com.TeachHub.model.tag.Tag;
import teachhub.com.TeachHub.model.tag.TagRepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class PostagemService extends AService<Postagem, PostagemRepository> {
    private final FavoritoRepository favoritoRepository;
    private final TagRepository tagRepository;

    public PostagemService(PostagemRepository repository,
                           FavoritoRepository favoritoRepository,
                           TagRepository tagRepository) {
        super(repository);
        this.favoritoRepository = favoritoRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Postagem salvar(Postagem novaPostagem) {
        if (novaPostagem.getId() == null) {
            novaPostagem.setDataCriacao(LocalDateTime.now());
        }
        novaPostagem.setDataAtualizacao(LocalDateTime.now());
        return super.salvar(novaPostagem);
    }

    @Transactional
    public Postagem criarPostagem(PostagemDTO.PostagemRequestDTO dto, Usuario usuario) {
        Postagem postagem = new Postagem();
        postagem.setTitulo(dto.titulo());
        postagem.setTexto(dto.texto());
        postagem.setCategoria(dto.categoria());
        postagem.setDataCriacao(LocalDateTime.now());
        postagem.setDataAtualizacao(LocalDateTime.now());
        postagem.setAutor(usuario);

        String texto = postagem.getTexto();
        postagem.setResumo(texto.length() > 150 ? texto.substring(0, 147) + "..." : texto);

        Set<Tag> tags = new HashSet<>();
        for (String nome : normalizarTags(dto.tags())) {
            Tag tag = tagRepository.findByNome(nome)
                    .orElseGet(() -> tagRepository.save(new Tag(nome)));
            tags.add(tag);
        }
        postagem.setTags(tags);
        return repository.save(postagem);
    }

    private List<String> normalizarTags(String entrada) {
        if (entrada == null || !entrada.trim().startsWith("#")) {
            throw new IllegalArgumentException("Informe de 1 a 10 tags no formato #tag.");
        }

        List<String> tags = Arrays.stream(entrada.split("#"))
                .map(String::trim)
                .filter(nome -> !nome.isBlank())
                .map(nome -> nome.toLowerCase(Locale.ROOT))
                .distinct()
                .toList();

        if (tags.isEmpty() || tags.size() > 10) {
            throw new IllegalArgumentException("Informe de 1 a 10 tags no formato #tag.");
        }
        return tags;
    }

    @Transactional(readOnly = true)
    public Page<PostagemDTO> listarTodas(Pageable pageable) {
        return repository.findAll(pageable).map(PostagemDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<PostagemDTO> buscar(String termo, Pageable pageable) {
        String consulta = termo == null ? "" : termo.trim().replaceAll("^#+", "").trim();
        if (consulta.isBlank()) {
            throw new IllegalArgumentException("Informe um termo para pesquisar.");
        }
        return repository.buscarPorTituloOuTag(consulta.toLowerCase(Locale.ROOT), pageable)
                .map(PostagemDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<PostagemDTO> listarMinhas(Usuario autor) {
        return repository.findByAutor(autor).stream()
                .map(PostagemDTO::fromEntity)
                .toList();
    }

    @Transactional
    public void deletarPostagem(Long id, Usuario usuarioLogado) {
        Postagem postagem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        if (!postagem.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new RuntimeException("Você não tem permissão para excluir essa postagem");
        }
        favoritoRepository.deleteByPostagem(postagem);
        repository.delete(postagem);
    }

    public Optional<Postagem> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deletarTodasDoAutor(Usuario autor) {
        for (Postagem postagem : repository.findByAutor(autor)) {
            favoritoRepository.deleteByPostagem(postagem);
            repository.delete(postagem);
        }
    }

    @Transactional(readOnly = true)
    public Page<PostagemDTO> listarRelacionadas(Long postagemId, Pageable pageable) {
        return repository.buscarRelacionadas(postagemId, pageable)
                .map(PostagemDTO::fromEntity);
    }
}
