package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ccsw
 *
 */
//CONFIGURACION DE TODO EL CONTROLADOR
//Nombre de la categoría para el swagger, que describe y documenta apis
@Tag(name = "Category", description = "API of Category")
@RequestMapping(value = "/category")
// Lo que devuelvan los métodos será como respuesta HTTP:
@RestController
// Permite peticiones desde otros dominios (nuestro localhost8080)
@CrossOrigin(origins = "*")
public class CategoryController {

    // Autowired: new CategoryService(); Aqui lo busca en C.IMPL implemnts C.Service y lo inyecta
    // si ponemos new daría error y nullpoints
    @Autowired
    private CategoryService categoryService;

    /**
     * Método para recuperar todas las categorias
     *
     * @return {@link List} de {@link CategoryDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Categories")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CategoryDto> findAll() {
        return this.categoryService.findAll();
    }

    /**
     * Método para crear o actualizar una categoria
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDto dto) {
        this.categoryService.save(id, dto);
    }

    /**
     * Método para borrar una categoria
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        this.categoryService.delete(id);
    }
}
