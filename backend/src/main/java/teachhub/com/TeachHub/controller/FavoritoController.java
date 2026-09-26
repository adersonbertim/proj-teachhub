package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
<<<<<<< Updated upstream
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.favorito.FavoritoDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.FavoritoService;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
=======
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.favorito.Favorito;
import teachhub.com.TeachHub.model.favorito.FavoritoDTO;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.FavoritoService;
import teachhub.com.TeachHub.service.PostagemService;


@RestController
@RequestMapping("/favoritos")
public class FavoritoController extends AController <Favorito, FavoritoDTO, Long, FavoritoService> {

    private final PostagemService postagemService;
    public FavoritoController(FavoritoService service, PostagemService postagemService) {
        super(service);
        this.postagemService = postagemService;
>>>>>>> Stashed changes
    }


    @PostMapping("/{postagemId}")
    public ResponseEntity<ApiResponse<Boolean>> toggle(
            @PathVariable Long postagemId,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        boolean favoritado = favoritoService.toggleFavorito(postagemId, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(favoritado));
    }

<<<<<<< Updated upstream
    @GetMapping("/meus")
    public ResponseEntity<ApiResponse<List<FavoritoDTO>>> listarMeus(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        List<FavoritoDTO> favoritos = favoritoService.listarFavoritos(usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(favoritos));
    }
}
=======
    @GetMapping("/postagem/{postagemId}/status")
    public ResponseEntity<ApiResponse<Boolean>> status(@PathVariable Long postagemId,
                                                       @AuthenticationPrincipal Usuario usuarioLogado) {
        Postagem postagem = buscarPostagem(postagemId);
        boolean favoritado = service.findByUserAndPostagem(usuarioLogado, postagem).isPresent();
        return ResponseEntity.ok(ApiResponse.success(favoritado));
    }

    // Favorita ou desfavorita, sempre com base no usuário autenticado (não confia no corpo da requisição)
    @PostMapping("/postagem/{postagemId}/toggle")
    public ResponseEntity<ApiResponse<Boolean>> toggle(@PathVariable Long postagemId,
                                                       @AuthenticationPrincipal Usuario usuarioLogado) {
        Postagem postagem = buscarPostagem(postagemId);
        var existente = service.findByUserAndPostagem(usuarioLogado, postagem);

        if (existente.isPresent()) {
            service.remover(existente.get());
            return ResponseEntity.ok(ApiResponse.success(false));
        }

        Favorito novo = new Favorito();
        novo.setPostagem(postagem);
        novo.setUsuario(usuarioLogado);
        service.salvar(novo);
        return ResponseEntity.ok(ApiResponse.success(true));
    }

    private Postagem buscarPostagem(Long id) {
        return postagemService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
    }
}
>>>>>>> Stashed changes
