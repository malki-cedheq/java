package com.malki.apilivros.repositories;

import com.malki.apilivros.models.LivroModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, UUID> {
    boolean existsByIsbn13(String isbn13);
    Optional<LivroModel> findByIsbn13(String isbn13);

    Optional<List<LivroModel>> findByAutor(String autor);

    Optional<List<LivroModel>> findLivroByAutorAndAno(String autor, Integer ano);

}
