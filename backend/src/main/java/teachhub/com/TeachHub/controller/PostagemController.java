package teachhub.com.TeachHub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachhub.com.TeachHub.config.AController;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.service.PostagemService;

@RestController
@RequestMapping("/postagens")
public class PostagemController extends AController<Postagem, PostagemService> {
    public PostagemController(PostagemService service) {
        super(service);
    }
}
