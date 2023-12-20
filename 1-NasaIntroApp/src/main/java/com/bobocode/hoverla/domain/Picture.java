package com.bobocode.hoverla.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Clock;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nasa_id")
    private Long nasaId;

    @Column(name = "img_src")
    private String imageSrc;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "camera_id")
    private Camera camera;

    @Column(name = "created_at")
    private Long createdAt;


    @PrePersist
    public void prePersist() {
        createdAt = Clock.systemUTC().millis();
    }
}
