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

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends org.springframework.web.filter.OncePerRequestFilter {

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

        String authHeader =
                request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // ============================
        // Check Authorization header
        // ============================

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

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

        // ============================
        // Authenticate user
        // ============================

        if (username != null &&
                SecurityContextHolder
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

                } else {

                    System.out.println(
                            "JWT token is invalid"
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "JWT authentication failed: "
                                + e.getMessage()
                );
            }
        }

        // ============================
        // Debug information
        // ============================

        System.out.println(
                "Request: "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        System.out.println(
                "Authenticated user: "
                        + SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );

        // Continue request
        filterChain.doFilter(request, response);
    }
}