package com.malki.apilivros.services;

import com.malki.apilivros.models.LivroModel;
import com.malki.apilivros.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {
   /*
    final LivroRepository livroRepository;
    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }
   */
    @Autowired
    LivroRepository livroRepository;

    @Transactional //commit senão rollback, indicado sempre em ações que alterem o recurso
    public LivroModel save(LivroModel livroModel){
        return livroRepository.save(livroModel);
    }

    public boolean existsByIsbn13(String isbn13) {
        return livroRepository.existsByIsbn13(isbn13);
    }

    public List<LivroModel> findAll() {
        return livroRepository.findAll();
    }

    public Page<LivroModel> findAllPageable(Pageable pageable) {
        return livroRepository.findAll(pageable);
    }
    public Optional<LivroModel> findById(UUID id) {
        return livroRepository.findById(id);
    }

    public Optional<LivroModel> findByIsbn13(String isbn13) {
        return livroRepository.findByIsbn13(isbn13);
    }

    public Optional<List<LivroModel>> findByAutor(String autor) {
        return livroRepository.findByAutor(autor);
    }

    public void delete(LivroModel livroModel) {
        livroRepository.delete(livroModel);
    }

    public Optional<List<LivroModel>> findLivroByAutorAndAno(String autor, Integer ano) {
        return livroRepository.findLivroByAutorAndAno(autor, ano);
    }
}
