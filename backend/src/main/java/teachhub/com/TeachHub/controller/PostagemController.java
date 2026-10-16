package teachhub.com.TeachHub.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/criar")
    public ResponseEntity<ApiResponse<PostagemDTO>> criar (
        @RequestBody @Valid PostagemDTO.PostagemRequestDTO dto,
                @AuthenticationPrincipal Usuario usuarioLogado) {
        Postagem postagem = service.criarPostagem(dto, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(PostagemDTO.fromEntity(postagem)));
    }

    @GetMapping("/listar-feed")
    public ResponseEntity<ApiResponse<Page<PostagemDTO>>> listarFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(service.listarTodas(paginacao(page, size))));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<Page<PostagemDTO>>> buscar(
            @RequestParam String termo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(service.buscar(termo, paginacao(page, size))));
    }

    @GetMapping("/minhas")
    public ResponseEntity<ApiResponse<List<PostagemDTO>>> listarMinhas(@AuthenticationPrincipal Usuario usuarioLogado){
        return ResponseEntity.ok(ApiResponse.success(service.listarMinhas(usuarioLogado)));
    }

    @GetMapping("/postagens/{id}")
    public ResponseEntity<ApiResponse<PostagemDTO>> buscarPorId(@PathVariable Long id){
        Postagem postagem = service.findById(id);
        return ResponseEntity.ok(ApiResponse.success(PostagemDTO.fromEntity(postagem)));
    }
    @DeleteMapping("/postagens/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        service.deletarPostagem(id, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    @GetMapping("/postagens/{id}/relacionadas")
    public ResponseEntity<ApiResponse<Page<PostagemDTO>>> relacionadas(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(service.listarRelacionadas(id, paginacao(page, size))));
    }

    private Pageable paginacao(int page, int size) {
        int paginaSegura = Math.max(page, 0);
        int tamanhoSeguro = Math.max(1, Math.min(size, 100));
        return PageRequest.of(paginaSegura, tamanhoSeguro, Sort.by(Sort.Direction.DESC, "dataCriacao"));
    }
}
