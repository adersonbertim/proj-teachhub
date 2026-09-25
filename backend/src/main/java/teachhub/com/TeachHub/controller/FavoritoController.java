package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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