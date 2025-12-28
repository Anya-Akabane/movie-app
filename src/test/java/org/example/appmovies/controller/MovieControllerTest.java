package org.example.appmovies.controller;

import org.example.appmovies.model.Movie;
import org.example.appmovies.service.MovieService;
import org.junit.jupiter.api.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @Test
    void shouldCreateMovie() throws Exception {
        when(movieService.createMovie(any(Movie.class)))
                .thenReturn(new Movie());

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "title": "Inception",
                  "genre": "Sci-Fi",
                  "duration": 148,
                  "releaseYear": 2010
                }
                """))
                .andExpect(status().isOk());
    }
}
