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
@Table(name = "camera")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nasa_id")
    private Long nasaId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Long createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = Clock.systemUTC().millis();
    }

}
