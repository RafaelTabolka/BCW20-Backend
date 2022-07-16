package com.SoulCode.servicos.Models;

public enum StatusChamado {

    RECEBIDO("Recebido"),
    ATRIBUIDO("Atribuido"),
    CONCLUIDO("Concluído"),
    ARQUIVADO("Arquivado");

    private String conteudo;

    StatusChamado(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }
}
