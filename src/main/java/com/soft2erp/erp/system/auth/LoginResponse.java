package com.soft2erp.erp.system.auth;

import java.util.List;

public record LoginResponse(
        String userId,
        String username,
        List<String> roles,
        String accessToken
) {
}

