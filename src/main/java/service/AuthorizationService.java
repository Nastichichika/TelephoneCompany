package service;

import entity.User;
import entity.response.TokenResponse;
import util.ApplicationProperties;
import util.Encryptor;
import io.jsonwebtoken.*;

import java.util.Date;

public class AuthorizationService {
    public static AuthorizationService INSTANCE = new AuthorizationService();

    private static final UserService userService = UserService.INSTANCE;

    private static final String signature = ApplicationProperties.get("signature");
    private static final Long ttlMillis = Long.valueOf(ApplicationProperties.get("ttlMillis"));

    private AuthorizationService() {}

    public TokenResponse authorize(String login, String password) {
        User user = userService.getByLogin(login);

        if (!user.getPassword().equals(Encryptor.encode(password))) {
            throw new RuntimeException("Wrong credentials");
        }

        return new TokenResponse(
                createJWT(user), user.getRole(),user.getLogin());
    }

    public static String createJWT(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder =
                Jwts.builder()
                        .setIssuedAt(now)
                        .setSubject(user.getLogin())
                        .claim("role", user.getRole())
                        .claim("id", user.getId())
                        .setExpiration(new Date(nowMillis + ttlMillis))
                        .signWith(SignatureAlgorithm.HS256, signature);

        return builder.compact();
    }

    public static Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
    }

    public static String getRole(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws.getBody().get("role").toString();
    }

    public static Integer getUserId(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return Integer.parseInt(claimsJws.getBody().get("id").toString());
    }
}