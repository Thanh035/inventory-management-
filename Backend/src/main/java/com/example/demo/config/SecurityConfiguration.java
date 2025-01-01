package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.constants.RolesConstants;
import com.example.demo.security.jwt.JWTConfigurer;
import com.example.demo.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final TokenProvider tokenProvider;

	private final CorsFilter corsFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable().addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
//					.authenticationEntryPoint(problemSupport)
//					.accessDeniedHandler(problemSupport)
				.and().headers()
				.contentSecurityPolicy(
						"default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
				.and().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN).and()
				.permissionsPolicy()
				.policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
				.and().frameOptions().sameOrigin().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().antMatchers("/app/**/*.{js,html}").permitAll()
				.antMatchers("/i18n/**").permitAll().antMatchers("/**").permitAll()
				.antMatchers("/swagger-ui/**").permitAll().antMatchers("/test/**").permitAll()
				.antMatchers("/api/v1.0/authenticate").permitAll().antMatchers("/api/v1.0/register").permitAll()
				.antMatchers("/api/v1.0/activate").permitAll().antMatchers("/api/v1.0/account/reset-password/init")
				.permitAll().antMatchers("/api/v1.0/account/reset-password/finish").permitAll()
				.antMatchers("/api/v1.0/admin/**").hasAuthority(RolesConstants.ADMIN).antMatchers("/api/v1.0/**")
				.permitAll().and().httpBasic().and().apply(securityConfigurerAdapter());

//		http.requiresChannel(channel -> channel.anyRequest().requiresSecure())
//				.authorizeRequests(authorize -> authorize.anyRequest().permitAll());

		return http.build();
		// @formatter:on
	}

}
