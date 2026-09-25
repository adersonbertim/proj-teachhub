package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.like.Like;
import teachhub.com.TeachHub.model.like.LikeRepository;
import teachhub.com.TeachHub.model.like.LikeStatusDTO;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikeService extends AService<Like, LikeRepository> {
    public LikeService(LikeRepository repository) {
        super(repository);
    }

    // Alterna entre curtir e descurtir, devolvendo o novo estado
    public LikeStatusDTO likeEstado(Postagem postagem, Usuario usuario) {
        var likeExistente = repository.findByUsuarioAndPostagem(usuario, postagem);

        if (likeExistente.isPresent()) {
            repository.delete(likeExistente.get());
        } else {
            Like novoLike = new Like();
            novoLike.setPostagem(postagem);
            novoLike.setUsuario(usuario);
            novoLike.setData(LocalDateTime.now());
            repository.save(novoLike);
        }

        return getStatus(postagem, usuario);
    }

    // Retorna a contagem atual e se o usuário logado já curtiu
    public LikeStatusDTO getStatus(Postagem postagem, Usuario usuario) {
        long qtd = repository.countByPostagem(postagem);
        boolean curtido = repository.findByUsuarioAndPostagem(usuario, postagem).isPresent();

        return LikeStatusDTO.builder()
                .qtdLikes(qtd)
                .curtidoPeloUsuario(curtido)
                .build();
    }
}