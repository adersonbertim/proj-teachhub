package teachhub.com.TeachHub.config;

/*
* Abstract controller to do basic CRUD operations
* @author: adersonbertim
* @since 11/01/2026
 * @param <E> the entity type
 * @param <D> the DTO type
 * @param <ID> the id type
 * @param <S> the service type
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teachhub.com.TeachHub.core.ApiResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class AController <E, D, ID, S extends AService<E, ?>> {
    protected S service;

    protected abstract D toDTO(E entity);

    public AController (S servico){
        this.service = servico;
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping
    public ResponseEntity<ApiResponse<List<D>>> listar(){
        List<D> entidades = service.findAll().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(entidades));
    }

    @CrossOrigin("http://localhost:4200")
    @PostMapping
    public ResponseEntity<ApiResponse<D>> cadastrarNovo(@RequestBody E entidade){
        E salvo = service.salvar(entidade);
        return ResponseEntity.ok(ApiResponse.success(toDTO(salvo)));
    }


    @CrossOrigin("http://localhost:4200")
    @PutMapping
    public  ResponseEntity<ApiResponse<D>> atualizar(@RequestBody E entidade){
        E atualizado = service.salvar(entidade);
        return ResponseEntity.ok(ApiResponse.success(toDTO(atualizado)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.ok(ApiResponse.success("Item removido com sucesso!"));
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<D>> buscarPorId(@PathVariable Long id){
        E entidade = service.findById(id);
        return ResponseEntity.ok(ApiResponse.success(toDTO(entidade)));
    }

}
