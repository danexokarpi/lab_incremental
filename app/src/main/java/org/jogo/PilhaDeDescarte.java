package org.jogo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representa a pilha de descarte de cartas do jogador.
 *
 * Essa classe gerencia as cartas que foram jogadas ou descartadas durante a
 * batalha. Permite devolver as cartas para a pilha de compra, garantindo que
 * o jogador possa reutilizar suas cartas quando a pilha de compra esvaziar.
 */
public class PilhaDeDescarte {
    private ArrayList<Carta> pilha = new ArrayList<Carta>();
    private static Random random = new Random();

    /**
     * Remove e retorna uma carta aleatória da pilha de descarte.
     *
     * @return carta sorteada aleatoriamente da pilha de descarte.
     */
    public Carta popRandom() {
        int randomIndex = random.nextInt(pilha.size());
        return this.pilha.remove(randomIndex);
    }

    /**
     * Adiciona uma carta à pilha de descarte.
     *
     * @param carta carta a ser adicionada à pilha de descarte.
     */
    public void push(Carta carta) {
        pilha.add(carta);
    }

    /**
     * Transfere todas as cartas da pilha de descarte para a pilha de compra.
     *
     * As cartas são transferidas em ordem aleatória, garantindo que a pilha
     * de compra seja reabastecida de forma imprevisível.
     *
     * @param pilhaDeCompra pilha de compra que receberá as cartas descartadas.
     */
    public void reabastecerCompra(PilhaDeCompra pilhaDeCompra) {
        while (this.pilha.size() != 0) {
            pilhaDeCompra.push(this.popRandom());
        }
    }

    /**
     * Verifica se a pilha de descarte está vazia.
     *
     * @return {@code true} se não houver cartas na pilha de descarte, {@code false}
     *         caso contrário.
     */
    public boolean isEmpty() {
        return pilha.size() == 0;
    }
}
