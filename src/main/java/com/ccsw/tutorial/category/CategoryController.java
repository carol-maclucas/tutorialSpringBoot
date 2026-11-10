package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccsw
 *
 */
//CONFIGURACIÓN DE TODO EL CONTROLADOR
//Nombre de la categoría para el swagger, que describe y documenta APIS
@Tag(name = "Category", description = "API of Category")
@RequestMapping(value = "/category")
// Lo que devuelvan los métodos será como respuesta HTTP:
@RestController
// Permite peticiones desde otros dominios (nuestro localhost8080)
@CrossOrigin(origins = "*")
public class CategoryController {

    // Autowired: new CategoryService(); Aquí lo busca en "Category Service Implementación" y lo inyecta
    // si ponemos new tal cual daría error y null-points
    @Autowired
    private CategoryService categoryService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todas las {@link Category}
     *
     * @return {@link List} de {@link CategoryDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Categories")
    @RequestMapping(path = "", method = RequestMethod.GET)
    // Del usuario le llega tipo DTO
    public List<CategoryDto> findAll() {
        // Para buscar Category (Entity) en los datos (repositorio) y pasarla a
        // tipo CategoryDto (DTO) para enseñarla al usuario:
        List<Category> categories = this.categoryService.findAll();

        // List<Category> -> Stream<Category>
        return categories.stream()
                // En cada iteración lo pasa a DTO
                // Pero: id==id, nombre==nombre (Entity==DTO)
                .map(e -> mapper.map(e, CategoryDto.class))
                // Para volver de Stream a ser "List <CategoryDTO>"
                .collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una {@link Category}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    // ""      -> Crear si id == null
    // "/{id}" -> Actualizar si id == valor
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    // @RequestBody:- Recoge el JSON -> a DTO
    // SOLO cuando necesitas leer datos del usuario/JSON
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDto dto) {
        //Hacia el CategoryServiceIMPLEMENTATION
        this.categoryService.save(id, dto);
    }

    /**
     * Método para borrar una {@link Category}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        // Sin @ResquestBody, no lo necesitamos porque el DATO VIENEN EN EL URL
        // y no necesitamos recibir el objeto completo
        this.categoryService.delete(id);
    }
}
