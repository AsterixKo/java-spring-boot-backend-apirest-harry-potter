package com.harrypotter.springboot.backend.apirest.models.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.harrypotter.springboot.backend.apirest.models.entity.Movie;

public final class UtilMovie {

	private UtilMovie() {
	}

	private static final int getRandomNumber(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}

	public static final void putRandomStars(List<Movie> movies) {
		movies.forEach(m -> m.setStars(getRandomNumber(1, 10)));
	}
}
