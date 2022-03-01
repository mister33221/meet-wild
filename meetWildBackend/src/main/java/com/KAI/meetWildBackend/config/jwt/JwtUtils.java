package com.KAI.meetWildBackend.config.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.KAI.meetWildBackend.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


//這頁還沒搞懂

//@Component：泛指組件，當組件不好歸類的時候，我們可以使用這個註解進行標註。
@Component
public class JwtUtils {

//	注意LoggerFactory是用import org.slf4j.LoggerFactory; 而非import org.hibernate.annotations.common.util.impl.LoggerFactory;
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	
	@Value("${meetWild.app.jwtSecret}")
	private String jwtSecret;

	@Value("${meetWild.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		
		
//		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//		
//		Claims claims = Jwts.claims();
//        claims.put("username", userPrincipal.getUsername());
//        System.out.println("username:"+userPrincipal.getUsername());
//        claims.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs));
//        claims.setIssuedAt(new Date());
//		
//		return Jwts.builder()
//				.setClaims(claims)
//				.signWith(SignatureAlgorithm.HS512, jwtSecret)
//				.compact();
//		
		
//		拿別人的參考
//	       Authentication authentication =
//	                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
//	        authentication = authenticationManager.authenticate(authentication);
//	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//	        Calendar calendar = Calendar.getInstance();
//	        calendar.add(Calendar.MINUTE, 2);
//
//	        Claims claims = Jwts.claims();
//	        claims.put("username", userDetails.getUsername());
//	        claims.setExpiration(calendar.getTime());
//	        claims.setIssuer("Programming Classroom");
//
//	        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
//
//	        return Jwts.builder()
//	                .setClaims(claims)
//	                .signWith(secretKey)
//	                .compact();
//	    }
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
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
	}

}
