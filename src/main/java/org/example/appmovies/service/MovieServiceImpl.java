package org.example.appmovies.service;

import org.example.appmovies.model.Movie;
import org.example.appmovies.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movie createMovie(Movie movie) {
        return repository.save(movie);
    }
}

