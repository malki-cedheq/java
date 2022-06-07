package com.malki.apilivros.controllers;

import com.malki.apilivros.data_transfer_objects.LivroDTO;
import com.malki.apilivros.models.LivroModel;
import com.malki.apilivros.services.LivroService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/apilivros/livros")
public class LivroController {

      @Autowired
    LivroService livroService;
    private Optional<LivroModel> livroModelOptional; //final LivroService livroService; public LivroController(LivroService livroService){ this.livroService = livroService; }

    @PostMapping
    @ResponseBody
    //@Valid garante a aplicação do Validation do Data Transfer Object
    public ResponseEntity<Object> saveLivro(@RequestBody @Valid LivroDTO livroDTO){
        var livroModel = new LivroModel();

        //converte o objeto DTO no objeto Model
        BeanUtils.copyProperties(livroDTO, livroModel);
        livroModel.setCadastrado_em(LocalDateTime.now(ZoneId.of("UTC")));

        if(livroService.existsByIsbn13(livroDTO.getIsbn13())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: O livro de ISBN-13 "+livroDTO.getIsbn13()+" já está cadastrado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livroModel));
    }

    //GET http://localhost:8080/apilivros/livros
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<LivroModel>> getAllLivros(){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAll());
    }

    //GET http://localhost:8080/apilivros/livros/
    @GetMapping("/paginacao")
    @ResponseBody
    public ResponseEntity<Object> getAllPageableLivros(@RequestParam(defaultValue = "false") Boolean paginacao,
                                                       @RequestParam(defaultValue = "0", required = false) int paginas,
                                                       @RequestParam(defaultValue = "10", required = false) int tamanho,
                                                       @RequestParam(defaultValue = "false", required = false) boolean direcao){
        if (paginacao){
            Pageable pageable = direcao ?
                        PageRequest.of(paginas,tamanho, Sort.by("titulo").ascending())
                        : PageRequest.of(paginas,tamanho, Sort.by("titulo").descending()); //configura paginação
            return ResponseEntity.status(HttpStatus.OK).body(livroService.findAllPageable(pageable));
        }

        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAll());

    }

    //utilizando caminho GET http://localhost:8080/apilivros/livrosUUID/{id}
    @GetMapping("/UUID/{id}")
    @ResponseBody
    public ResponseEntity<Object> getOneLivro(@PathVariable(value = "id") UUID id){
        Optional<LivroModel> livroModelOptional = livroService.findById(id);
        if(!livroModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info: O livro de ID "+id+" não foi encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(livroModelOptional.get());
    }

    @GetMapping("/isbn13/{isbn13}")
    @ResponseBody
    //utilizando parâmetros GET http://localhost:8080/apilivros/livrosisbn13/{isbn13} retorna apenas 1 pois isbn é único
    public ResponseEntity<Object> getLivroByISBN13(@PathVariable(value = "isbn13") String isbn13){
        Optional<LivroModel> livroModelOptional = livroService.findByIsbn13(isbn13);
        return livroModelOptional.
                <ResponseEntity<Object>>map(livroModel -> ResponseEntity.status(HttpStatus.OK).body(livroModel)).
                orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info: O livro de isbn13 " + isbn13 + " não foi encontrado."));
    }

    @GetMapping("/autor")
    @ResponseBody
    //utilizando parâmetros GET http://localhost:8080/apilivros/livrosautor?autor={autor} retorna lista pois autor não é único
    public ResponseEntity<Object> getLivroByAutor(@RequestParam(value = "autor") String autor){
        Optional<List<LivroModel>> livroModelOptional = livroService.findByAutor(autor);
        if (livroModelOptional.get().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info: Não foram encontrados Livros do autor " + autor + ".");
        }
        return ResponseEntity.status(HttpStatus.OK).body(livroModelOptional.get());
    }

    @GetMapping("/autoreano")
    @ResponseBody
    //utilizando parâmetros GET http://localhost:8080/apilivros/livrosautoreano?autor={autor}&ano={ano} retorna lista pois autor não é único
    public ResponseEntity<Object> getLivroByAutorAndAno(@RequestParam(value = "autor") String autor,
                                                   @RequestParam Integer ano){
        Optional<List<LivroModel>> livroModelOptional = livroService.findLivroByAutorAndAno(autor, ano);
        if (livroModelOptional.get().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info: Não foram encontrados Livros do autor " + autor +" do ano "+ ano + ".");
        }
        return ResponseEntity.status(HttpStatus.OK).body(livroModelOptional.get());
    }


    //utilizando caminho GET http://localhost:8080/apilivros/livrosUUID/{id}
    @DeleteMapping("/UUID/{id}")
    @ResponseBody
    public ResponseEntity<Object> DeleteLivro(@PathVariable(value = "id") UUID id){
        Optional<LivroModel> livroModelOptional = livroService.findById(id);
        if(!livroModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info: O livro de ID "+id+" não foi encontrado.");
        }
        livroService.delete(livroModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("O livro de ID "+id+" foi excluido com sucesso.");
    }

    @PutMapping("/UUID/{id}")
    @ResponseBody
    //@Valid garante a aplicação do Validation do Data Transfer Object
    public ResponseEntity<Object> saveLivro(@PathVariable(value = "id") UUID id,
                                            @RequestBody @Valid LivroDTO livroDTO){
        Optional<LivroModel> livroModelOptional = livroService.findById(id);
        if(!livroModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Info: O livro de ID "+id+" não foi encontrado.");
        }
        var livroModel = new LivroModel();

        //converte o objeto DTO no objeto Model
        BeanUtils.copyProperties(livroDTO, livroModel);
        livroModel.setId(livroModelOptional.get().getId()); //mantem o uuid anterior
        livroModel.setIsbn13(livroModelOptional.get().getIsbn13()); //mantem o ISBN13 anterior
        livroModel.setCadastrado_em(livroModelOptional.get().getCadastrado_em()); //mantem a data anterior

        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livroModel));
    }

}
