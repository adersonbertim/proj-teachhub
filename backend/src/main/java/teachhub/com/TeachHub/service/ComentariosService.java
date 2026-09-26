package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.comentarios.*;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentariosService extends AService<Comentarios, ComentariosRepository> {
    private final ComentarioLikeRepository likeRepository;

    public ComentariosService(ComentariosRepository repository, ComentarioLikeRepository likeRepository) {
        super(repository);
        this.likeRepository = likeRepository;
    }

    public List<Comentarios> listarPorPostagem(Postagem postagem) {
        return repository.findByPostagemAndComentarioPaiIsNullOrderByDataDesc(postagem);
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
        comentario.setUsuario(usuario);
        comentario.setComentarioPai(pai);
        return repository.save(comentario);
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

    // Converte um comentário (e recursivamente suas respostas) pro DTO
    private ComentariosDTO toDTO(Comentarios c, Usuario usuarioLogado) {
        List<ComentariosDTO> respostas = repository.findByComentarioPaiOrderByDataAsc(c).stream()
                .map(resp -> toDTO(resp, usuarioLogado))
                .toList();

        return ComentariosDTO.builder()
                .id(c.getId_comentarios())
                .texto(c.getTexto())
                .data(c.getData())
                .nomeUsuario(c.getUsuario().getNome())
                .nomeUsuario(c.getUsuario().getImagemPerfil())
                .idPostagem(c.getPostagem().getId())
                .build();
    }


}
