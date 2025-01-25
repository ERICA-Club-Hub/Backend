package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.common.BaseEntity;
import lombok.*;

@Entity
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

}
