package com.harrypotter.springboot.backend.apirest.models.services;

import java.util.List;

import com.harrypotter.springboot.backend.apirest.models.entity.Movie;

public interface IMovieService {

	public List<Movie> findAll();
	
	public Movie findById(Long id);
	
	public List<Movie> save(List<Movie> movies);
	
	public Movie save(Movie movie);
	
	public void delete(Long id);
}
