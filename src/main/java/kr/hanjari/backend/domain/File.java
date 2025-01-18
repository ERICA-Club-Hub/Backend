package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file")
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_key", nullable = false)
    private String file_key;

    @Column(name = "extention", nullable = false)
    private String extention;

    @Column(name = "size", nullable = false)
    private Integer size;
}
