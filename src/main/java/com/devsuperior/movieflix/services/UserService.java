package com.devsuperior.movieflix.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);
 	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private oAuthService auth;
	
	public User findByEmail() {
		User user = auth.isAuthenticated();
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = repository.findByEmail(username);
		if(user == null) {
			logger.error("Email not found: " + username);
			throw new UsernameNotFoundException("User Not Found!");
		}
		logger.info("Email found: " + username);
		return user;
	}

	

}
