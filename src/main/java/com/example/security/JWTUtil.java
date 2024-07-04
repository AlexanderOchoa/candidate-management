package com.example.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public final class JWTUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);
    private static final String KEY_AUTHORIZATION = "keyAuthorization";

    private JWTUtil() {
    }

    public static String generarToken(String usuario) {
        return Jwts.builder().setIssuedAt(new Date())
                .setSubject(usuario)
                .signWith(SignatureAlgorithm.HS512, KEY_AUTHORIZATION).compact();
    }

    public static String validarToken(String token) {
        try {
            if (token != null) {
                return Jwts.parser()
                        .setSigningKey(KEY_AUTHORIZATION)
                        .parseClaimsJws(token.replace("Bearer", ""))
                        .getBody()
                        .getSubject();
            }
        } catch (ExpiredJwtException exception) {
            LOGGER.warn("Token expirado.", exception);
        } catch (UnsupportedJwtException exception) {
            LOGGER.error("Token no soportado.", exception);
        } catch (MalformedJwtException exception) {
            LOGGER.error("Token inválido.", exception);
        } catch (SignatureException exception) {
            LOGGER.error("Token no firmado.", exception);
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Token no enviado.", exception);
        }

        return null;
    }

}
