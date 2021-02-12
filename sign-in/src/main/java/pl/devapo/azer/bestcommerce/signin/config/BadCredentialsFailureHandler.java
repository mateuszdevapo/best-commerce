package pl.devapo.azer.bestcommerce.signin.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class BadCredentialsFailureHandler implements AuthenticationFailureHandler {
    private static final String ERROR_MESSAGE = "Invalid login or password";

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException {
        resp.sendError(HttpStatus.UNAUTHORIZED.value(), ERROR_MESSAGE);
    }
}
