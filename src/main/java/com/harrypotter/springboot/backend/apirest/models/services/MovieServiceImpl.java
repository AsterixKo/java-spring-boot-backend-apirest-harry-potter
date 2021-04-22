package com.harrypotter.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harrypotter.springboot.backend.apirest.models.dao.IMovieDao;
import com.harrypotter.springboot.backend.apirest.models.entity.Movie;

@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieDao movieDao;

	@Override
	@Transactional(readOnly = true)
	public List<Movie> findAll() {
		return (List<Movie>) movieDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Movie findById(Long id) {
		return movieDao.findById(id).orElse(null);
	}

	@Override()
	@Transactional()
	public List<Movie> save(List<Movie> movies) {
		return (List<Movie>) movieDao.saveAll(movies);
	}

	@Override
	@Transactional()
	public Movie save(Movie movie) {
		return movieDao.save(movie);
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		movieDao.deleteById(id);
	}

}
