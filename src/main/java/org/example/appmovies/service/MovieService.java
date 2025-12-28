package org.example.appmovies.service;

import org.example.appmovies.model.Movie;

import java.util.List;

public interface MovieService {
    Movie createMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);


}
