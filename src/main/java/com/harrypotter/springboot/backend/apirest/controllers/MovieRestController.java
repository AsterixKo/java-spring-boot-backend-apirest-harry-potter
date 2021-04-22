package com.harrypotter.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.harrypotter.springboot.backend.apirest.models.entity.Movie;
import com.harrypotter.springboot.backend.apirest.models.entity.SearchMovie;
import com.harrypotter.springboot.backend.apirest.models.services.IMovieService;
import com.harrypotter.springboot.backend.apirest.models.util.UtilMovie;

@CrossOrigin(origins = { "http://localhost:4200" })
@Validated
@RestController
@RequestMapping("/api")
public class MovieRestController {

	public static final Logger logger = LoggerFactory.getLogger(MovieRestController.class);

	@Autowired
	private IMovieService movieService;
	@Value("${api.key.omdb}")
	private String apiKey;

	@GetMapping("/movies")
	public List<Movie> index() {
		return movieService.findAll();
	}

	@GetMapping("/searchAndPopulate/{q}")
	public ResponseEntity<?> searchAndPopulateDB(@PathVariable String q) {

		logger.info("apiKey= {}", apiKey);
		logger.info("query= {}", q);

		SearchMovie searchMovie = null;
		List<Movie> listNewMovies = null;
		Map<String, Object> response = new HashMap<>();

		try {
			final String uri = "http://www.omdbapi.com/?apikey={apiKey}&s={q}&type=movie&r=json";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<SearchMovie> result = restTemplate.getForEntity(uri, SearchMovie.class, apiKey, q);
			logger.info(result.toString());
			
			searchMovie = result.getBody();
			
			if(searchMovie.getSearchList() == null ||searchMovie.getSearchList().isEmpty()) {
				response.put("mensaje", "La búsqueda no tiene resultados");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			UtilMovie.putRandomStars(searchMovie.getSearchList());//Ponemos valoraciones aleatorias entre 1 y 10
			listNewMovies = movieService.save(searchMovie.getSearchList());
			
			logger.info(searchMovie.toString());
			
		}catch(RestClientException e) {
			response.put("mensaje", "Error al realizar la petición a la api http://www.omdbapi.com");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La búsqueda y la creación en nuestra base de datos ha sido exitosa!");
		response.put("movies", listNewMovies);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/movies/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Movie movie = null;
		Map<String, Object> response = new HashMap<>();

		try {
			movie = movieService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (movie == null) {
			response.put("mensaje", "El movie ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

	@PostMapping("/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody  Movie movie) {

		Movie movieNew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			movieNew = movieService.save(movie);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El movie ha sido creado con éxito!");
		response.put("movie", movieNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/movies/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Movie movie, @PathVariable Long id) {

		Movie movieActual = movieService.findById(id);
		Movie movieUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (movieActual == null) {
			response.put("mensaje", "Error: no se puede editar, el movie ID: ".concat(id.toString())
					.concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			movieActual.setImdbID(movie.getImdbID());
			movieActual.setTitle(movie.getTitle());
			movieActual.setYear(movie.getYear());
			movieActual.setStars(movie.getStars());

			movieUpdated = movieService.save(movieActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el movie en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El movie ha sido actualizado con éxito!");
		response.put("movie", movieUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/movies/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			movieService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el movie en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El movie eliminado con exito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
