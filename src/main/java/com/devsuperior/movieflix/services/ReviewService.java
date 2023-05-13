package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dtos.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository repo;
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired
	private oAuthService service;
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	public ReviewDTO insertReview(ReviewDTO dto){
		Review entity = new Review();
		User user = service.isAuthenticated();
		user = service.isAuthenticated();
		service.validateUserAndMember(user.getId());
		entity.setId(dto.getId());
		entity.setText(dto.getText());
		entity.setUser(user);
		Movie movie = movieRepo.getOne(dto.getMovieId());
		entity.setMovie(movie);
		repo.save(entity);
		return new ReviewDTO(entity);
	}

}
