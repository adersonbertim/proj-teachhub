package teachhub.com.TeachHub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.comentarios.*;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentariosService extends AService<Comentarios, ComentariosRepository> {
    private final ComentarioLikeRepository likeRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public ComentariosService(ComentariosRepository repository, ComentarioLikeRepository likeRepository, UsuarioRepository usuarioRepository, EmailService emailService) {
        super(repository);
        this.likeRepository = likeRepository;
        this.usuarioRepository= usuarioRepository;
        this.emailService = emailService;
    }

    public List<Comentarios> listarPorPostagem(Postagem postagem) {
        return repository.findByPostagemAndComentarioPaiIsNullOrderByDataDesc(postagem);
    }

    @Transactional(readOnly = true)
    public List<Comentarios> listarRespostas(
            Comentarios comentarioPai) {

        return repository
                .findByComentarioPaiOrderByDataAsc(comentarioPai);
    }

    public Optional<Comentarios> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Cria um comentário raiz (comentarioPai = null)
    public Comentarios criarComentario(String texto, Postagem postagem, Usuario usuario) {
        return salvarComentario(texto, postagem, usuario, null);
    }

    // Cria uma resposta a um comentário existente
    public Comentarios responder(String texto, Comentarios comentarioPai, Usuario usuario) {
        return salvarComentario(texto, comentarioPai.getPostagem(), usuario, comentarioPai);
    }

    private Comentarios salvarComentario(String texto, Postagem postagem, Usuario usuario, Comentarios pai) {
        Comentarios comentario = new Comentarios();
        comentario.setTexto(texto);
        comentario.setData(LocalDateTime.now());
        comentario.setPostagem(postagem);
        comentario.setUsuario(usuarioRepository.getReferenceById(usuario.getId()));
        comentario.setComentarioPai(pai);
        Comentarios salvo = repository.save(comentario);
        Usuario autor = postagem.getAutor();
        if (!autor.getId().equals(usuario.getId())) {
            emailService.enviarEmailPostagemComentada(autor.getEmail(), autor.getNome(),
                    postagem.getTitulo(), usuario.getNome());
        }
        return salvo;
    }

    // Curte/descurte um comentário (mesmo padrão do LikeService de postagem)
    public ComentariosDTO likeEstado(Comentarios comentario, Usuario usuario) {
        var existente = likeRepository.findByUsuarioAndComentario(usuario, comentario);

        if (existente.isPresent()) {
            likeRepository.delete(existente.get());
        } else {
            ComentarioLike like = new ComentarioLike();
            like.setComentario(comentario);
            like.setUsuario(usuario);
            like.setData(LocalDateTime.now());
            likeRepository.save(like);
        }

        return toDTO(comentario, usuario);
    }

    // Lista os comentários raiz de uma postagem, cada um já com suas respostas dentro
    public List<ComentariosDTO> listarPorPostagem(Postagem postagem, Usuario usuarioLogado) {
        return repository.findByPostagemAndComentarioPaiIsNullOrderByDataDesc(postagem).stream()
                .map(c -> toDTO(c, usuarioLogado))
                .toList();
    }

    // Exclui comentarios e as respostas
    @Transactional
    public void excluirComentario(Comentarios comentario, Usuario usuario) {
        if (!comentario.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Você só pode excluir os seus comentários");
        }
        apagarComRespostas(comentario);
    }


    @Transactional
    public void apagarTodosDaPostagem(Postagem postagem) {
        for (Comentarios comentario : repository.findByPostagemAndComentarioPaiIsNullOrderByDataDesc(postagem)) {
            apagarComRespostas(comentario);
        }
    }

    @Transactional
    public void apagarTodosDoUsuario(Usuario usuario) {
        likeRepository.deleteByUsuario(usuario);
        for (Comentarios comentario : repository.findByUsuario(usuario)) {

            if (repository.existsById(comentario.getId_comentarios())) {
                apagarComRespostas(comentario);
            }
        }
    }

    private void apagarComRespostas(Comentarios comentario) {
        for (Comentarios resposta : repository.findByComentarioPaiOrderByDataAsc(comentario)) {
            apagarComRespostas(resposta);
        }
        likeRepository.deleteByComentario(comentario);
        repository.delete(comentario);
    }


    // Converte um comentário (e recursivamente suas respostas) pro DTO
    private ComentariosDTO toDTO(Comentarios c, Usuario usuarioLogado) {
        List<ComentariosDTO> respostas = repository.findByComentarioPaiOrderByDataAsc(c).stream()
                .map(resp -> toDTO(resp, usuarioLogado))
                .toList();

        boolean curtido = usuarioLogado != null
                && likeRepository.findByUsuarioAndComentario(usuarioLogado, c).isPresent();

        return ComentariosDTO.builder()
                .id(c.getId_comentarios())
                .texto(c.getTexto())
                .data(c.getData())
                .nomeUsuario(c.getUsuario().getNome())
                .fotoUsuario(c.getUsuario().getImagemPerfil())
                .idPostagem(c.getPostagem().getId())
                .idComentarioPai(c.getComentarioPai() != null
                        ? c.getComentarioPai().getId_comentarios() : null)
                .qtdLikes(likeRepository.countByComentario(c))
                .curtidoPeloUsuario(curtido)
                .souAutor(usuarioLogado != null && c.getUsuario().getId().equals(usuarioLogado.getId()))
                .respostas(respostas)
                .build();
    }


}
