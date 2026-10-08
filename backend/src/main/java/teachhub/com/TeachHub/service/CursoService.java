package teachhub.com.TeachHub.service;

import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.config.AService;
import teachhub.com.TeachHub.model.curso.Curso;
import teachhub.com.TeachHub.model.curso.CursoCadastroDTO;
import teachhub.com.TeachHub.model.curso.CursoRepository;
import teachhub.com.TeachHub.model.curso.ResultadoYoutubeDTO;

import java.util.List;

@Service
public class CursoService extends AService<Curso, CursoRepository> {

    private final YoutubeService youtubeService;

    public CursoService(CursoRepository repository, YoutubeService youtubeService) {
        super(repository);
        this.youtubeService = youtubeService;
    }

    public List<Curso> listarAtivos() {
        return repository.findByAtivoTrueOrderByIdDesc();
    }

    public List<Curso> listarTodosParaGerenciar() {
        return repository.findAllByOrderByIdDesc();
    }

    public List<ResultadoYoutubeDTO> pesquisarNoYoutube(String termo) {
        return youtubeService.pesquisar(termo).stream()
                .map(r -> r.comJaAdicionado(repository.findByIdExterno(r.idExterno()).isPresent()))
                .toList();
    }

    public Curso adicionar(CursoCadastroDTO dto) {
        if (repository.findByIdExterno(dto.idExterno()).isPresent()) {
            throw new RuntimeException("Esse curso já foi adicionado");
        }

        Curso curso = new Curso();
        curso.setIdExterno(dto.idExterno());
        curso.setTitulo(dto.titulo());
        curso.setDescricao(dto.descricao());
        curso.setImagem(dto.imagem());
        curso.setCanal(dto.canal());
        curso.setCategoria(dto.categoria());
        curso.setPlataforma("YouTube");
        curso.setLink(YoutubeService.montarLink(dto.idExterno(), dto.tipo()));
        curso.setAtivo(true);
        return repository.save(curso);
    }

    public Curso adicionarPorLink(String link, String categoria) {
        ResultadoYoutubeDTO r = youtubeService.buscarPorLink(link);
        return adicionar(new CursoCadastroDTO(
                r.idExterno(), r.tipo(), r.titulo(), r.descricao(), r.imagem(), r.canal(), categoria
        ));
    }

    public Curso alterarAtivo(Long id, boolean ativo) {
        Curso curso = findById(id);
        curso.setAtivo(ativo);
        return repository.save(curso);
    }
}
