package com.akshay.authservice.service;

import com.akshay.authservice.dto.AuthResponseDTO;
import com.akshay.authservice.dto.LoginRequestDTO;
import com.akshay.authservice.dto.RegisterRequestDTO;


public interface AuthService {

    AuthResponseDTO register(
            RegisterRequestDTO request
    );

    AuthResponseDTO login(
            LoginRequestDTO request
    );
}