package teachhub.com.TeachHub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.service.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController extends AController<Curso, CursoService> {
    public CursoController(CursoService service) {
        super(service);
    }
}
