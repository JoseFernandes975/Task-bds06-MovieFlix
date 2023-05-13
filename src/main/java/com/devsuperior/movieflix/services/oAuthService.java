package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.exceptions.ForbiddenException;
import com.devsuperior.movieflix.exceptions.UnauthorizedException;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class oAuthService {
	
	@Autowired 
	private UserRepository repo;
	
	public User isAuthenticated() {
		try {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		User username = repo.findByEmail(user);
		return username;
		}catch(Exception e) {
			throw new UnauthorizedException("Unauthorized! Not logged!" + e.getMessage());
		}
	}
	
	public void validateUserAndMember(Long id) {
		User user = isAuthenticated();
		if(!user.getId().equals(id) && !user.hasRole("ROLE_MEMBER")) {
		   	throw new ForbiddenException("Not Member! Not Permission!");
		}
	}
}
