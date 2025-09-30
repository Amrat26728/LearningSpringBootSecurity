package com.example.amrat.LearningSpringBootSecurity.security;

import com.example.amrat.LearningSpringBootSecurity.entity.User;
import com.example.amrat.LearningSpringBootSecurity.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

    // inject secret key from application.properties
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    // convert secret key into hmacShaKey
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }


    // get AuthProviderType
    public AuthProviderType getAuthProviderTypeFromRegistrationId(String registrationId){
        return switch (registrationId.toLowerCase()){
            case "google" -> AuthProviderType.GOOGLE;
            case "github" -> AuthProviderType.GITHUB;
            case "facebook" -> AuthProviderType.FACEBOOK;
            default -> throw new IllegalArgumentException("Unsupported auth provider: " + registrationId);
        };
    }

    // determine providerId
    public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId){
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported OAuth2 provider: {}", registrationId);
                throw new IllegalArgumentException("Unsupported auth provider: " + registrationId);
            }
        };

        if (providerId == null || providerId.isBlank()){
            log.error("Unable to determine providerId for provider: {}", registrationId);
            throw new IllegalArgumentException("Unable to determine providerId for OAuth2 login");
        }
        return providerId;
    }

    // determine username
    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId){
        String email = oAuth2User.getAttribute("email");
        if (email != null && !email.isBlank()){
            return email;
        }

        return switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }

}
