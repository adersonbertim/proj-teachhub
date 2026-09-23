package teachhub.com.TeachHub.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.model.like.LikeStatusDTO;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.LikeService;
import teachhub.com.TeachHub.service.PostagemService;

// Não estende AController porque aqui não é um CRUD comum — é um "toggle"
@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final PostagemService postagemService;

    public LikeController(LikeService likeService, PostagemService postagemService) {
        this.likeService = likeService;
        this.postagemService = postagemService;
    }

    // Consulta o estado atual (quantidade de likes + se o usuário logado já curtiu)
    @GetMapping("/postagem/{postagemId}")
    public LikeStatusDTO status(@PathVariable Long postagemId, @AuthenticationPrincipal Usuario usuarioLogado) {
        return likeService.getStatus(buscarPostagem(postagemId), usuarioLogado);
    }

    // Curte ou descurte, dependendo do estado atual
    @PostMapping("/postagem/{postagemId}/toggle")
    public LikeStatusDTO toggle(@PathVariable Long postagemId, @AuthenticationPrincipal Usuario usuarioLogado) {
        return likeService.likeEstado(buscarPostagem(postagemId), usuarioLogado);
    }

    private Postagem buscarPostagem(Long id) {
        return postagemService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
    }
}