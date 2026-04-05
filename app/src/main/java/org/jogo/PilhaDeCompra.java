package org.jogo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representa a pilha de compra de cartas do jogador.
 *
 * Esta classe gerencia as cartas disponíveis para serem compradas durante
 * a batalha, permitindo sorteio aleatório de cartas e reabastecimento da mão
 * do jogador a partir da pilha de descarte quando necessário.
 */
public class PilhaDeCompra {
    private ArrayList<Carta> pilha = new ArrayList<Carta>();
    private static Random random = new Random();

    /**
     * Cria uma pilha de compra inicial a partir de uma lista de cartas.
     *
     * @param cartasInventario cartas iniciais que compõem a pilha de compra.
     */
    public PilhaDeCompra(ArrayList<Carta> cartasInventario) {
        for (Carta carta : cartasInventario) {
            this.pilha.add(carta);
        }
    }

    /**
     * Reabastece a mão do jogador com cartas da pilha de compra.
     *
     * Se a pilha de compra estiver vazia, solicita o reabastecimento a partir
     * da pilha de descarte. Adiciona cartas aleatórias à mão até que ela esteja
     * cheia.
     *
     * @param maoDoJogador    mão do jogador a ser preenchida.
     * @param pilhaDeDescarte pilha de descarte utilizada para reabastecer a pilha
     *                        de compra.
     */
    public void reabastecerMao(MaoDoJogador maoDoJogador, PilhaDeDescarte pilhaDeDescarte) {
        while (!maoDoJogador.estaCheia()) {
            if (this.isEmpty()) {
                pilhaDeDescarte.reabastecerCompra(this);
            }
            maoDoJogador.addCarta(this.popRandom());
        }
    }

    /**
     * Remove e retorna uma carta aleatória da pilha de compra.
     *
     * @return carta sorteada aleatoriamente.
     */
    public Carta popRandom() {
        int randomIndex = random.nextInt(pilha.size());
        return this.pilha.remove(randomIndex);
    }

    /**
     * Adiciona uma carta à pilha de compra.
     *
     * @param carta carta a ser adicionada.
     */
    public void push(Carta carta) {
        this.pilha.add(carta);
    }

    /**
     * Verifica se a pilha de compra está vazia.
     *
     * @return {@code true} se a pilha estiver vazia, {@code false} caso contrário.
     */
    public boolean isEmpty() {
        return pilha.size() == 0;
    }
}
