package org.example.appmovies.service;

import org.example.appmovies.exception.MovieNotFoundException;
import org.example.appmovies.model.Movie;
import org.example.appmovies.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public void deleteMovie(Long id) {
        if (!repository.existsById(id)) {
            throw new MovieNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        Movie existing = repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        existing.setTitle(movie.getTitle());
        existing.setGenre(movie.getGenre());
        existing.setDuration(movie.getDuration());
        existing.setReleaseYear(movie.getReleaseYear());

        return repository.save(existing);
    }
}

