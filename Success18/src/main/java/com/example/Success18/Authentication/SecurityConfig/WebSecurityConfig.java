package com.example.Success18.Authentication.SecurityConfig;

import com.example.Success18.Authentication.UserDetails.DetailsOfUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity //declares one or more @Bean
@EnableGlobalMethodSecurity(// securedEnabled = true,// jsr250Enabled = true,@postAuthorize, @preAuthorize, @Secure
        prePostEnabled = true)


//todo: It's responsible for setting up the security framework,
// defining authentication and authorization rules, and configuring CORS (Cross-Origin Resource Sharing) settings
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${users.app.client.origin_url}")
    private String origin;
    @Value("${users.app.client.origin_ip}")
    private String client_origin_ip;
    @Value("${users.app.client.origin_52_ip}")
    private String client_origin_52_ip;
    @Autowired
    DetailsOfUsersService detailsOfUsersService;

    @Autowired
    private JwtAuthenticationCommencing jwtAuthenticationCommencing;

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    //todo: Uses a custom userDetailsService and a password encoder
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(detailsOfUsersService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    //todo:Responsible for Authenticating the user.
    // Overrides the authenticationBean() to expose authenticationManager as a bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    //todo: Responsible for encoding passwords
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    //todo: configures the HttpsSecurity to define which URL paths should be secured and how.
    // Enables CORS and disables the CSRF protection
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationCommencing).and()
                //todo:Server does not does not maintain sessions state(stateless)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/v1/auth/signin/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/v1/auth/refreshtoken/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/v1/auth/otp/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/v1/auth/reset-password/**").permitAll().and()
                .authorizeRequests().antMatchers("/api/v1/auth/forgot-password/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .anyRequest().permitAll();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    //todo:Configures CORS settings to allow requests from specific origins and headers.
        // todo:It uses a CorsConfiguration object to define the allowed origins, headers, and methods.
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin(origin);
        config.addAllowedOrigin(client_origin_ip);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }}
