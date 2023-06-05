package com.reeverse.gseven.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.reeverse.gseven.model.User;
import com.reeverse.gseven.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
	
		User user = userRepository.findByEmail(email);
		
		if(user != null) {
			return new CustomUserDetails(user);
			/*
			return new org.springframework.security.core.userdetails.User(user.getEmail()
                    , user.getPassword(),
                    user.getRoles().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));		*/
		}else {
			throw new UsernameNotFoundException("Invalid email or password");
		}
	}
}
