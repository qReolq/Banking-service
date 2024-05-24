package qreol.project.bankingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qreol.project.bankingservice.domain.User;
import qreol.project.bankingservice.service.AuthService;
import qreol.project.bankingservice.service.UserService;
import qreol.project.bankingservice.web.dto.JwtRequest;
import qreol.project.bankingservice.web.dto.JwtResponse;
import qreol.project.bankingservice.web.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse response = new JwtResponse();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        User user = userService.getByLogin(loginRequest.getLogin());

        response.setId(user.getId());
        response.setLogin(user.getLogin());
        response.setAccessToken(jwtTokenProvider.createAccessToken(user));
        response.setRefreshToken(jwtTokenProvider.createRefreshToken(user));

        return response;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserToken(refreshToken);
    }

}
