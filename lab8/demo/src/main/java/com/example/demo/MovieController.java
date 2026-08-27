package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin("*")
@Slf4j
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @GetMapping
    public List<Movie> getAllMovies() {
        log.info("Отримано запит на список всіх фільмів");
        return repository.findAll();
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        log.info("Отримано запит на створення фільму: {}", movie.getName());
        return repository.save(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        log.info("Отримано запит на оновлення фільму з id: {}", id);
        return repository.findById(id)
                .map(movie -> {
                    movie.setName(movieDetails.getName());
                    movie.setDescription(movieDetails.getDescription());
                    movie.setImageUrl(movieDetails.getImageUrl());
                    Movie updatedMovie = repository.save(movie);
                    log.info("Фільм з id: {} успішно оновлено", id);
                    return ResponseEntity.ok(updatedMovie);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.info("Отримано запит на видалення фільму з id: {}", id);
        repository.deleteById(id);
        log.info("Фільм з id: {} успішно видалено", id);
        return ResponseEntity.noContent().build();
    }
}