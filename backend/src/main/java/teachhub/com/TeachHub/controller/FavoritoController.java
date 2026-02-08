package teachhub.com.TeachHub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.model.favorito.Favorito;
import teachhub.com.TeachHub.model.favorito.FavoritoDTO;
import teachhub.com.TeachHub.service.FavoritoService;
import teachhub.com.TeachHub.service.NotificacaoService;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController extends AController <Favorito, FavoritoDTO, Long, FavoritoService> {
    public FavoritoController(FavoritoService service) {
        super(service);
    }

    @Override
    protected FavoritoDTO toDTO(Favorito entity) {
        return FavoritoDTO.fromEntity(entity);
    }
}
