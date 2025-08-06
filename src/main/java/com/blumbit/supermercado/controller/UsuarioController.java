package com.blumbit.supermercado.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.supermercado.common.dto.CustomResponse;
import com.blumbit.supermercado.dto.request.UsuarioRequest;
import com.blumbit.supermercado.dto.request.UsuarioUpdateRequest;
import com.blumbit.supermercado.dto.response.UsuarioResponse;
import com.blumbit.supermercado.service.IUsuarioService;

@RestController
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios/{id}")
    public CustomResponse<UsuarioResponse> getUsuarioById(@PathVariable Long id) {   
        return CustomResponse.<UsuarioResponse>success(usuarioService.findById(id));
    }

    @GetMapping("/usuarios")
    public CustomResponse<List<UsuarioResponse>> getAllUsuarios() {
        return CustomResponse.<List<UsuarioResponse>>success(usuarioService.findAll());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> createUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse savedUsuario = usuarioService.save(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateRequest usuarioRequest) {
        UsuarioResponse savedUsuario = usuarioService.update(id, usuarioRequest); 
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
