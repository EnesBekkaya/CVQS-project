package com.cvqs.securityservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * This class performs JWT creation, validation, and parsing operations.
 *
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@Service
public class JwtService {
    private static final String SECRET_KEY="244226452948404D635166546A576E5A7234753778217A25432A462D4A614E64";

    /**
     * Used to extract the username from the given JWT.
     * @param token JWT
     * @return Username inside the JWT
     */
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * Extracts the specified claim from the given JWT token.
     * @param token JWT token
     * @param clasimsResolver function to extract the claim
     * @param <T> type of the claim
     * @return the value of the specified claim in the token, or null if it does not exist
     */
    public <T> T extractClaim(String token, Function<Claims,T> clasimsResolver){
        final Claims claims=extractAllClaims(token);
        return clasimsResolver.apply(claims);
    }

    /**
     * Generates a JWT using the provided UserDetails object.
     * @param userDetails UserDetails object to be used for JWT content.
     * @return Generated JWT.
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    /**
     * Creates a JWT token based on the given claims and UserDetails object.
     * @param extraClaims List of claims to be added to the JWT
     * @param userDetails UserDetails object to be used for creating the JWT
     * @return Created JWT token
     */
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        extraClaims.put("roles",roles);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * This method checks whether the given JWT token is valid and whether it corresponds to the provided UserDetails object.
     *
     * @param token JWT token to be checked for validity
     * @param  userDetails object containing details of the user in the JWT token
     * @return boolean value indicating whether the JWT token and UserDetails object are valid or not
     */
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }

    /**
     * Checks whether the given JWT token has expired or not.
     *
     * @param token The JWT token to check for expiration.
     * @return Returns true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     * @param token JWT token to extract expiration date from.
     * @return Date object representing the expiration date of the JWT token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    /**
     * Gets all claims from the body of the given JWT token.
     *
     * @param token the JWT token to extract claims from
     * @return a Claims object containing all claims from the token's body
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Private method used to obtain the secretKey.
     * @return a key.
     */
    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Checks if the given JWT token contains the specified role.
     * @param token JWT token to check the role for.
     * @param role Role to check.
     * @return Returns true if the token contains the specified role, false otherwise.
     */
    public boolean hasRole(String token, String role) {
        if (token != null) {

            Claims claims = extractAllClaims(token);

            if (claims.containsKey("roles")) {
                List<String> roles = claims.get("roles", List.class);
                return roles.contains(role);
            }
        }
        return false;
    }
}
