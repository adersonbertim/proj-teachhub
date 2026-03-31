package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.log_ia.LogIA;
import teachhub.com.TeachHub.model.log_ia.PerguntaDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.IAService;

import java.util.List;

@RestController
@RequestMapping("/ia")
public class IAController  {

    private final IAService iaService;
    public IAController(IAService iaService) {
        this.iaService = iaService;
    }

    @PostMapping("/perguntar")
    public ResponseEntity<ApiResponse<String>>conversa(
            @RequestBody PerguntaDTO perguntaDTO,
            @AuthenticationPrincipal Usuario usuarioLogado
            ){
        String resposta = iaService.pergunta(perguntaDTO.pergunta(), usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(resposta));
    }

    @GetMapping("/historico")
    public ResponseEntity<ApiResponse<List<LogIA>>> listarHistorico(
            @AuthenticationPrincipal Usuario usuarioLogado
    ){
        var historico = iaService.historico(usuarioLogado);
        return  ResponseEntity.ok(ApiResponse.success(historico));
    }
}
