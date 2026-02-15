package teachhub.com.TeachHub.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.postagem.PostagemDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.PostagemService;

@RestController
@RequestMapping("/postagens")
public class PostagemController  {
    private PostagemService service;

    public PostagemController(PostagemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostagemDTO>> criar (
        @RequestBody @Valid PostagemDTO.PostagemRequestDTO dto,
                @AuthenticationPrincipal Usuario usuarioLogado) {
        Postagem postagem = service.criarPostagem(dto, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(PostagemDTO.fromEntity(postagem)));
    }
}
