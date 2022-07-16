package com.SoulCode.servicos.Controllers.Exceptions;

import com.SoulCode.servicos.Services.Exceptions.DataIntegrityViolationException;
import com.SoulCode.servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){

        StandardError erro = new StandardError();

        erro.setTimeStamp(Instant.now());

        erro.setStatus(HttpStatus.NOT_FOUND.value());

        erro.setError("Registro mão encontrado");

        erro.setMessage(e.getMessage());

        erro.setPath(request.getRequestURI());

        erro.setTrace("Entity Not Found Exeption");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> DataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request){

        StandardError erro = new StandardError();

        erro.setTimeStamp(Instant.now());

        erro.setStatus(HttpStatus.BAD_REQUEST.value());

        erro.setError("Atributo não pode ser duplicado");

        erro.setMessage(e.getMessage());

        erro.setPath(request.getRequestURI());

        erro.setTrace("Data Integrity Violation Exception");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
