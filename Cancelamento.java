/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.amatur;

/**
 *
 * @author USUÁRIO
 */
public class Cancelamento extends Thread {
    private Onibus onibus;

    public Cancelamento(Onibus onibus) {
        this.onibus = onibus;
    }

    @Override
    public void run() {
        try {
            // Simula um tempo até que ocorra um cancelamento
            Thread.sleep(4000); 
            // Libera 2 assentos para quem estiver esperando
            onibus.cancelarPassagem(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}