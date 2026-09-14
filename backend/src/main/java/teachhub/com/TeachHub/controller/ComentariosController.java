package teachhub.com.TeachHub.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.model.comentarios.Comentarios;
import teachhub.com.TeachHub.model.comentarios.ComentariosDTO;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.ComentariosService;
import teachhub.com.TeachHub.service.PostagemService;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController extends AController<Comentarios, ComentariosDTO, Long, ComentariosService> {

    private final PostagemService postagemService;

    public ComentariosController(ComentariosService service, PostagemService postagemService) {
        super(service);
        this.postagemService = postagemService;
    }

    @Override
    protected ComentariosDTO toDTO(Comentarios entity) {
        return ComentariosDTO.fromEntity(entity);
    }

    // Lista os comentários de uma postagem específica
    @GetMapping("/postagem/{postagemId}")
    public List<ComentariosDTO> listarPorPostagem(@PathVariable Long postagemId) {
        Postagem postagem = postagemService.buscarPorId(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        return service.listarPorPostagem(postagem).stream()
                .map(ComentariosDTO::fromEntity)
                .toList();
    }

    // Cria um comentário numa postagem específica
    @PostMapping("/postagem/{postagemId}")
    public ComentariosDTO criar(
            @PathVariable Long postagemId,
            @RequestBody ComentariosDTO.ComentarioRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Postagem postagem = postagemService.buscarPorId(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        Comentarios comentario = service.criarComentario(dto.texto(), postagem, usuarioLogado);
        return ComentariosDTO.fromEntity(comentario);
    }
}