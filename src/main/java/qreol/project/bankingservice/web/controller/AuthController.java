package qreol.project.bankingservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qreol.project.bankingservice.domain.User;
import qreol.project.bankingservice.repository.mappers.UserMapper;
import qreol.project.bankingservice.service.AuthService;
import qreol.project.bankingservice.service.UserService;
import qreol.project.bankingservice.web.dto.JwtRequest;
import qreol.project.bankingservice.web.dto.JwtResponse;
import qreol.project.bankingservice.web.dto.RegisterUserRequest;
import qreol.project.bankingservice.web.validator.UserValidator;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(summary = "Register")
    @PostMapping("/registration")
    public ResponseEntity<User> register(@RequestBody @Validated RegisterUserRequest userRequest, BindingResult bindingResult) {
        userValidator.validate(userRequest, bindingResult);
        User user = userMapper.toEntity(userRequest);

        return ResponseEntity.ok(userService.save(user));

    }

    @Operation(summary = "Refresh JWT token")
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

}
