//package com.hhs.codeboard.config.security;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//        // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
//        String userEmail = token.getName();
//        String userPw = (String) token.getCredentials();
//        // UserDetailsService를 통해 DB에서 아이디로 사용자 조회
//        userDetailsVO userDetailsVO = (UserDetailsVO) userDetailsService.loadUserByUsername(userEmail);
//
//        if (!passwordEncoder.matches(userPw, userDetailsVO.getPassword())) {
//            throw new BadCredentialsException(userDetailsVO.getUsername() + "Invalid password");
//        }
//
//        return new UsernamePasswordAuthenticationToken(userDetailsVO, userPw, userDetailsVO.getAuthorities());
//	}
//	
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
//	}
//}
