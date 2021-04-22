package com.harrypotter.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchMovie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("Search")
	public List<Movie> searchList;

	public List<Movie> getSearchList() {
		return searchList;
	}

	public void setSearchList(List<Movie> searchList) {
		this.searchList = searchList;
	}

	@Override
	public String toString() {
		return searchList == null ? "SearchMovie [searchList=null]"
				: "SearchMovie [searchList="
						+ searchList.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
	}

}
