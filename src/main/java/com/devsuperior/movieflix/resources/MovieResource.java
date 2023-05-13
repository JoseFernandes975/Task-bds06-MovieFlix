package com.devsuperior.movieflix.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dtos.MovieDTO;
import com.devsuperior.movieflix.dtos.MovieDetailsDTO;
import com.devsuperior.movieflix.dtos.MovieReviewDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieResource {

	@Autowired
	private MovieService service;
	
	@GetMapping
	public ResponseEntity<Page<MovieDTO>> findByGenre(@RequestParam(name = "genreId", defaultValue = "0") Long genreId, Pageable pageable){
		Page<MovieDTO> page = service.findByGenre(genreId, pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id){
	 MovieDetailsDTO dto = service.findByIdMovieDetails(id);
	 return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/{id}/reviews")
	public ResponseEntity<MovieReviewDTO> findMovieWithReviews(@PathVariable Long id){
		 MovieReviewDTO dto = service.findMovieWithReviews(id);
		 return ResponseEntity.ok().body(dto);
	}
}
