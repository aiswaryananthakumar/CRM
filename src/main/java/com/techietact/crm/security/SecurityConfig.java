package com.techietact.crm.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("CRMUserDetailsService")
	UserDetailsService userDetailsService;


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager getAuthenticationManagerBean() throws Exception {
		return authenticationManagerBean();
	}
	

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
		
	
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http		
		.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/assets/**").permitAll() 
		//.antMatchers("/list").hasAnyRole("ADMIN","CUSTOMER_VIEW")
		//.antMatchers("/lead/list").hasAnyRole("ADMIN","LEAD_VIEW")
		//.antMatchers("/resources/**").permitAll()		 
		.and() 
		 .formLogin() 
		 .loginPage("/login").permitAll() 
	      
		  .loginProcessingUrl("/login").usernameParameter("userName").passwordParameter("password")
		  .permitAll() 
		  .defaultSuccessUrl("/home", true)
	      .failureUrl("/login?error=true")
		  .and() 
		  .logout()
	      .logoutSuccessUrl("/login?logout=true")
	      .permitAll()
		  .and()
		  .exceptionHandling()
		  .accessDeniedPage("/login");
		  }

}