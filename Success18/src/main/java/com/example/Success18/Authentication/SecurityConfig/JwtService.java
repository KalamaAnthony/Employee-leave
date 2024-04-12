package com.example.Success18.Authentication.SecurityConfig;

import com.example.Success18.Authentication.UserDetails.DetailsOfUsers;
import com.example.Success18.Authentication.Users.Users;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
//@Component
public class JwtService {

    //todo: for debugging and monitoring
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    //todo: secret key for signing JWTs
    @Value("${users.app.jwtSecret}")
    private String jwtSecret;


    //TODO specifies the expiration duration for the JWTs in milliseconds
    @Value("${users.app.jwtExpirationMs}")
    private int jwtExpirationMs;




//    public String extractUsername(String token) {
//        return  null;
//
//    }
   // private static final String SECRET_Key= "OpkogU5vj3CmdJiICt4SeQ==";
//todo: Extracts username from JWT. uses jwts.parser methods to set signingKey and then get the subject from the token's body
    public String getUserNameFromJwtToken(String token){

        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();


    }


    //todo:Generates a JWT for a given authentication object(DetailsOfUser which is an instance of the user).User the jwt.builder method to generate a new JWT
    public String generateJwtToken (Authentication authentication){
    DetailsOfUsers userPrinciple = (DetailsOfUsers) authentication.getPrincipal();
    return Jwts.builder()
            //sets the subject which is the user name
            .setSubject(userPrinciple.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            //compact the result into a string
            .compact();
}



//todo: Validates the JWT. Parses the JWT with the secret key. If the parsing is successful thn it returns true
    public boolean validateJwtToken(String jwt) {

 try {
         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;

            //Can catch either signatureExceptions or MalformedJwtExceptions
                    } catch (
SignatureException e) {
        logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
        logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
        logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
        logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
        logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
        }}
//    private Key getSigningKey() {
//         byte[] byteKey = BASE64DecoderStream.decode(SECRET_Key.getBytes());
//         return Keys.hmacShakeyFor(byteKey);
//
//    }
//jwt(json web token. made up of 3 parts,
// 1. Header-contains type of token and algorithm for logging in,
// 2. PayLoad - contains claim(details of the entity e.g subject, name, authorities)and
// 3. Signature- verifys the sender  of the token if it is who it claims to be and makes sure the message wasn't changed along the way)
// A signingInKey is generally a secret key that is used to digitally sign in the JWT. It is used to create the signature part of the JWT Token