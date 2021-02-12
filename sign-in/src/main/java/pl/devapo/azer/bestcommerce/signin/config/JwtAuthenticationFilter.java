package pl.devapo.azer.bestcommerce.signin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
@Slf4j
class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper mapper;
    private final SecurityConfigProperties securityConfigProperties;

    JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationFailureHandler authenticationFailureHandler,
            SecurityConfigProperties securityConfigProperties
    ) {
        super(new AntPathRequestMatcher(securityConfigProperties.getLoginUrl(), "POST"));
        this.securityConfigProperties = securityConfigProperties;
        setAuthenticationManager(authenticationManager);
        setAuthenticationFailureHandler(authenticationFailureHandler);
        this.mapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserLoginPayload u = mapper.readValue(request.getInputStream(), UserLoginPayload.class);
        var authenticationToken = new UsernamePasswordAuthenticationToken(u.getLogin(), u.getPassword(), emptyList());
        authenticationToken.setDetails(u.rememberMe);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authentication) throws IOException {
        var signingKey = securityConfigProperties.getJwtSecret().getBytes();
        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(toDate(LocalDateTime.now()))
                .setExpiration(tokenTtl((Boolean) authentication.getDetails()))
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .compact();
        response.addHeader(securityConfigProperties.getTokenHeader(), securityConfigProperties.getTokenPrefix() + " " + token);
        response.addHeader("Content-type", "text/plain");
        response.getWriter().write("SUCCESS LOGIN");
    }

    private Date tokenTtl(Boolean rememberMe) {
        return rememberMe ?
                toDate(LocalDateTime.now().plusSeconds(securityConfigProperties.getTokenRememberMeTtlSeconds())) :
                toDate(LocalDateTime.now().plusSeconds(securityConfigProperties.getTokenTtlSeconds()));
    }

    private Date toDate(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    @Getter
    @Setter
    private static class UserLoginPayload {
        private String login;
        private String password;
        private Boolean rememberMe;
    }
}
