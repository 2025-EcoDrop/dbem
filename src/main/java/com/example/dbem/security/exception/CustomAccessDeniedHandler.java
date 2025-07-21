package com.example.dbem.security.exception;

import com.example.dbem.exception.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .code("Authorization_Failed")
                .message(accessDeniedException.getMessage())
                .build();

        String json = new ObjectMapper().writeValueAsString(error);

        response.getWriter().write(json);
    }
}
