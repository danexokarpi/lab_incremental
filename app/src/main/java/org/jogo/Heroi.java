package org.jogo;

/**
 * Representa o herói controlado pelo jogador no jogo.
 * 
 * Esta classe herda de {@link Entidade}, portanto possui vida, escudo e efeitos
 * aplicáveis. O herói é a entidade principal que o jogador controla durante a
 * batalha.
 */
public class Heroi extends Entidade {

    /**
     * Construtor para criar um herói com atributos iniciais.
     *
     * @param nome       o nome do herói
     * @param vidaMaxima a quantidade máxima de vida que o herói pode ter
     * @param escudo     a quantidade inicial de escudo do herói
     */
    public Heroi(String nome, int vidaMaxima, int escudo, String ascci) {
        super(nome, vidaMaxima, escudo, ascci);
    }

}
