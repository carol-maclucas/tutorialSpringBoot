package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {
        // Solo funciona si añadimos una función "AuthorRepository"
        return this.authorRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, AuthorDto data) {

        Author author;

        if (id == null) {
            author = new Author();
        } else {
            author = this.authorRepository.findById(id).orElse(null);

            if (author == null) {
                throw new RuntimeException("Author not found");
            }
        }
        // BeanUtils:- Para copiar más de un dato de una clase a otra
        // Ignoramos id para que no copie el null si hubiera
        BeanUtils.copyProperties(data, author, "id");

        this.authorRepository.save(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.authorRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }

        this.authorRepository.deleteById(id);
    }
}
