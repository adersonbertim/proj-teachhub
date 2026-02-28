package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.curso.CursoRepository;

@Service
public class CursoService extends AService<Curso, CursoRepository> {
    public CursoService(CursoRepository repository) {
        super(repository);
    }

}
