package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// ------------- TEST UNITARIOS -----------
// Como los test unitarios comprueban la "lógica" solemos probar la clase de implementación

//@ExtendWith -> Para indicar que NO inicialice el contexto Spring
@ExtendWith(MockitoExtension.class)
public class CategoryTest {
    // @Mock: creamos un OBJETO FALSO para sustituir al repositorio real
    // Son los @autowired
    @Mock
    private CategoryRepository categoryRepository;
    // @InjectMocks: crea la instancia de la clase "C.ServiceImpl" e inyecta los mocks
    //
    @InjectMocks
    private CategoryServiceImpl categoryService;

    // Cada método de prueba pondremos @TEST, porque sino no lo ejecutará
    // En cada método SOLO se prueba UNA ACCIÓN

    //-------------- LISTA de CATEGORÍAS ------------
    @Test
    public void findAllShouldReturnAllCategories() {

        List<Category> list = new ArrayList<>();
        // Para añadir un objeto Category falso
        list.add(mock(Category.class));

        // Comprobamos el método FINDaLL()
        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> categories = categoryService.findAll();

        // Como añado una categoría, compruebo que el tamaño es 1
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    //-------------- CREACIÓN de CATEGORÍAS ------------
    public static final String CATEGORY_NAME = "CAT1";

    @Test
    public void saveNotExistsCategoryIdShouldInsert() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(CATEGORY_NAME);

        // "ArgumentCaptor" captura los objetos pasados a métodos para poder usar el "get(), set()"
        ArgumentCaptor<Category> category = ArgumentCaptor.forClass(Category.class);

        categoryService.save(null, categoryDto);
        // Comprobar que se llamó al "save()" del repositorio
        // "capture()" guarda lo que se le ha pasado
        verify(categoryRepository).save(category.capture());
        assertEquals(CATEGORY_NAME, category.getValue().getName());
    }

    //-------------- MODIFICACIÓN de CATEGORÍAS ------------
    public static final Long EXISTS_CATEGORY_ID = 1L;

    @Test
    public void saveExistsCategoryIdShouldUpdate() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(CATEGORY_NAME);

        Category category = mock(Category.class);

        // Cuando se busque el "id", devuelve una categoría existente
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

        categoryService.save(EXISTS_CATEGORY_ID, categoryDto);
        verify(categoryRepository).save(category);
    }

    //-------------- ELIMINACIÓN de CATEGORÍAS ------------
    @Test
    public void deleteExistsCategoryIdShouldDelete() throws Exception {
        Category category = mock(Category.class);
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

        categoryService.delete(EXISTS_CATEGORY_ID);
        verify(categoryRepository).deleteById(EXISTS_CATEGORY_ID);
    }

    //-------------- LLAMAR a CATEGORÍAS ------------
    public static final Long NOT_EXISTS_CATEGORY_ID = 0L;

    // Con Id
    @Test
    public void getExistsCategoryIdShouldReturnCategory() {

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(EXISTS_CATEGORY_ID);
        when(categoryRepository.findById(EXISTS_CATEGORY_ID)).thenReturn(Optional.of(category));

        Category categoryResponse = categoryService.get(EXISTS_CATEGORY_ID);

        assertNotNull(categoryResponse);
        assertEquals(EXISTS_CATEGORY_ID, category.getId());
    }

    // Sin Id
    @Test
    public void getNotExistsCategoryIdShouldReturnNull() {
        when(categoryRepository.findById(NOT_EXISTS_CATEGORY_ID)).thenReturn(Optional.empty());

        Category category = categoryService.get(NOT_EXISTS_CATEGORY_ID);

        assertNull(category);
    }
}