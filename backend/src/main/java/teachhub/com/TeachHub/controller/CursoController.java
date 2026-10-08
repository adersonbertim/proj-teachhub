package teachhub.com.TeachHub.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.core.ApiResponse;
import teachhub.com.TeachHub.model.curso.*;
import teachhub.com.TeachHub.service.CursoService;

import java.util.List;

@RestController
@RequestMapping("/curso")
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

    @GetMapping("/listar-todos")
    public ResponseEntity<ApiResponse<List<CursoDTO>>> listarTodos() {
        var cursos = service.listarAtivos()
                .stream()
                .map(CursoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(cursos));
    }

    @GetMapping("/admin/todos")
    public ResponseEntity<ApiResponse<List<CursoDTO>>> listarParaGerenciar() {
        var cursos = service.listarTodosParaGerenciar()
                .stream()
                .map(CursoDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(cursos));
    }

    @GetMapping("/admin/youtube")
    public ResponseEntity<ApiResponse<List<ResultadoYoutubeDTO>>> pesquisarNoYoutube(@RequestParam String termo) {
        return ResponseEntity.ok(ApiResponse.success(service.pesquisarNoYoutube(termo)));
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<CursoDTO>> adicionar(@RequestBody @Valid CursoCadastroDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(CursoDTO.fromEntity(service.adicionar(dto))));
    }

    @PostMapping("/admin/link")
    public ResponseEntity<ApiResponse<CursoDTO>> adicionarPorLink(@RequestBody @Valid CursoLinkDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(CursoDTO.fromEntity(service.adicionarPorLink(dto.link(), dto.categoria()))));
    }

    @PutMapping("/admin/{id}/ativo")
    public ResponseEntity<ApiResponse<CursoDTO>> alterarAtivo(@PathVariable Long id, @RequestParam boolean valor) {
        return ResponseEntity.ok(ApiResponse.success(CursoDTO.fromEntity(service.alterarAtivo(id, valor))));
    }
}
