package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dtos.MovieDTO;
import com.devsuperior.movieflix.dtos.MovieDetailsDTO;
import com.devsuperior.movieflix.dtos.MovieReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private GenreRepository genreRepo;
	
	@Autowired
	private oAuthService auth;
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findByGenre(Long genreId, Pageable pageable){
		Genre genre = (genreId == 0) ? null : genreRepo.getOne(genreId);
		Page<Movie> page = repository.findByGenre(genre, pageable);
		return page.map(x -> new MovieDTO(x));
	}

	@Transactional(readOnly = true)
	public MovieDetailsDTO findByIdMovieDetails(Long id) {
	Optional<Movie> obj = repository.findById(id);
	Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
	return new MovieDetailsDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public MovieReviewDTO findMovieWithReviews(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
		return new MovieReviewDTO(entity);
	}
	

}
