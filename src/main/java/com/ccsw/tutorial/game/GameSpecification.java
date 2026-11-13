package com.ccsw.tutorial.game;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

// - Para no hacerla en Service para no duplicar código, centralizamos la construcción de predicados

public class GameSpecification implements Specification<Game> {

    private static final long serialVersionUID = 1L;

    // Cuando se crea la especificación, se debe general el predicado
    private final SearchCriteria criteria;

    // Le pasamos los criterios para el FILTRADO
    public GameSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // TIPO DE OPERACIÓN
        // (comparación)
        // operación -> se marca con : (<>, <, >)
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = getPath(root);
            // Predicate
            if (path.getJavaType() == String.class) {
                // Si es texto
                return builder.like(path, "%" + criteria.getValue() + "%");
            } else {
                // Si es número/fecha
                return builder.equal(path, criteria.getValue());
            }
        }
        return null;
    }

    // Para mirar las sub entidades (Autor y Categoría)
    private Path<String> getPath(Root<Game> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }
        return expression;
    }

}
