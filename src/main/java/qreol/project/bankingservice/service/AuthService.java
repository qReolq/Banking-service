package qreol.project.bankingservice.service;

import qreol.project.bankingservice.web.dto.JwtRequest;
import qreol.project.bankingservice.web.dto.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}
