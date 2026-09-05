package teachhub.com.TeachHub.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.usuarios.PerfilResponseDTO;
import teachhub.com.TeachHub.model.usuarios.PerfilUpdateDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.service.PerfilService;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> visualizarPerfil(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        PerfilResponseDTO perfil = perfilService.buscarPerfil(id, usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(perfil));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> meuPerfil(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        PerfilResponseDTO perfil = perfilService.buscarPerfil(usuarioLogado.getId(), usuarioLogado);
        return ResponseEntity.ok(ApiResponse.success(perfil));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> atualizarPerfil(
            @RequestBody PerfilUpdateDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        PerfilResponseDTO atualizado = perfilService.atualizarPerfil(usuarioLogado, dto);
        return ResponseEntity.ok(ApiResponse.success(atualizado));
    }

    @PostMapping(value = "/me/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> atualizarFotoPerfil(
            @RequestParam("arquivo") MultipartFile arquivo,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Não autenticado"));
        }
        PerfilResponseDTO atualizado = perfilService.atualizarFotoPerfil(usuarioLogado, arquivo);
        return ResponseEntity.ok(ApiResponse.success(atualizado));
    }
}