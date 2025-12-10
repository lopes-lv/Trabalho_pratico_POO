# Trabalho Prático: O Desafio da Concorrência na AMATUR

**Disciplina:** Programação Orientada a Objetos (Java)  
**Tema:** Threads, Sincronização e Concorrência  

## 1. Contextualização
A empresa de transportes **AMATUR** enfrentava problemas críticos de *overbooking* na rota Boa Vista-Manaus.Múltiplos agentes de venda tentavam reservar a mesma poltrona simultaneamente, gerando saldos negativos de assentos devido à falta de controle de concorrência no sistema antigo.

Este projeto simula esse cenário caótico e implementa soluções robustas utilizando:
* **Synchronized Blocks** (Exclusão Mútua)
* **Wait/Notify** (Comunicação entre Threads)
* **Semaphores** (Controle de fluxo)

---

## 2. Evidência do Problema (Parte I: Race Condition)

Na primeira etapa da simulação, o sistema foi executado sem proteções (`synchronized` ou `semaphores`) e com uma latência artificial (`Thread.sleep`).

**Resultado Obtido:**
Observa-se abaixo que múltiplos agentes entraram na verificação de disponibilidade ao mesmo tempo. O **Agente 2** conseguiu efetuar uma compra mesmo quando o saldo já era zero, levando o sistema a um estado inconsistente de **-1 assento**.

![Erro de Overbooking](imagem/imagem_poo)
*(Print do console mostrando a Race Condition e venda excedente)*

---

## 3. Soluções Implementadas

### A. Correção com Synchronized (Parte II)
Para resolver o problema de integridade dos dados, foi utilizado um **Bloco Sincronizado** (`synchronized(this)`) na classe `Onibus`.

**Explicação Técnica:**
O problema original ocorria porque a operação de "Verificar Saldo" e "Decrementar Saldo" não era atômica. Uma thread podia verificar que havia saldo, ser interrompida pelo escalonador, e quando voltasse, o saldo já teria sido consumido por outra.

O `synchronized` cria um **Mutex (Mutual Exclusion)**. Ele garante que apenas uma thread por vez possa acessar a seção crítica do código. Enquanto um agente está consultando e comprando, todos os outros são obrigados a esperar fora do bloco, garantindo que o saldo lido seja sempre o atual e impedindo o *overbooking*.

### B. Eficiência com Wait e Notify (Parte III)
Ao invés de simplesmente bloquear a venda quando o ônibus lota, implementamos o padrão **Produtor-Consumidor**. Se não há vagas, a thread entra em estado de espera.

**Por que usar `wait()` e não um `while`?**
Se usássemos um loop simples para checar se vagou um lugar (ex: `while(lotado) {}`), estaríamos praticando **Busy-Waiting**. A CPU ficaria processando essa verificação milhões de vezes por segundo, consumindo recursos e energia inutilmente.

O método `wait()` é mais eficiente porque:
1.  Faz a thread "dormir" e liberar o processador para outras tarefas.
2.  A thread só acorda quando recebe um sinal explícito (`notifyAll()`) vindo da thread de `Cancelamento`, indicando que há novas vagas disponíveis. [cite_start]Isso otimiza drasticamente o uso da CPU.

---

## 4. Resultado Final (Sistema Seguro)

Após as correções, o sistema comporta-se de maneira estável. O **Semáforo** limita o acesso simultâneo (simulando limite de conexões), e o **Wait/Notify** gerencia a fila de espera.

**Comportamento observado:**
1.  Vendas ocorrem sequencialmente até zerar o saldo.
2.  Agentes excedentes aguardam (`WAIT`) em vez de gerar erro.
3.  O Cancelamento libera vagas e os agentes em espera completam a compra.

![Execução Correta](imagem/imagem_correta)
