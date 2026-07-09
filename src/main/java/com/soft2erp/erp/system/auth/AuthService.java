package com.soft2erp.erp.system.auth;

import com.soft2erp.erp.common.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    public LoginResponse login(LoginRequest request) {
        if (request.userId().isBlank() || request.password().isBlank()) {
            throw new BusinessException("User id and password are required");
        }

        return new LoginResponse(
                request.userId(),
                request.userId(),
                List.of("ROLE_DEV"),
                "dev-token"
        );
    }
}

