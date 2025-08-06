package com.blumbit.supermercado.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blumbit.supermercado.entity.Permiso;
import com.blumbit.supermercado.entity.PermisoRol;
import com.blumbit.supermercado.entity.Rol;
import com.blumbit.supermercado.entity.Usuario;
import com.blumbit.supermercado.repository.PermisoRepository;
import com.blumbit.supermercado.repository.PermisoRolRepository;
import com.blumbit.supermercado.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UsuarioRepository usuarioRepository;
    private final PermisoRolRepository permisoRolRepository;
    private final PermisoRepository permisoRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return username -> {
            Usuario usuario = usuarioRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            if(usuario.getRoles() != null){
                for(Rol rol: usuario.getRoles()){
                    authorities.add(new SimpleGrantedAuthority("ROL_"+rol.getNombre()));
                    List<PermisoRol> permisosRol = permisoRolRepository.findByRol_Id(rol.getId());
                    if(permisosRol.size() > 0){
                        List<Integer> permisosIds = permisosRol.stream().map(permisoRol->permisoRol.getId()).collect(Collectors.toList()); 
                        for(Permiso permiso: permisoRepository.findAllById(permisosIds)){
                            authorities.add(new SimpleGrantedAuthority(permiso.getNombre()));
                        }
                    }
                }
            }
            return User
                    .builder()
                    .username(usuario.getEmail())
                    .password(usuario.getPassword())
                    .authorities(authorities)
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
