package org.example.appmovies.controller;

import org.example.appmovies.model.Movie;
import org.example.appmovies.service.MovieService;
import org.junit.jupiter.api.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MovieService movieService;

    @Test
    void shouldCreateMovie() throws Exception {
        when(movieService.createMovie(any(Movie.class)))
                .thenReturn(new Movie());

        mockMvc.perform(post("/movies")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
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
