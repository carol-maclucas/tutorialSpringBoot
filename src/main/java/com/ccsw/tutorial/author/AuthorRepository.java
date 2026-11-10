package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {

    /**
     * Método para recuperar un listado paginado de {@link Author}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Author}
     */

    // Para en Implementación, pasarle un tipo "Pageable" y nos devuelva un "Page"
    Page<Author> findAll(Pageable pageable);

    // Además, nos ahorra tener que generar una sql para buscar una página concreta
}
