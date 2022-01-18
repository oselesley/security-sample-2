package com.sq009.springsecuritydemo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.sq009.springsecuritydemo.filters.JWTDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@PropertySources({@PropertySource(value = "classpath:jwt.properties")})
public class JWTConfig {
	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.tokenPrefix}")
	private String tokenPrefix;
	@Value("${jwt.expirationDate}")
	private Long expirationDate;

	@Bean
	public JWTDataSource getJwtDataSource() {
		JWTDataSource jwtDataSource = new JWTDataSource();
		jwtDataSource.setSecretKey(secretKey);
		jwtDataSource.setTokenPrefix(tokenPrefix);
		jwtDataSource.setExpirationDate(expirationDate);

		return jwtDataSource;
	}
}
