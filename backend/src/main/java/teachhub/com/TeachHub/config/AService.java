package teachhub.com.TeachHub.config;

/*
 * Abstract class service to do basic CRUD operations
 * @author: adersonbertim
 * @since 11/01/2026
 */

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AService <E, R extends JpaRepository<E, Long>> {

    protected R repository;

    public AService (R repository){
        this.repository = repository;
    }
}
