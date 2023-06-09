package com.testgame.gseven.utility.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.testgame.gseven.model.service.utils.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@SuppressWarnings("unused")
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	
       http.authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/registration").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/confirm").permitAll()
                                .requestMatchers("/resetPassword").permitAll()
                                .requestMatchers("/resetPasswordProcess").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/dashboard").hasAuthority("STUDENTE")
                                .anyRequest().permitAll()
                                
    		   )
                .formLogin((form) -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/dashboard")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .permitAll()
                )
       			.logout((logout) -> logout
       							.logoutUrl("/logout")
       							.logoutSuccessUrl("/login")
       							.permitAll())
       			.exceptionHandling(handling -> handling.accessDeniedPage("/access-denied"));
       return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
    
}
