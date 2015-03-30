package com.configurations;

import com.controllers.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * TODO: CAN THIS BE IMPROVED WITH PROPERTIES OR ANNOTATIONS?
 * SPRING HAS SECURITY PROPERTIES THAT SEEN TO DO WHAT HERE IS DONE PROGRAMATICALLY;
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthController userDetailsService;

  /*
   * Basic Authentication configuration for both WEB and REST.
   */
  /*
   @Override
   protected void configure(HttpSecurity http) throws Exception {
   http.csrf().disable().authorizeRequests()
   .antMatchers("/").permitAll()
   .antMatchers("/**").authenticated()
   .and()
   .httpBasic();
   }
   */

  /*
   * Login authentication with Login/Logout pages for WEB ONLY.
   * CSRF is enabled and is not needed in REST.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/", "/resources/**", "/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and().logout().permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
  }
}
