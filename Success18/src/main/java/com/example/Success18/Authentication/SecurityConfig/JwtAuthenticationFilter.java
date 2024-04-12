package com.example.Success18.Authentication.SecurityConfig;

import com.example.Success18.Authentication.UserDetails.DetailsOfUsersService;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@Component //we can also use @Service or @Repository
//@RequiredArgsConstructor //Forms a constructor for any final field declared inside the class.

//todo:This filter is designed to intercept HTTP requests, extracts a JWT (JSON Web Token) from the request header,
// validate the token, and then set the authentication context for the request
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;

    @Autowired
    DetailsOfUsersService detailsOfUsersService;
    //makes sure everytime a request is sent, the filter is activated
    @Override
    protected void doFilterInternal(


            //private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
            //The request sent
            @NotNull HttpServletRequest request,
            //The response to the request
            @NotNull HttpServletResponse response,
            //Cause next filter in the chain to be invoked
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtService.validateJwtToken(jwt)) {
                String username = jwtService.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = detailsOfUsersService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }


    }

