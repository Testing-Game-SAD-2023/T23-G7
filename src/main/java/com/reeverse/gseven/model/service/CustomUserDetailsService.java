package com.reeverse.gseven.model.service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reeverse.gseven.model.dto.Role;
import com.reeverse.gseven.model.dto.Student;
import com.reeverse.gseven.model.dao.IStudentDAO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private IStudentDAO userRepository;

	@Autowired
	public CustomUserDetailsService(IStudentDAO userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
	
		Student studentUser = userRepository.findByEmail(email);
		
		if(studentUser != null) {
			return new User(studentUser.getEmail(), studentUser.getPassword(),
								mapRolesToAuthorities(studentUser.getRoles()));
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
	
	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
	}
}
