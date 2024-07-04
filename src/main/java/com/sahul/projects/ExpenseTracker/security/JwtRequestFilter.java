package com.sahul.projects.ExpenseTracker.security;

import com.sahul.projects.ExpenseTracker.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;
                if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
                {
                    jwtToken = requestTokenHeader.substring(7);
                    try{
                        username = jwtTokenUtil.getusernameFromToken(jwtToken);
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new RuntimeException("unable to get jwt token");
                    }
                    catch (ExpiredJwtException e)
                    {
                        throw new RuntimeException("jwt token has expired");
                    }
                }
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
                    if(jwtTokenUtil.validateToken(jwtToken, userDetails))
                    {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails
                        , null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }

                }
                filterChain.doFilter(request, response);

    }
}
