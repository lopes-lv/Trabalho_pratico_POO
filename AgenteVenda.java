/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.amatur;

/**
 *
 * @author USUÁRIO
 */
public class AgenteVenda extends Thread {
    private Onibus onibus;
    private String nomeAgente;

    public AgenteVenda(Onibus onibus, String nome) {
        this.onibus = onibus;
        this.nomeAgente = nome;
    }

    @Override
    public void run() {
        onibus.reservarAssento(this.nomeAgente);
    }
}
