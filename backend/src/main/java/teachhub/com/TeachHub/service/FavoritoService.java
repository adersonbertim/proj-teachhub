package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teachhub.com.TeachHub.model.favorito.Favorito;
import teachhub.com.TeachHub.model.favorito.FavoritoDTO;
import teachhub.com.TeachHub.model.favorito.FavoritoRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final PostagemService postagemService;

    public FavoritoService(FavoritoRepository favoritoRepository, PostagemService postagemService) {
        this.favoritoRepository = favoritoRepository;
        this.postagemService = postagemService;
    }


    @Transactional
    public boolean toggleFavorito(Long postagemId, Usuario usuario) {
        Postagem postagem = postagemService.buscarPorId(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        Optional<Favorito> existente = favoritoRepository
                .findByUsuarioAndPostagem(usuario, postagem);

        if (existente.isPresent()) {
            favoritoRepository.delete(existente.get());
            return false;
        }

        Favorito novo = new Favorito();
        novo.setUsuario(usuario);
        novo.setPostagem(postagem);
        novo.setData(LocalDateTime.now());
        favoritoRepository.save(novo);
        return true;
    }

<<<<<<< Updated upstream
    public List<FavoritoDTO> listarFavoritos(Usuario usuario) {
        return favoritoRepository.findByUsuarioOrderByDataDesc(usuario)
                .stream()
                .map(FavoritoDTO::fromEntity)
                .toList();
    }

    public boolean isFavorita(Long postagemId, Usuario usuario) {
        return favoritoRepository.existsByUsuarioIdAndPostagemId(usuario.getId(), postagemId);
    }

    @Transactional
    public void deletarTodosDoUsuario(Usuario usuario) {
        favoritoRepository.deleteByUsuario(usuario);
    }
}
=======
    //Verificação se ja foi favorito
    @Override
    public List<Favorito> findAll() {
        return super.findAll();
    }

    public Optional<Favorito> findByUserAndPostagem(Usuario usuario,  Postagem postagem) {
        return repository.findByUsuarioAndPostagem(usuario, postagem);
    }

    public void remover(Favorito favorito) {
        repository.delete(favorito);
    }
}
>>>>>>> Stashed changes
