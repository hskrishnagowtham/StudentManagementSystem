package com.krishna.security;

import com.krishna.util.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // ==========================================
        // TEMPORARILY SKIP JWT FOR STUDENT APIs
        // ==========================================

        if (requestURI.equals("/students")
                || requestURI.startsWith("/students/")) {

            filterChain.doFilter(request, response);
            return;
        }

        // ==========================================
        // GET AUTHORIZATION HEADER
        // ==========================================

        String authHeader =
                request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);

            try {

                username =
                        jwtService.extractUsername(jwt);

            } catch (Exception e) {

                System.out.println(
                        "JWT extraction failed: "
                                + e.getMessage()
                );
            }
        }

        // ==========================================
        // AUTHENTICATE USER
        // ==========================================

        if (username != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            try {

                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(username);

                if (jwtService.isTokenValid(
                        jwt,
                        userDetails)) {

                    UsernamePasswordAuthenticationToken
                            authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authenticationToken
                            );

                    System.out.println(
                            "JWT authentication successful for: "
                                    + username
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "JWT authentication failed: "
                                + e.getMessage()
                );
            }
        }

        // ==========================================
        // CONTINUE REQUEST
        // ==========================================

        filterChain.doFilter(request, response);
    }
}