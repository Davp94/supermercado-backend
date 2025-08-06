package com.blumbit.supermercado.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/estado")
    public String serviceStatus(){
        return "Servicio ejecutandose exitosamente";
    }

}
