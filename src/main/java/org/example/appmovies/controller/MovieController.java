package org.example.appmovies.controller;

import org.example.appmovies.model.Movie;
import org.example.appmovies.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return service.createMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }
}
