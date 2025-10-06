/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lavafacil.persistencia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;

/**
 *
 * @author Renato
 */
             @Entity
public class Carros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String placa, telefone, observacoes;
            
    @Column(name="marca_carro")
    private String marcaCarro;
    
    private Integer visitas, lavagens, gratis;
    
    @Column(name="tempo_limite")
    private Timestamp tempoLimite;
    
    @Column(name="nome_cliente")
    private String nomeCliente;

    public Integer getLavagens() {
        return lavagens;
    }

    public void setLavagens(Integer lavagens) {
        this.lavagens = lavagens;
    }

    public Integer getGratis() {
        return gratis;
    }

    public void setGratis(Integer gratis) {
        this.gratis = gratis;
    }
    
    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

    public Timestamp getTempoLimite() {
        return tempoLimite;
    }

    public void setTempoLimite(Timestamp tempoLimite) {
        this.tempoLimite = tempoLimite;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getMarcaCarro() {
        return marcaCarro;
    }

    public void setMarcaCarro(String marcaCarro) {
        this.marcaCarro = marcaCarro;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
            

}
