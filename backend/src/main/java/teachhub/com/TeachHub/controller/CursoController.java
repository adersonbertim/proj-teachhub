package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.curso.CursoDTO;
import teachhub.com.TeachHub.model.usuarios.Usuario;
import teachhub.com.TeachHub.model.usuarios.UsuarioDTO;
import teachhub.com.TeachHub.service.CursoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private CursoService service;
    public CursoController(CursoService service) {
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<ApiResponse<List<CursoDTO>>> listarTodos() {
        var cursos = service.findAll()
                .stream()
                .map(CursoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(cursos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CursoDTO>> salvar(@RequestBody Curso curso) {
        return ResponseEntity.ok(ApiResponse.success(CursoDTO.fromEntity(service.salvar(curso))));
    }
}
