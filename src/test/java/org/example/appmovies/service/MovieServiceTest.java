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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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



    @Test
    void shouldDeleteMovie() {
        Movie movie = new Movie();
        movie.setId(1L);

        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));

        movieService.deleteMovie(1L);

        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentMovie() {
        when(movieRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> {
            movieService.deleteMovie(999L);
        });
    }

    @Test
    void shouldUpdateMovie() {
        Movie existingMovie = new Movie();
        existingMovie.setId(1L);
        existingMovie.setTitle("Inception");

        Movie updatedData = new Movie();
        updatedData.setTitle("Inception Updated");
        updatedData.setGenre("Thriller");
        updatedData.setDuration(150);
        updatedData.setReleaseYear(2010);

        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Movie result = movieService.updateMovie(1L, updatedData);

        assertEquals("Inception Updated", result.getTitle());
        assertEquals("Thriller", result.getGenre());
        assertEquals(150, result.getDuration());
        assertEquals(2010, result.getReleaseYear());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentMovie() {
        Movie updatedData = new Movie();
        updatedData.setTitle("New Title");

        when(movieRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> {
            movieService.updateMovie(999L, updatedData);
        });
    }
}

