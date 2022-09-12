package com.todo.todobackend.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // inject required dependencies

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PersonDetailService employeeDetailService;


    public JwtAuthenticationFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        if(!request.getServletPath().equals("/login")) {
        // get jwtToken from http Request
        String token = getJwtFromRequest(request);
        // validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
        // get username from token
            String username = jwtTokenProvider.getUsernameFromJwt(token);
        // load user associated with the token
            UserDetails userDetails = employeeDetailService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // set spring security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }else{
            log.error("invalid token {}", token);
        }
//        }
        filterChain.doFilter(request, response);

    }
    private  String getJwtFromRequest(HttpServletRequest request){
         String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("Token recieved {}",bearerToken);
            return bearerToken.substring(7, bearerToken.length());
        }


        log.error("Token not found");
        return null;
    }

}
