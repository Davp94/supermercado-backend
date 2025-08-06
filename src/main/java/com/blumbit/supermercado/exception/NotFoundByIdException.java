package com.blumbit.supermercado.exception;

public class NotFoundByIdException extends RuntimeException{
    public NotFoundByIdException(String message){
        super(String.format("Recurso no encontrado.", message));
    }
}
