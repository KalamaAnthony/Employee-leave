package com.example.Success18.Authentication.SecurityConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//TODO: Used to handle authentication exceptions. Used with security config to specify authentication exceptions should be handled
public class JwtAuthenticationCommencing implements AuthenticationEntryPoint {


//todo: for log messages
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationCommencing.class);

    //TODO:Where authentication of the user starts(called when unauthenticated user tries to access a protectes resource )
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}" ,authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");

    }

}