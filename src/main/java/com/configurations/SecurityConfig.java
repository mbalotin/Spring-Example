package com.configurations;

import com.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 http://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html
 NICE TUTORIAL: http://kielczewski.eu/2014/12/spring-boot-security-application/
 */
@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	/*
   This registers the /login view without the need for a controller
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	/*
   These @Beans add configurations to Spring Security's defaults
	 */
	@Bean
	public WebAppSecurityConfig webAppSecurityConfig() {
		return new WebAppSecurityConfig();
	}

	@Bean
	public RestApiSecurityConfig restApiSecurityConfig() {
		return new RestApiSecurityConfig();
	}

	@Bean
	public AuthenticationSecurity authenticationSecurity() {
		return new AuthenticationSecurity();
	}

	/*
   Security configuration for rest API with path "/api/**"
   This security should be tested first
	 */
	@Order(1)
	protected static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
							.antMatcher("/rest/**").authorizeRequests()
							.antMatchers("/rest/admin/**").hasRole("ADMIN")
							.anyRequest().authenticated()
							.and()
							.httpBasic();
		}
	}

	/*
   Security configuration for web content
   SecurityProperties.ACCESS_OVERRIDE_ORDER overrides the access rules without changing any other autoconfigured features
	 */
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
							.authorizeRequests()
							.antMatchers("/readme").permitAll()
							.antMatchers("/error/**").permitAll()
							.antMatchers("admin/**").hasRole("ADMIN")
							.anyRequest().fullyAuthenticated()
							.and()
							.formLogin()
							.loginPage("/login").failureUrl("/login?error").permitAll()
							.and()
							.logout().logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll()
							.and()
							.rememberMe();
		}

		/*
     This is needed until the issue is resolved.
     https://github.com/spring-projects/spring-boot/issues/2460
		 */
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/webjars/**");
		}
	}

	/*
   Adding password enconding
	 */
	@Order(Ordered.HIGHEST_PRECEDENCE + 10)
	protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private AuthenticationService userDetailsService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}
	}

}
