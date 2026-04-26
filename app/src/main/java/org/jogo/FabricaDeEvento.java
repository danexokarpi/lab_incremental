package org.jogo;

import java.util.ArrayList;

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

    public Batalha criaBatalha(int quantidadeDeInimigos) {
        ArrayList<Inimigo> listaDeInimigos = FabricaDeInimigo.criaListaDeInimigos(quantidadeDeInimigos);
        return new Batalha(heroi, listaDeInimigos, capacidadeDaMao, menu);
    }
    
    public Loja criaLoja(){
        return new Loja(heroi , menu);
    }
    
    public Escolha criaEscolha(){
        return new Escolha(menu, heroi);
    }
}
