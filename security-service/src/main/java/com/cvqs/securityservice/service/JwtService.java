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
 * Bu sınıf, JWT oluşturma, doğrulama ve ayrıştırma işlemleri yapar.
 *
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@Service
public class JwtService {
    private static final String SECRET_KEY="244226452948404D635166546A576E5A7234753778217A25432A462D4A614E64";

    /**
     * Verilen JWT içerisinden kullanıcı adını çıkarmak için kullanılır.
     * @param token JWT
     * @return JWT içerisindeki kullanıcı adı
     */
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * Verilen JWT token'ından belirtilen claim'i çıkarır.
     * @param token JWT token'ı
     * @param clasimsResolver claim'i çıkarmak için bir fonksiyon
     * @param <T> claim'in tipi
     * @return token'da belirtilen claim değeri, eğer yoksa null
     */
    public <T> T extractClaim(String token, Function<Claims,T> clasimsResolver){
        final Claims claims=extractAllClaims(token);
        return clasimsResolver.apply(claims);
    }

    /**
     * Verilen UserDetails nesnesi kullanarak bir JWT üretir.
     * @param userDetails JWT içeriği için kullanılacak UserDetails nesnesi.
     * @return Üretilen JWT.
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    /**
     * Verilen  taleplere ve UserDetails nesnesine dayanarak bir JWT token oluşturur.
     * @param extraClaims JWT'ye eklenmesi gereken taleplerin listesi
     * @param userDetails JWT'nin oluşturulacağı UserDetails nesnesi
     * @return oluşturulan JWT token
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
     * Verilen JWT token'ı ve UserDetails nesnesinin geçerli olup olmadığını kontrol eder.
     *
     * @param token geçerli olup olmadığı kontrol edilecek JWT token'ı
     * @param userDetails  JWT token'ındaki kullanıcının ayrıntılarını içeren UserDetails nesnesi
     * @return JWT token'ı ve UserDetails nesnesinin geçerli olup olmadığını gösteren boolean değeri
     */
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }
    /**
     * Verilen token'ın geçerli olup olmadığını ve aynı zamanda belirtilen rolü içerip içermediğini kontrol eder.
     *
     * @param token JWT token'ı
     * @param userDetails kullanıcının kimlik bilgileri
     * @param role  kontrol edilecek rol adı
     * @return token geçerli ise ve kullanıcı belirtilen rolü içeriyorsa true, aksi takdirde false
     */
    public boolean isTokenValidAndHasRole(String token,UserDetails userDetails,String role){
        if(this.isTokenValid(token,userDetails)&&this.hasRole(token,role)){
            return true;
        }
        return false;
    }

    /**
     * Verilen JWT token'ın süresinin dolup dolmadığını kontrol eder.
     * @param token kontrol edilecek JWT token'ı.
     * @return token'ın süresi dolmuşsa true, dolmamışsa false döner.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Verilen JWT token'ın içerisinden expiration tarihini çıkarır.
     * @param token JWT token'ı.
     * @return JWT token'ın  Date nesnesi.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    /**
     * Verilen JWT token'ının gövdesindeki tüm claim'leri alır.
     *
     * @param token JWT token'ı
     * @return Token'ın gövdesindeki tüm claim'leri içeren bir Claims nesnesi
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
     * secretKey  elde etmek için kullanılan özel metot.
     * @return  bir key döndürür.
     */
    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Verilen token'da belirtilen role'ün olup olmadığını kontrol eder.
     * @param token Role kontrolü yapılacak JWT token'ı.
     * @param role Kontrol edilecek rol.
     * @return Token'da belirtilen role varsa true, yoksa false döner.
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
