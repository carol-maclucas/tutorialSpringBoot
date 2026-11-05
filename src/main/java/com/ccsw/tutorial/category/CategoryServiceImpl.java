package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 */
// Para crear un SERVICIO :- Siempre en Implementacion
@Service
//"Las operaciones de la clase se ejecutan dentro de la transaccion"
// Osea encapsula, y todo sale bien o si falla algo no lo hace
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    //SELECT * FROM category;
    @Override
    public List<Category> findAll() {
        return (List<Category>) this.categoryRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CategoryDto dto) {
        //Como al "Controller" le llega un tipo DTO, lo paso a Category porqueq es llamado por:
        // "CategoryRepository" (capa de datos) y necesita un tipo CATEGORY
        Category category;
        if (id == null) {
            category = new Category();
        } else {
            category = this.categoryRepository.findById(id).orElse(null);
        }
        category.setName(dto.getName());

        this.categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.categoryRepository.findById(id).orElse(null) == null) {
            throw new Exception("Category not found");
        }
        this.categoryRepository.deleteById(id);
    }

}
