package org.example.hackathon_de02.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hackathon_de02.model.entity.*;
import org.example.hackathon_de02.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializeService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ViewerRepository viewerRepository;

    @PostConstruct
    public void initializeDatabase() {
        if (genreRepository.count() == 0) {
            System.out.println("Initializing generic data for De02...");
            Genre c1 = genreRepository.save(new Genre(null, "Type A", "Description A"));
            Genre c2 = genreRepository.save(new Genre(null, "Type B", "Description B"));
            
            movieRepository.saveAll(List.of(
                new Movie(null, "Item 1", "Desc 1", new BigDecimal("100000"), 50, null, c1),
                new Movie(null, "Item 2", "Desc 2", new BigDecimal("200000"), 30, null, c2)
            ));
            
            viewerRepository.saveAll(List.of(
                new Viewer(null, "User A", "0901234567", "a@example.com", "Address A"),
                new Viewer(null, "User B", "0912345678", "b@example.com", "Address B")
            ));
        }
    }
}
