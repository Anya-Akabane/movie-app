package org.example.appmovies.service;

import org.example.appmovies.model.Movie;
import org.example.appmovies.repository.MovieRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}

