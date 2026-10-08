package dev.matheusGama.gerenciamento_pescadores_api.controller;

import dev.matheusGama.gerenciamento_pescadores_api.dto.request.LoginRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.request.RegisterRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.LoginResponse;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.RegisterResponse;
import dev.matheusGama.gerenciamento_pescadores_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> login(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse RegisterResponse = authService.register(registerRequest);

        return ResponseEntity.ok(RegisterResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);

        ResponseCookie cookie = ResponseCookie
                .from("access_token", token)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(2))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse("Login realizado com sucesso."));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logou() {
        ResponseCookie cookie = ResponseCookie
                .from("access_token", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
