package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.comentarios.Comentarios;
import teachhub.com.TeachHub.model.comentarios.ComentariosRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentariosService extends AService<Comentarios, ComentariosRepository> {
    public ComentariosService(ComentariosRepository repository) {
        super(repository);
    }

    public Comentarios criarComentario(String texto, Postagem postagem, Usuario usuario) {
        Comentarios comentario = new Comentarios();
        comentario.setTexto(texto);
        comentario.setData(LocalDateTime.now());
        comentario.setPostagem(postagem);
        comentario.setUsuario(usuario);
        return repository.save(comentario);
    }

    public List<Comentarios> listarPorPostagem(Postagem postagem) {
        return repository.findByPostagem(postagem);
    }
}
