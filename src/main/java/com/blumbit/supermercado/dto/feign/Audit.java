package com.blumbit.supermercado.dto.feign;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    private String id;

    private String userId;

    private String action;

    private String resource;

    private String details;

    private LocalDateTime timestamp;
}
