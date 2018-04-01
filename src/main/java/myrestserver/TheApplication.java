package myrestserver;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
@RestController
public class TheApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TheApplication.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(TheApplication.class, args);
    }

    @GetMapping(value = {"/", "/index.html"})
    public String index() {
        return "MyRestServer ...";
    }
	
	@GetMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}

	@GetMapping("/resource")
	@ResponseBody
	public Map<String, Object> resource() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}	
		
	@Configuration
	//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	//@Order(SecurityProperties.BASIC_AUTH_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.cors()
					.and()
				.httpBasic()
					.and()
				.authorizeRequests()
					.antMatchers("/index.html", "/", "/user", "/login", "/logout", "/tassonomia").permitAll()
					.antMatchers(HttpMethod.OPTIONS).permitAll()
					.anyRequest().authenticated()
					.and()
				.csrf()
					.ignoringAntMatchers("/login","/logout")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				;
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth
				.ldapAuthentication()
					.userDnPatterns("uid={0},ou=people")
					.groupSearchBase("ou=groups")
					.contextSource()
					.url("ldap://localhost:10389/dc=springframework,dc=org")
					.and()
					.passwordCompare()
					.passwordEncoder(new LdapShaPasswordEncoder())
					.passwordAttribute("userPassword");
			
			auth
				.inMemoryAuthentication()
					.withUser(User.withUsername("user").password("{noop}password").roles("USER").build())
					.withUser(User.withUsername("admin").password("{noop}password").roles("USER", "ADMIN").build());
			
			auth.authenticationProvider(new TheAuthenticationProvider());
		}		

		@Bean
		CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowCredentials(true);
			configuration.addAllowedOrigin("*");
			configuration.addAllowedMethod("*");
			configuration.addAllowedHeader("*");
			configuration.setMaxAge(3600L);
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}	
	}	
}
