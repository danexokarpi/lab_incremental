package org.jogo;

import java.util.ArrayList;

public class FabricaDeBatalha {
    private Heroi heroi;
    private ArrayList<Carta> baralho;
    private int energiaMaxima;
    private int capacidadeDaMao;

    public FabricaDeBatalha(Heroi heroi, ArrayList<Carta> baralho, int energiaMaxima, int capacidadeDaMao) {
        this.heroi = heroi;
        this.baralho = baralho;
        this.energiaMaxima = energiaMaxima;
        this.capacidadeDaMao = capacidadeDaMao;
    }

    public Batalha criaBatalha(int quantidadeDeInimigos) {
        ArrayList<Inimigo> listaDeInimigos = FabricaDeInimigo.criaListaDeInimigos(quantidadeDeInimigos);
        return new Batalha(heroi, listaDeInimigos, baralho, energiaMaxima, capacidadeDaMao);
    }
}
