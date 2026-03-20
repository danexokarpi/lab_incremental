package org.jogo;

import java.util.Random;

public class Inimigo extends Entidade {
    private static Random random = new Random();
    private int dano;
    private int cura;
    private int escudoAoProteger;
    private char[] listaDeAcoes;
    private char proximaAcao;

    public Inimigo(String nome, int vidaMaxima, int escudo, int dano, int cura, int escudoAoProteger,
            char[] listaDeAcoes) {
        super(nome, vidaMaxima, escudo);
        this.dano = dano;
        this.escudoAoProteger = escudoAoProteger;
        this.listaDeAcoes = listaDeAcoes;
        this.cura = cura;
        this.proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    public void agir(Tabuleiro tabuleiro) {
        switch (proximaAcao) {
            case 'A':
                tabuleiro.getHeroi().receberDano(dano);
                break;
            case 'C':
                this.curar(cura);
                break;
            case 'E':
                this.receberEscudo(escudoAoProteger);
                break;
        }

        proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    public String imprimirProxAcao() {
        switch (proximaAcao) {
            case 'A':
                return "Atacar: " + this.dano;
            case 'C':
                return "Curar: " + this.cura;
            case 'E':
                return "Proteger-se: " + this.escudoAoProteger;
            default:
                return "";
        }
    }

    public int getDano() {
        return this.dano;
    }

}
