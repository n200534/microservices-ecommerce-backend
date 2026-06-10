package com.akshay.authservice.service.impl;

import com.akshay.authservice.dto.LoginRequestDTO;
import com.akshay.authservice.dto.RegisterRequestDTO;
import com.akshay.authservice.dto.AuthResponseDTO;
import com.akshay.authservice.entity.Role;
import com.akshay.authservice.entity.User;
import com.akshay.authservice.exception.InvalidCredentialsException;
import com.akshay.authservice.exception.UserAlreadyExistsException;
import com.akshay.authservice.repository.UserRepository;
import com.akshay.authservice.security.JwtService;
import com.akshay.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    @Override
    public AuthResponseDTO register(
            RegisterRequestDTO request
    ) {

        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new UserAlreadyExistsException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.USER)
                .build();

        User savedUser =
                userRepository.save(user);

        String token =
                jwtService.generateToken(
                        savedUser
                );

        return AuthResponseDTO.builder()
                .token(token)
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }

    @Override
    public AuthResponseDTO login(
            LoginRequestDTO request
    ) {

        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "Invalid email or password"
                                ));

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
