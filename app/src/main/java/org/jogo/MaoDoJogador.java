package org.jogo;

import java.util.ArrayList;

/**
 * Representa a mão do jogador durante a batalha.
 *
 * Esta classe gerencia as cartas que o jogador possui em mãos, permitindo
 * adicionar, remover e descartar cartas. Também controla a capacidade máxima
 * da mão, garantindo que o jogador não ultrapasse o limite de cartas.
 */
public class MaoDoJogador {
    private int capacidade;
    private int tamanho;
    private ArrayList<Carta> mao;

    /**
     * Cria uma nova mão do jogador com capacidade máxima definida.
     *
     * @param capacidade número máximo de cartas que a mão pode conter.
     */
    public MaoDoJogador(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.mao = new ArrayList<Carta>();
    }

    /**
     * Verifica se a mão do jogador está cheia.
     *
     * @return {@code true} se a mão atingiu sua capacidade máxima, {@code false}
     *         caso contrário.
     */
    public boolean estaCheia() {
        return tamanho == capacidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void addCarta(Carta carta) {
        if (this.estaCheia()) {
            throw new java.lang.RuntimeException("Mão já está cheia. Não é possível adicionar mais cartas.");
        }
        this.mao.add(tamanho, carta);
        this.tamanho++;
    }

    public Carta getCarta(int index) {
        return this.mao.get(index);
    }

    public void removeCarta(int index) {
        this.mao.remove(index);
        this.tamanho--;
    }

    /**
     * Descarta todas as cartas da mão para a pilha de descarte.
     *
     * Este método esvazia a mão do jogador e adiciona todas as cartas
     * à pilha de descarte fornecida.
     *
     * @param pilhaDeDescarte pilha de descarte que receberá todas as cartas da mão.
     */
    public void descartarTudo(PilhaDeDescarte pilhaDeDescarte) {
        while (this.mao.size() != 0) {
            pilhaDeDescarte.push(this.mao.get(0));
            this.mao.remove(0);
            this.tamanho--;
        }
    }

}
