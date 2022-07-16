package com.SoulCode.servicos.Services.Exceptions;

public class DataIntegrityViolationException extends RuntimeException{

    public  DataIntegrityViolationException(String mensagem) {
        super(mensagem);
    }
}
