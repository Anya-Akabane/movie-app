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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


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

    @Test
    void shouldDeleteMovie() throws Exception {
        doNothing().when(movieService).deleteMovie(1L);

        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentMovie() throws Exception {
        doThrow(new MovieNotFoundException(999L))
                .when(movieService).deleteMovie(999L);

        mockMvc.perform(delete("/movies/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldUpdateMovie() throws Exception {
        Movie updatedMovie = new Movie();
        updatedMovie.setId(1L);
        updatedMovie.setTitle("Inception Updated");
        updatedMovie.setGenre("Thriller");
        updatedMovie.setDuration(150);
        updatedMovie.setReleaseYear(2010);

        when(movieService.updateMovie(eq(1L), any(Movie.class)))
                .thenReturn(updatedMovie);

        mockMvc.perform(put("/movies/1")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content("""
                {
                  "title": "Inception Updated",
                  "genre": "Thriller",
                  "duration": 150,
                  "releaseYear": 2010
                }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception Updated"))
                .andExpect(jsonPath("$.genre").value("Thriller"))
                .andExpect(jsonPath("$.duration").value(150))
                .andExpect(jsonPath("$.releaseYear").value(2010));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentMovie() throws Exception {
        when(movieService.updateMovie(eq(999L), any(Movie.class)))
                .thenThrow(new MovieNotFoundException(999L));

        mockMvc.perform(put("/movies/999")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content("""
                {
                  "title": "New Title",
                  "genre": "Drama",
                  "duration": 120,
                  "releaseYear": 2020
                }
                """))
                .andExpect(status().isNotFound());
    }
}
