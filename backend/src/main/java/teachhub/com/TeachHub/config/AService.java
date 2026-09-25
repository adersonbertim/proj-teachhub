package teachhub.com.TeachHub.config;

/*
 * Abstract class service to do basic CRUD operations
 * @author: adersonbertim
 * @since 11/01/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;
import teachhub.com.TeachHub.model.postagem.Postagem;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.List;
import java.util.Optional;

public abstract class AService <E, R extends JpaRepository<E, Long>> {

    protected R repository;

    public AService (R repository){
        this.repository = repository;
    }
    public E salvar(E novaEntidade){
        return this.repository.save(novaEntidade);
    }
    public List<E> findAll(){
        return this.repository.findAll();
    }
    public void deletar(Long id){
        this.repository.deleteById(id);
    }

    public E findById(Long id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado com o ID " + id));
    }

}
