package com.example.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAutorizadorFilter extends BasicAuthenticationFilter {

	JWTAutorizadorFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			String header = request.getHeader("Authorization");

			if (header == null || !header.startsWith("Bearer")) {
				chain.doFilter(request, response);
				return;
			}

			UsernamePasswordAuthenticationToken authentication = validarToken(header);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
            response.setStatus(401);
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            response.setStatus(403);
		}
	}

	private UsernamePasswordAuthenticationToken validarToken(String header) {
		String usuario = JWTUtil.validarToken(header);

		if (usuario != null) {
			return new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
		}

		return null;
	}

}
