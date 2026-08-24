package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.usuarios.PerfilResponseDTO;
import teachhub.com.TeachHub.model.usuarios.PerfilUpdateDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.PerfilService;

// Confirmado: mesmo pacote do AuthController que você mandou.
@RestController
@RequestMapping("/perfil")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    // Perfil visitante — acessível para qualquer um, logado ou não.
    // Garanta que essa rota esteja liberada (permitAll) no SecurityConfig.
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> visualizarPerfil(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        PerfilResponseDTO perfil = perfilService.buscarPerfil(id, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(perfil));
    }

    // Atalho para o próprio usuário logado ver o próprio perfil completo (tela de config)
    @GetMapping("/me")
    public ResponseEntity<?> meuPerfil(@AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            if (usuarioLogado == null) {
                return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
            }
            PerfilResponseDTO perfil = perfilService.buscarPerfil(usuarioLogado.getId(), usuarioLogado);
            return ResponseEntity.ok(ApiResponse.success(perfil));
        } catch (Exception e) {
            e.printStackTrace(); // temporário, só pra ver a linha exata no console
            throw e;
        }
    }

    // Atualização do próprio perfil — sempre a partir do usuário autenticado, nunca por id na URL
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> atualizarPerfil(
            @RequestBody PerfilUpdateDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        PerfilResponseDTO atualizado = perfilService.atualizarPerfil(usuarioLogado, dto);
        ResponseEntity<ApiResponse<PerfilResponseDTO>> ok = ResponseEntity.ok(ApiResponse.success(atualizado));
        return ok;
    }
}