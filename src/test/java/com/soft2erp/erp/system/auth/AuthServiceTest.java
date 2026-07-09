package com.soft2erp.erp.system.auth;

import com.soft2erp.erp.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest {

    private final AuthService authService = new AuthService();

    @Test
    @DisplayName("로그인 요청이 유효하면 개발용 토큰과 권한을 반환한다")
    void loginReturnsDevelopmentToken() {
        LoginResponse response = authService.login(new LoginRequest("admin", "secret"));

        assertThat(response.userId()).isEqualTo("admin");
        assertThat(response.username()).isEqualTo("admin");
        assertThat(response.roles()).containsExactly("ROLE_DEV");
        assertThat(response.accessToken()).isEqualTo("dev-token");
    }

    @Test
    @DisplayName("사용자 ID 또는 비밀번호가 공백이면 업무 예외를 던진다")
    void loginRejectsBlankCredentials() {
        assertThatThrownBy(() -> authService.login(new LoginRequest(" ", "secret")))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User id and password are required");

        assertThatThrownBy(() -> authService.login(new LoginRequest("admin", " ")))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User id and password are required");
    }
}
