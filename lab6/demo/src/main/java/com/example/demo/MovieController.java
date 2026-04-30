package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin("*")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return repository.save(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        return repository.findById(id)
                .map(movie -> {
                    movie.setName(movieDetails.getName());
                    movie.setDescription(movieDetails.getDescription());
                    movie.setImageUrl(movieDetails.getImageUrl());
                    Movie updatedMovie = repository.save(movie);
                    return ResponseEntity.ok(updatedMovie);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}