package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.favorito.Favorito;
import teachhub.com.TeachHub.model.favorito.FavoritoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoritoService extends AService<Favorito, FavoritoRepository> {
    public FavoritoService(FavoritoRepository repository) {
        super(repository);
    }

    // Vai garantir que nunca haja uma data nula
    @Override
    public Favorito salvar(Favorito entidade) {
        if (entidade.getId() == null) {
            entidade.setData(LocalDateTime.now());
        }
        return super.salvar(entidade);
    }

    @Override
    public List<Favorito> findAll() {
        return super.findAll();
    }
}
