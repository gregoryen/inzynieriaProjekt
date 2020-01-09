package com.engineering.shop;

import com.engineering.shop.fileUpload.property.FileStorageProperties;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class RestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }
    
    @Bean
    public FilterRegistrationBean simpleCorsFilter() {
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	CorsConfiguration config = new CorsConfiguration();
    	config.setAllowCredentials(true);
    	
    	config.setAllowedOrigins(Collections.singletonList("http://localhost:8081"));
    	config.setAllowedMethods(Collections.singletonList("*"));
    	config.setAllowedHeaders(Collections.singletonList("*"));
    	source.registerCorsConfiguration("/**", config);
    	FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    	bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    	
    	return bean;
    }
}


