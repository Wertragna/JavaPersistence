package com.bobocode.hoverla.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CameraRepository extends JpaRepository<Camera, Long> {
    Optional<Camera> findByNasaId(Long nasaId);
}
