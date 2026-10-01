package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.favorito.Favorito;
import teachhub.com.TeachHub.model.favorito.FavoritoDTO;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.FavoritoService;
import teachhub.com.TeachHub.service.PostagemService;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController  {

    private final FavoritoService  service;

    public FavoritoController(FavoritoService service) {
        this.service = service;
    }

    @PostMapping("/{postagemId}")
    public ResponseEntity<ApiResponse<Boolean>> toggle(
            @PathVariable Long postagemId,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        boolean favoritado = service.toggleFavorito(postagemId, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(favoritado));
    }

    @GetMapping("/meus")
    public ResponseEntity<ApiResponse<List<FavoritoDTO>>> listarMeus(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        List<FavoritoDTO> favoritos = service.listarFavoritos(usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(favoritos));
    }

    @GetMapping("/postagem/{postagemId}/status")
    public ResponseEntity<ApiResponse<Boolean>> status(
            @PathVariable Long postagemId,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        boolean favoritado = service.isFavorita(postagemId, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(favoritado));
    }

    @PostMapping("/postagem/{postagemId}/toggle")
    public ResponseEntity<ApiResponse<Boolean>> toggleLegado(
            @PathVariable Long postagemId,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        boolean favoritado = service.toggleFavorito(postagemId, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(favoritado));
    }
}
