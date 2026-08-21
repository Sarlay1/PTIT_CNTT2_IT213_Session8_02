package org.example.hackathon_de02.controller;

import org.example.hackathon_de02.model.entity.Movie;
import org.example.hackathon_de02.repository.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm"));
        return ResponseEntity.ok(movie);
    }
}
