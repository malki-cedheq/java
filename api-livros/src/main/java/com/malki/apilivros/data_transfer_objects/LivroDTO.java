package com.malki.apilivros.data_transfer_objects;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

public class LivroDTO {
    @Getter @Setter
    @NotBlank @Size(min=3, max=50)
    private String autor;
    @Getter @Setter
    @NotBlank @Size(min=3, max=100)
    private String titulo;
    @Getter @Setter
    @Min(1)
    private Integer edicao;
    @Getter @Setter
    @Min(1455) @Max(2099) //ano do primeiro livro impresso a Bíblia
    private Integer ano;
    @Getter @Setter
    @NotBlank @Size(min=14, max=14)
    private String isbn13;
    @Getter @Setter
    @NotBlank @Size(min=3, max=50)
    private String editora;

}
