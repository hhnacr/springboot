package com.ccgg.SpringBootRestDemo.handler;

import com.ccgg.SpringBootRestDemo.security.SecurityUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException, ServletException {
        SecurityUtils.sendResponse(response,HttpServletResponse.SC_FORBIDDEN,
                "Authentication failed", exception);

    }
}
