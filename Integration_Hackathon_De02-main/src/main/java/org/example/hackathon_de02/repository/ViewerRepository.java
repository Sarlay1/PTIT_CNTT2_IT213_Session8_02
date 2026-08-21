package org.example.hackathon_de02.repository;

import org.example.hackathon_de02.model.entity.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewerRepository extends JpaRepository<Viewer, Long> {
    Optional<Viewer> findByPhone(String phone);
}
