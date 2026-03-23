package org.example.hikingmatchservice.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hikingmatchservice.entities.User;
import org.example.hikingmatchservice.repository.IUserRepository;
import org.example.hikingmatchservice.security.custom.CustomUser;
import org.example.hikingmatchservice.security.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Optional;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    private JwtTokenUtils jwtTokenUtils;
    private IUserRepository userRepository;

    public JwtValidationFilter(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils, IUserRepository userRepository) {
        super(authenticationManager);
        this.jwtTokenUtils = jwtTokenUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = null;
        String header = request.getHeader(JwtTokenUtils.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(JwtTokenUtils.PREFIX_TOKEN.length()).trim();
        try {
            CustomUser customUser = null;

            Claims claims = jwtTokenUtils.getTokenPayload(token);
            Optional<User> userOpt = userRepository.findUserByUsername(claims.getSubject());
            if (userOpt.isPresent()) {
                user = userOpt.get();
                customUser = new CustomUser(user.getUsername(), user.getPassword(), user.getRoleAuthorities(), user.getId());
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUser, null, user.getRoleAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);

        } catch (JwtException | AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
