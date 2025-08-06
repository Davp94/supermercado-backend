package com.blumbit.supermercado.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.blumbit.supermercado.dto.request.AuthRequest;
import com.blumbit.supermercado.dto.response.AuthResponse;
import com.blumbit.supermercado.entity.Usuario;
import com.blumbit.supermercado.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.expiration}")
    private String expiration;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest authRequest){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), 
                authRequest.getPassword(), 
                null)
        );
        Usuario usuario = usuarioRepository.findByEmail(authRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);
        return  AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .expiration(expiration)
                .build(); 
    }

    public AuthResponse refreshToken(String authentication){
        if(authentication == null || !authentication.startsWith("Bearer ")){
            throw new IllegalArgumentException("Header auth invalido");
        }
        String refreshToken = authentication.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail == null){
            return null;
        }
        Usuario usuario = usuarioRepository.findByEmail(userEmail).orElseThrow();
        boolean isTokenValid = jwtService.isTokenValid(refreshToken, usuario);
        if(!isTokenValid){
            return null;
        }
        String token = jwtService.generateRefreshToken(usuario);
        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build(); 
    }
}
