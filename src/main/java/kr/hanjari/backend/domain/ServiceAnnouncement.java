package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.common.BaseEntity;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_announcement")
@NoArgsConstructor
public class ServiceAnnouncement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

}
