package com.KAI.meetWildBackend.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.KAI.meetWildBackend.security.service.UserDetailsServiceImpl;


//定義一個每一次request都會經過的filter
//繼承OncePerRequestFilter 並且複寫doFilterInternal()
//另外還有一個dofilter() 是整個過濾器最體層的filter的街口中的方法，所有過力氣都要實現filter
//oncerPerRequestFilter可以確保在每一次的請求中，該次request只通過一次filter，是spring提供的抽象類別

public class AuthTokenFilter extends OncePerRequestFilter {

	
	@Autowired //沒辦法改成constructor?
	private JwtUtils jwtUtils;

	@Autowired //沒辦法改成constructor? 呼叫的時候就會一定要放參數 但改用@Autowired就不用?
	private UserDetailsServiceImpl userDetailsService;
	
	

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	
	//doFilterInternal是onvePerRequestFilter中的一哥抽象方法
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			System.out.println(request.authenticate(response));
			String jwt = parseJwt(request);
			System.out.println("jwt:"+jwt);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
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

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

}
