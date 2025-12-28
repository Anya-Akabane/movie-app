package org.example.appmovies.service;

import org.example.appmovies.exception.MovieNotFoundException;
import org.example.appmovies.model.Movie;
import org.example.appmovies.repository.MovieRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @Test
    void shouldCreateMovie() {
        Movie movie = new Movie();
        movie.setTitle("Inception");

        when(movieRepository.save(any(Movie.class)))
                .thenReturn(movie);

        Movie result = movieService.createMovie(movie);

        assertEquals("Inception", result.getTitle());
    }

    @Test
    void shouldGetAllMovies() {
        Movie movie1 = new Movie();
        movie1.setTitle("Inception");

        Movie movie2 = new Movie();
        movie2.setTitle("Interstellar");

        when(movieRepository.findAll())
                .thenReturn(Arrays.asList(movie1, movie2));

        List<Movie> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        assertEquals("Interstellar", result.get(1).getTitle());


    }

    @Test
    void shouldGetMovieById() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");

        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);

        assertEquals("Inception", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenMovieNotFound() {
        when(movieRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> {
            movieService.getMovieById(999L);
        });
    }
}

