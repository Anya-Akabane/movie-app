package org.example.appmovies.repository;

import org.example.appmovies.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.assertNotNull;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void shouldSaveMovie() {
        Movie movie = new Movie();
        movie.setTitle("Inception");

        Movie saved = movieRepository.save(movie);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldFindAllMovies() {
        Movie movie1 = new Movie();
        movie1.setTitle("Inception");
        movieRepository.save(movie1);

        Movie movie2 = new Movie();
        movie2.setTitle("Interstellar");
        movieRepository.save(movie2);

        List<Movie> movies = movieRepository.findAll();

        assertEquals(2, movies.size());
    }

    @Test
    void shouldReturnEmptyWhenMovieNotFound() {
        Optional<Movie> found = movieRepository.findById(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldDeleteMovie() {
        Movie movie = new Movie();
        movie.setTitle("Inception");
        Movie saved = movieRepository.save(movie);

        movieRepository.deleteById(saved.getId());

        Optional<Movie> found = movieRepository.findById(saved.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldUpdateMovie() {
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setGenre("Sci-Fi");
        Movie saved = movieRepository.save(movie);

        saved.setTitle("Inception Updated");
        saved.setGenre("Thriller");
        Movie updated = movieRepository.save(saved);

        assertEquals("Inception Updated", updated.getTitle());
        assertEquals("Thriller", updated.getGenre());
        assertEquals(saved.getId(), updated.getId());
    }
}
