package teachhub.com.TeachHub.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.postagem.PostagemDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.PostagemService;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class PostagemController extends AController<Postagem, PostagemDTO, Long, PostagemService>  {
    private PostagemService service;

    public PostagemController(PostagemService service) {
        super(service);
        this.service = service;
    }

    @Override
    protected PostagemDTO toDTO(Postagem entity) {
        return PostagemDTO.fromEntity(entity);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<PostagemDTO>> cadastrarNovo (
        @RequestBody @Valid PostagemDTO.PostagemRequestDTO dto,
                @AuthenticationPrincipal Usuario usuarioLogado) {
        Postagem postagem = service.criarPostagem(dto, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(PostagemDTO.fromEntity(postagem)));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<PostagemDTO>>> listarTodos (){
        return ResponseEntity.ok(ApiResponse.success(service.listarTodas()));
    }

    @GetMapping("/minhas")
    public ResponseEntity<ApiResponse<List<PostagemDTO>>> listarMinhas(@AuthenticationPrincipal Usuario usuarioLogado){
        return ResponseEntity.ok(ApiResponse.success(service.listarMinhas(usuarioLogado)));
    }
}
