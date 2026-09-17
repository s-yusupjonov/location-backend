package com.trackly.locationtracking.auth;

import com.trackly.locationtracking.auth.dto.LoginRequest;
import com.trackly.locationtracking.auth.dto.LoginResponse;
import com.trackly.locationtracking.common.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AdminUserRepository adminUserRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        AdminUser adminUser = adminUserRepository.findByUsername(request.username())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), adminUser.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(adminUser.getUsername());
        return new LoginResponse(token, "Bearer", jwtService.getExpirationMs());
    }
}
