package teachhub.com.TeachHub.model.postagem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.util.List;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

    List<Postagem> findByAutor(Usuario autor);
    void deleteByAutor(Usuario autor);

    @Query("""
            select distinct p from Postagem p
            left join p.tags t
            where lower(p.titulo) like concat('%', lower(:termo), '%')
               or lower(t.nome) = lower(:termo)
            """)
    Page<Postagem> buscarPorTituloOuTag(@Param("termo") String termo, Pageable pageable);

    @Query(
            value = """
                    select p from Postagem p
                    join p.tags t
                    where p.id <> :postagemId
                      and t.id in (
                          select tagAtual.id from Postagem atual
                          join atual.tags tagAtual
                          where atual.id = :postagemId
                      )
                    group by p
                    order by count(distinct t.id) desc, p.dataCriacao desc
                    """,
            countQuery = """
                    select count(distinct p.id) from Postagem p
                    join p.tags t
                    where p.id <> :postagemId
                      and t.id in (
                          select tagAtual.id from Postagem atual
                          join atual.tags tagAtual
                          where atual.id = :postagemId
                      )
                    """
    )
    Page<Postagem> buscarRelacionadas(@Param("postagemId") Long postagemId, Pageable pageable);
}
