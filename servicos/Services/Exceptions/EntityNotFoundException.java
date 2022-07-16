package com.SoulCode.servicos.Services.Exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String mensagem){
        super(mensagem);
    }
}
