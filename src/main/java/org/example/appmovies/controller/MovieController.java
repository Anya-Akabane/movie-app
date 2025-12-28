package org.example.appmovies.controller;

import org.example.appmovies.model.Movie;
import org.example.appmovies.service.MovieService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
