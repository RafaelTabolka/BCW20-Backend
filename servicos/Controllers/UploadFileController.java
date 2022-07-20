package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Services.FuncionarioService;

import com.SoulCode.servicos.Util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class UploadFileController {

    @Autowired
    FuncionarioService funcionarioService;


    @PostMapping("/funcionarios/envioFotos/{idFuncionario}")
    public ResponseEntity<Void> enviarFoto(@PathVariable Integer idFuncionario,
                                           MultipartFile file,
                                           @RequestParam("nome") String nome) throws IOException {


        String fileNme = nome;
        String uploadDir = "C:/Users/rafae/Downloads/fotosFuncionario";
        String nomeMaisCaminho = uploadDir + nome;

        try{
            UploadFile.saveFile(uploadDir,fileNme,file);
            funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho);

        }catch (IOException e) {
            System.out.println("O arquivo não foi enviado: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
