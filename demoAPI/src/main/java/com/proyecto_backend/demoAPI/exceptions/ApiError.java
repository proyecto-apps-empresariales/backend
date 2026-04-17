package com.proyecto_backend.demoAPI.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private int status;
    private String message;
    private Date timestamp;
    private Map<String, String> errors; // opcional (para @Valid)
}
