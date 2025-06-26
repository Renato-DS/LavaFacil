package com.rm.lavarapidorm.persistencia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

             @Entity
public class Opcoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="nome_opcao")
    private String nomeOpcao;
    
    @Column(name="valor_opcao")
    private String valorOpcao;
        
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeOpcao() {
        return nomeOpcao;
    }

    public void setNomeOpcao(String nomeOpcao) {
        this.nomeOpcao = nomeOpcao;
    }

    public String getValorOpcao() {
        return valorOpcao;
    }

    public void setValorOpcao(String valorOpcao) {
        this.valorOpcao = valorOpcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
