package com.hhplus.hhplus_week3_4_5.ecommerce.base.config.filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@WebFilter("/*")
@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("---- AuthFilter >>> doFilter ----");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if authentication token (JWT) exists in request header
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Missing or invalid Authorization header");
            log.error("Missing or invalid Authorization header");
            return;
        }

        // Extract JWT token from Authorization header
        String jwt = authHeader.substring(7); // "Bearer " 다음의 토큰 문자열 추출

        // Perform JWT validation (Example: using JWT library)
        if (!isValidToken(jwt)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid JWT token");
            log.error("Invalid JWT token");
            return;
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    private boolean isValidToken(String jwt) {
        log.info("---- AuthFilter >>> isValidToken ----");
        return jwt != null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("---- AuthFilter >>> init ----");
    }

    @Override
    public void destroy() {
        log.info("---- AuthFilter >>> destroy ----");
    }
}