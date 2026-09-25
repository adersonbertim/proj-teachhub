package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.curso.CursoRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.Optional;

@Service
public class CursoService extends AService<Curso, CursoRepository> {
    public CursoService(CursoRepository repository) {
        super(repository);
    }

}
