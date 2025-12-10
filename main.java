/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.amatur;

/**
 *
 * @author USUÁRIO
 */
public class main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Sistema AMATUR (Seguro) ---");
        
        // 5 assentos iniciais
        Onibus onibus = new Onibus(5);

        // Inicia o processo de cancelamento automático
        Cancelamento cancelamento = new Cancelamento(onibus);
        cancelamento.start();

        // Tenta vender para 7 agentes (vai faltar vaga para 2 inicialmente)
        for (int i = 1; i <= 7; i++) {
            AgenteVenda agente = new AgenteVenda(onibus, "Agente " + i);
            agente.start();
        }
    }
}
