package com.reeverse.gseven.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.reeverse.gseven.repository.UserRepository;
import com.reeverse.gseven.service.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
    

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	
       http.authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/registration").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/confirm/**").permitAll()
                                .requestMatchers("/resetPassword/**").permitAll()
                                .requestMatchers("/resetPasswordProcess/**").permitAll()
                                .requestMatchers("/user/**").hasAuthority("STUDENTE").anyRequest().authenticated()
                                
    		   );
           /*     .formLogin((form) -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .permitAll()
                )
                
                .exceptionHandling(handling -> handling.accessDeniedPage("/access-denied"));*/
       return http.build();
	}
	
	
}
