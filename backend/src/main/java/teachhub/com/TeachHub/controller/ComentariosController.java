package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.comentarios.Comentarios;
import teachhub.com.TeachHub.model.comentarios.ComentariosDTO;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.ComentariosService;
import teachhub.com.TeachHub.service.PostagemService;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    private final ComentariosService comentariosService;
    private final PostagemService postagemService;

    public ComentariosController(ComentariosService comentariosService, PostagemService postagemService) {
        this.comentariosService = comentariosService;
        this.postagemService = postagemService;
    }

    // Lista os comentários (com respostas aninhadas) de uma postagem
    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<ApiResponse<List<ComentariosDTO>>> listarPorPostagem(
            @PathVariable Long postagemId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Postagem postagem = buscarPostagem(postagemId);
        return ResponseEntity.ok(ApiResponse.success(comentariosService.listarPorPostagem(postagem, usuarioLogado)));
    }

    // Cria um comentário raiz numa postagem
    @PostMapping("/postagem/{postagemId}")
    public ResponseEntity<ApiResponse<ComentariosDTO>> criar(
            @PathVariable Long postagemId,
            @RequestBody ComentariosDTO.ComentarioRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Postagem postagem = buscarPostagem(postagemId);
        Comentarios criado = comentariosService.criarComentario(dto.texto(), postagem, usuarioLogado);
        ComentariosDTO resultado = buscarNaLista(comentariosService.listarPorPostagem(postagem, usuarioLogado), criado.getId_comentarios());
        return ResponseEntity.ok(ApiResponse.success(resultado));
    }

    // Responde a um comentário existente
    @PostMapping("/{comentarioId}/resposta")
    public ResponseEntity<ApiResponse<ComentariosDTO>> responder(
            @PathVariable Long comentarioId,
            @RequestBody ComentariosDTO.ComentarioRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Comentarios pai = comentariosService.findById(comentarioId);
        comentariosService.responder(dto.texto(), pai, usuarioLogado);
        ComentariosDTO resultado = buscarNaLista(
                comentariosService.listarPorPostagem(pai.getPostagem(), usuarioLogado), pai.getId_comentarios());
        return ResponseEntity.ok(ApiResponse.success(resultado));
    }

    // Curte/descurte um comentário
    @PostMapping("/{comentarioId}/like/estado")
    public ResponseEntity<ApiResponse<ComentariosDTO>> likeEstado(
            @PathVariable Long comentarioId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Comentarios comentario = comentariosService.findById(comentarioId);
        return ResponseEntity.ok(ApiResponse.success(comentariosService.likeEstado(comentario, usuarioLogado)));
    }

    // Procura, na lista já convertida em DTO (com likes/respostas resolvidos), o comentário pelo id
    private ComentariosDTO buscarNaLista(List<ComentariosDTO> lista, Long id) {
        return lista.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    private Postagem buscarPostagem(Long id) {
        return postagemService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
    }
}
