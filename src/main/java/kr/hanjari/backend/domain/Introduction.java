package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "introduction")
@NoArgsConstructor
public class Introduction {

    @Id
    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @MapsId("clubId")
    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "content1")
    private String content1;

    @Column(name = "content2")
    private String content2;

    @Column(name = "content3")
    private String content3;

}
