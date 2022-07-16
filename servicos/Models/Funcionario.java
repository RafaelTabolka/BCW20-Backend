package com.SoulCode.servicos.Models;

import com.SoulCode.servicos.Repositories.FuncionarioRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Funcionario {

    @Id // Chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O mesmo de auto_increment
    private Integer idFuncionario;

    @Column(nullable = false, length = 100) // nullable = not null no MySQL e length é a quantiadade de caracteres
    private String nome;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = true)
    private String foto;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario") // mappedBy fará um mapeamento a partir de funcionário
    private List<Chamado> chamado = new ArrayList<Chamado>();

    // ManyToOne
    // A primeira parte da anotação, Many, do relacionamento diz respeito a tabela que estamos inserindo o relacionamento ("funcionario")
    // A segunda parte da anotação, One, diz respeito a segunda tabela, com o qual esta vai se relacionar ("cargo")

    @ManyToOne
    @JoinColumn(name = "idCargo") // Join column é o mesmo do MySQL, é a junção de duas tabelas. Join significa juntar.
    private Cargo cargo;

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Chamado> getChamado() {
        return chamado;
    }

    public void setChamado(List<Chamado> chamado) {
        this.chamado = chamado;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
