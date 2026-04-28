package org.jogo;

import java.util.ArrayList;
import java.util.Random;

public class FabricaDeEvento {
    private Heroi heroi;
    private int energiaMaxima;
    private int capacidadeDaMao;
    private Menu menu;
    private int ouro;

    public FabricaDeEvento(Heroi heroi, int capacidadeDaMao, Menu menu) {
        this.heroi = heroi;
        this.energiaMaxima = heroi.getEnegiaMaxima();
        this.ouro = heroi.getOuro();
        this.capacidadeDaMao = capacidadeDaMao;
        this.menu = menu;
    }

    public Evento criaEventoAleatorio(int nivelDeProgressao) {
        Random random = new Random();
        Evento evento = null;
        switch (random.nextInt(6)) {
            case 0:
            case 1:
            case 2:
                evento = criaBatalha(nivelDeProgressao);
                break;
            case 3:
                evento = criaLoja();
                break;
            case 4:
                evento = criaFogueira();
                break;
            case 5:
                evento = criaEscolha();
                break;
        }
        return evento;
    }

    public Batalha criaBatalha(int quantidadeDeInimigos) {
        ArrayList<Inimigo> listaDeInimigos = FabricaDeInimigo.criaListaDeInimigos(quantidadeDeInimigos);
        return new Batalha(heroi, listaDeInimigos, capacidadeDaMao, menu);
    }

    public Loja criaLoja() {
        return new Loja(heroi, menu);
    }

    public Fogueira criaFogueira() {
        return new Fogueira(heroi, menu);
    }

    public Escolha criaEscolha() {
        return new Escolha(menu, heroi);
    }
}
