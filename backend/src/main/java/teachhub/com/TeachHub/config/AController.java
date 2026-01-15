package teachhub.com.TeachHub.config;

/*
* Abstract controller to do basic CRUD operations
* @author: adersonbertim
* @since 11/01/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AController <E, S extends AService<E, ?>> {
    protected S service;

    public AController (S servico){
        this.service = servico;
    }

}
