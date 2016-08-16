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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/*
 * http://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html
 * NICE TUTORIAL: http://kielczewski.eu/2014/12/spring-boot-security-application/
 * http://www.baeldung.com/spring-mvc-static-resources
 */
@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	/*
   * This registers the /login and errors views withoput the need for a controller.
	 * You can add more errors here as needed and use them in GlobalControllerExceptionHandler.java
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/error/404").setViewName("error/404");
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
   Security configuration for rest API with path "/rest/**"
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
			http.csrf()
							.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
							.and()
							.authorizeRequests()
							.antMatchers("admin/**").hasRole("ADMIN")
							.anyRequest().fullyAuthenticated()
							.and()
							.formLogin()
							.loginPage("/login").failureUrl("/login?error").permitAll()
							.and()
							.logout().deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll()
							.and()
							.rememberMe();
		}

		/*
     * This is needed because defaults are disabled by @EnableWebSecurity
		 * http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-security.html
     * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
		 * http://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html
		 */
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/images/**", "/css/**", "/js/**", "/webjars/**");
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
