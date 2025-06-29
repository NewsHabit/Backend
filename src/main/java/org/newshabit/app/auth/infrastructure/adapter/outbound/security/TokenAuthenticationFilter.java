package org.newshabit.app.auth.infrastructure.adapter.outbound.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.auth.common.exception.AccessTokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(new RequestHeaderRequestMatcher("Authorization"));
		setAuthenticationManager(authenticationManager);
		setAuthenticationFailureHandler((request, response, exception) -> {
			if (!response.isCommitted()) {
				response.resetBuffer();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json;charset=UTF-8");
				String json = "{\"status\": 401, \"message\": \"" + exception.getMessage() + "\", \"data\": null}";
				PrintWriter writer = response.getWriter();
				writer.write(json);
				writer.flush();
			}
		});
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String raw = request.getHeader("Authorization");

		String token = (raw != null && raw.startsWith("Bearer ")) ? raw.substring(7) : raw;

		TokenAuthentication authRequest = new TokenAuthentication(token);
		try {
			return getAuthenticationManager().authenticate(authRequest);
		} catch (AccessTokenException e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain, Authentication authResult)
		throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed)
		throws IOException, ServletException {
		getFailureHandler().onAuthenticationFailure(request, response, failed);
	}
}
