package com.ccsw.tutorial.game;

import com.ccsw.tutorial.game.model.Game;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author ccsw
 *
 */
// JpaSpecificationExecutor - Specification
public interface GameRepository extends CrudRepository<Game, Long>, JpaSpecificationExecutor<Game> {
    // Para que haga sub-consultas de join (evitando muchas consultas en BBDD)
    @Override
    // Con los atributos que queremos que se incluyan en el join
    @EntityGraph(attributePaths = { "category", "author" })
    List<Game> findAll(Specification<Game> spec);
}