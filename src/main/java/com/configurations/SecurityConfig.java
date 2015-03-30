package com.configurations;

import com.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 NICE TUTORIAL: http://kielczewski.eu/2014/12/spring-boot-security-application/
 If you want CSFR: http://docs.spring.io/spring-security/site/docs/3.2.x/reference/htmlsingle/#csrf
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationService userDetailsService;

  /*
   Basic Authentication configuration for both WEB and REST.
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
   Login authentication with Login/Logout pages for WEB ONLY.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
            .antMatchers("/", "/js/**", "/css/**", "/images/**", "/webjars/**").permitAll()
            .anyRequest().fullyAuthenticated()
            .and()
            .formLogin()
            .loginPage("/login").failureUrl("/login?error").permitAll()
            .and()
            .logout().logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/").permitAll()
            .and()
            .rememberMe();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }
}
