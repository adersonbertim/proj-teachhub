package teachhub.com.TeachHub.config;

/*
* Abstract controller to do basic CRUD operations
* @author: adersonbertim
* @since 11/01/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AController <E, S extends AService<E, ?>> {
    protected S service;

    public AController (S servico){
        this.service = servico;
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping
    public List<E> listar(){
        return service.findAll();
    }

    @CrossOrigin("http://localhost:4200")
    @PostMapping
    public void cadastrarNovo(@RequestBody E entidade){
        service.salvar(entidade);
    }

    @CrossOrigin("http://localhost:4200")
    @PutMapping
    public void atualizar(@RequestBody E entidade){
        service.salvar(entidade);
    }

    @DeleteMapping("/{id}")
    public void deletar(@RequestParam Long id){
        service.deletar(id);
    }


}
