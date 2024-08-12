package com.commerce.userapi.config.filter;

import java.io.IOException;

import com.commerce.commercedomain.common.UserVo;
import com.commerce.commercedomain.config.JwtAuthenticationProvider;
import com.commerce.userapi.service.customer.CustomerService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@WebFilter(urlPatterns = "/customer/*")
@RequiredArgsConstructor
public class CustomerFilter implements Filter {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final CustomerService customerService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String token = req.getHeader("X-AUTH-TOKEN");
		if (!jwtAuthenticationProvider.validateToken(token)) {
			throw new ServletException("Invalid Access");
		}
		UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
		customerService.findByIdAndEmail(userVo.getId(),userVo.getEmail()).orElseThrow(
			() -> new ServletException("Invalid access")
		);

	}

}
