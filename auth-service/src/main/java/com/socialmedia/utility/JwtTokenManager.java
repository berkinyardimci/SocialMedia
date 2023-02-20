package com.socialmedia.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    /*
    @Value("${jwt.secretkey}")
    String secretKey;
    @Value("${jwt.audience}")
    String audience;
    @Value("${jwt.issuer}")
    String issuer;
    */


    public String createToken(Long id) {
        String token = null;
        String secretKey = "secretkey";

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);

            token = JWT.create()
                    .withAudience("bilgeadam")
                    .withIssuer("berkin")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
                    .withClaim("id", id)
                    .sign(algorithm);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return token;
    }

   public Optional<Long> getUserId(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512("secretkey");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("berkin")
                    .withAudience("bilgeadam")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT ==null){
                throw new AuthManagerException(ErrorType.INVALID_TOKEN);
            }
            Long id= decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        }catch (Exception e){
            return  Optional.empty();
        }
   }
}
