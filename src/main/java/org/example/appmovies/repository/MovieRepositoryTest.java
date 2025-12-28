package org.example.appmovies.repository;

import org.example.appmovies.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;

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
}
