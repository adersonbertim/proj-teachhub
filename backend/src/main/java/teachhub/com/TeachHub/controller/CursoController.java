package teachhub.com.TeachHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.curso.CursoDTO;
import teachhub.com.TeachHub.service.CursoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController extends AController<Curso, CursoDTO, Long, CursoService> {

    private CursoService service;
    public CursoController(CursoService service) {
        super(service);
        this.service = service;
    }
    @Override
    protected CursoDTO toDTO(Curso entity) {
        return CursoDTO.fromEntity(entity);
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<CursoDTO>>> listarTodos() {
        var cursos = service.findAll()
                .stream()
                .map(CursoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(cursos));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<CursoDTO>> cadastrarNovo(@RequestBody Curso curso) {
        return ResponseEntity.ok(ApiResponse.success(CursoDTO.fromEntity(service.salvar(curso))));
    }
}
