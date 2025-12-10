/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.amatur;

/**
 *
 * @author USUÁRIO
 */
import java.util.concurrent.Semaphore;

public class Onibus {
    private int assentosDisponiveis;
    private final Semaphore semaforo;

    public Onibus(int assentosIniciais) {
        this.assentosDisponiveis = assentosIniciais;
        // Parte IV: Semáforo limitando a 3 conexões simultâneas
        this.semaforo = new Semaphore(3);
    }

    public void reservarAssento(String agente) {
        try {
            System.out.println(agente + " chegou na fila.");
            
            // Parte IV: Adquire permissão (simula limite de conexões do servidor)
            semaforo.acquire();
            
            // Parte II: Bloco Sincronizado para proteger a área crítica
            synchronized (this) {
                // Parte III: Se lotado, aguarda (Wait) em vez de falhar
                while (assentosDisponiveis == 0) {
                    System.out.println(agente + " viu que está lotado e aguarda desistências (WAIT)...");
                    try {
                        wait(); // Libera o lock e dorme até ser notificado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Lógica de Venda Segura
                if (assentosDisponiveis > 0) {
                    // Simula latência
                    try { Thread.sleep(100); } catch (InterruptedException e) {}

                    assentosDisponiveis--;
                    System.out.println(agente + " CONSEGUIU reservar! Saldo: " + assentosDisponiveis);
                }
            } // Fim do synchronized

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Parte IV: Libera a conexão para o próximo da fila
            semaforo.release();
        }
    }

    // Parte III: Método para devolver assentos (Cancelamento)
    public void cancelarPassagem(int qtd) {
        synchronized (this) {
            assentosDisponiveis += qtd;
            System.out.println("--- CANCELAMENTO: " + qtd + " vagas liberadas. Novo saldo: " + assentosDisponiveis + " ---");
            notifyAll(); // Acorda as threads que estavam no wait()
        }
    }
}
