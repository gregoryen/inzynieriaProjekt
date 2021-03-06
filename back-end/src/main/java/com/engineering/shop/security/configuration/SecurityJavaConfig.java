package com.engineering.shop.security.configuration;

import com.engineering.shop.security.RestAuthenticationEntryPoint;
import com.engineering.shop.security.SavedRequestAwareAuthenticationSuccessHandler;
import com.engineering.shop.security.jwt.JwtRequestFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    public SecurityJavaConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter, RestAuthenticationEntryPoint authenticationEntryPoint, SavedRequestAwareAuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.successHandler = successHandler;
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));

        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers("/signUp", "/logIn").permitAll()


                .antMatchers("/bucket", "/bucket/**").permitAll()
                .antMatchers("/bucketPosition", "/bucketPosition/**").permitAll()
                .antMatchers("/order", "/order/**").permitAll()
                .antMatchers("/product", "/product/**").permitAll()
                .antMatchers("/bucketProduct", "/bucketProduct/**").permitAll()
                .antMatchers("/cart", "/cart/**").permitAll()
                .antMatchers("/payment","/payment/**").permitAll()
                .antMatchers("/images/**", "/products/**").permitAll()
                .antMatchers("/uploadFile/**", "/uploadMultipleFiles/**","/products/**","/downloadFile/**").permitAll()
                .antMatchers("/suppliers/**", "/stock_amounts/**", "/supplies/**", "/reports/**").permitAll()
                .antMatchers("/images/**", "/downloadFile/**").permitAll()
                .antMatchers("/products/**", "/categories/**").permitAll()
                .antMatchers("/opinions", "/opinions/**").permitAll()
                .antMatchers("/changeUserRole").permitAll()
                .antMatchers("/changeRolePrivileges").permitAll()
                .antMatchers("/getUserPrivileges").permitAll()
                .antMatchers("/createRoleByName").permitAll()
                .antMatchers("/deleteRole").permitAll()
                .antMatchers("/deleteUserByEmail").permitAll()
                .antMatchers("/advImages","/advImages/**").permitAll()
                .antMatchers("/advertisement","/advertisement/getAll").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successHandler).failureHandler(failureHandler()).and()
                .logout();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


}

