package com.harrypotter.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.harrypotter.springboot.backend.apirest.models.entity.Movie;

public interface IMovieDao extends CrudRepository<Movie, Long> {

}
