package com.malki.apilivros.models;

import lombok.Getter; //https://projectlombok.org/features/GetterSetter
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_LIVROS") //https://en.wikibooks.org/wiki/Java_Persistence/Relationships
public class LivroModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private UUID id;
    @Column(nullable = false, length = 50)
    @Getter @Setter
    private String autor;
    @Column(nullable = false, length = 100)
    @Getter @Setter
    private String titulo;
    @Column(nullable = false)
    @Getter @Setter
    private Integer edicao;
    @Column(nullable = false)
    @Getter @Setter
    private Integer ano;
    @Column(nullable = false, length = 50)
    @Getter @Setter
    private String editora;
    @Column(nullable = false, unique = true, length = 14)
    @Getter @Setter
    private String isbn13;
    @Column(nullable = false)
    @Getter @Setter
    private LocalDateTime cadastrado_em;

}
