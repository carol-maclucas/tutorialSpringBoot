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
// Para crear un SERVICIO:- Siempre en Implementación
@Service
//"Las OPERACIONES de la clase se ejecutan DENTRO de la transacción"
// O sea encapsula, y todo sale bien o si falla algo no lo hace
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
        //Como al "Controller" le llega un tipo DTO, lo paso a Category porque es llamado por:
        // "CategoryRepository" (capa de datos) y necesita un tipo CATEGORY (entity)
        Category category;
        if (id == null) {
            category = new Category();
        } else {
            category = this.categoryRepository.findById(id).orElse(null);
            // NO DEL TUTORIAL: pero si llega id=3 con categoría=null no se manejaría
            // No es bueno asumir que porque tenga id siempre tendrá categoría
            if (category == null) {
                throw new RuntimeException("Category not found");
            }
        }
        // Para guardarlo en el "repositorio" ponemos el nombre del "setName.entity" llamando
        // al nombre del "getName.DTO" que es lo mismo
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
