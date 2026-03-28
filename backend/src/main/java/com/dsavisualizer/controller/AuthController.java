package com.dsavisualizer.controller;

import com.dsavisualizer.dto.AuthResponse;
import com.dsavisualizer.dto.LoginRequest;
import com.dsavisualizer.dto.SignupRequest;
import com.dsavisualizer.dto.UserDTO;
import com.dsavisualizer.model.User;
import com.dsavisualizer.repository.UserRepository;
import com.dsavisualizer.security.JwtTokenProvider;
import com.dsavisualizer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174", "http://localhost:3000" }, maxAge = 3600)
public class AuthController {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @PostMapping("/signup")
        public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
                UserDTO userDTO = userService.registerUser(signupRequest);
                return ResponseEntity.ok(userDTO);
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                String token = jwtTokenProvider.generateToken(authentication);

                User user = userRepository.findByUsername(authentication.getName()).orElse(null);
                UserDTO userDTO = UserDTO.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .fullName(user.getFullName())
                                .roles(user.getRoles())
                                .build();

                AuthResponse authResponse = AuthResponse.builder()
                                .token(token)
                                .expiresIn(jwtTokenProvider.getExpirationTime())
                                .user(userDTO)
                                .build();

                return ResponseEntity.ok(authResponse);
        }
}
