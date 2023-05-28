package org.Stasy.SpringBoot3.SpringSecurity6.auth;

import lombok.RequiredArgsConstructor;
import org.Stasy.SpringBoot3.SpringSecurity6.config.JwtService;
import org.Stasy.SpringBoot3.SpringSecurity6.user.Role;
import org.Stasy.SpringBoot3.SpringSecurity6.user.User;
import org.Stasy.SpringBoot3.SpringSecurity6.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User
                   .builder()
                   .firstname(request.getFirstname())
                   .lastname(request.getLastname())
                   .email(request.getEmail())
                   .password(passwordEncoder.encode(request.getPassword()))
                   .role(Role.USER)
                   .build();
        userRepository.save(user);
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                                     .token(jwtToken)
                                     .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //user is authenticated if this is now the processing step
        var user =userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
