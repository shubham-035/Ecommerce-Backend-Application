package com.auth_nov_service.filter;

import com.auth_nov_service.service.CustomerUserDetailsService;
import com.auth_nov_service.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;

    private CustomerUserDetailsService customerUserDetailsService;

    public JwtFilter(JwtService jwtService, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtService = jwtService;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken!=null && jwtToken.startsWith("Bearer ")){
            String token = jwtToken.substring(7);
            String username = jwtService.validateJwtTokenAndRetriveUsername(token);
            if(username!=null)
            {
                var userDetails = customerUserDetailsService.loadUserByUsername(username);
                var auth=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }
        filterChain.doFilter(request,response);
    }
}
