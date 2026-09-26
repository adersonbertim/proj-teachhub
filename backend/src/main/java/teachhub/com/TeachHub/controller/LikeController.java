package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.like.LikeStatusDTO;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.LikeService;
import teachhub.com.TeachHub.service.PostagemService;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final PostagemService postagemService;

    public LikeController(LikeService likeService, PostagemService postagemService) {
        this.likeService = likeService;
        this.postagemService = postagemService;
    }

    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<ApiResponse<LikeStatusDTO>> status(@PathVariable Long postagemId,
                                                             @AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(ApiResponse.success(likeService.getStatus(buscarPostagem(postagemId), usuarioLogado)));
    }

    @PostMapping("/postagem/{postagemId}/toggle")
    public ResponseEntity<ApiResponse<LikeStatusDTO>> toggle(@PathVariable Long postagemId,
                                                             @AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(ApiResponse.success(likeService.likeEstado(buscarPostagem(postagemId), usuarioLogado)));
    }

    private Postagem buscarPostagem(Long id) {
        return postagemService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
    }
}