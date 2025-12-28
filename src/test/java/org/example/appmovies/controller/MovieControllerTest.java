package org.example.appmovies.controller;

import org.junit.jupiter.api.Test;
import org.example.appmovies.model.Movie;
import org.example.appmovies.service.MovieService;
import org.junit.jupiter.api.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.example.appmovies.exception.MovieNotFoundException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @org.junit.jupiter.api.Test
    void shouldGetAllMovies() throws Exception {
        Movie movie1 = new Movie();
        movie1.setTitle("Inception");

        Movie movie2 = new Movie();
        movie2.setTitle("Interstellar");

        when(movieService.getAllMovies())
                .thenReturn(Arrays.asList(movie1, movie2));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[1].title").value("Interstellar"));
    }

    @Test
    void shouldGetMovieById() throws Exception {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");

        when(movieService.getMovieById(1L))
                .thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void shouldReturn404WhenMovieNotFound() throws Exception {
        when(movieService.getMovieById(999L))
                .thenThrow(new MovieNotFoundException(999L));

        mockMvc.perform(get("/movies/999"))
                .andExpect(status().isNotFound());
    }
}
