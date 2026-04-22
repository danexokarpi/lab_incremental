package org.jogo;
import java.util.ArrayList;

public class DadosDoSave {
    int vida;
    int energiaMaxima;
    ArrayList<Carta> inventarioHeroi;
    int posicaoNoMapa;
    //  ArrayList<No> arvore;
    public DadosDoSave(){}

    public DadosDoSave(int vida, int energiaMaxima, ArrayList<Carta> inventarioHeroi, int posicaoNoMapa){
        this.vida = vida;
        this.energiaMaxima = energiaMaxima;
        this.inventarioHeroi = inventarioHeroi;
        this.posicaoNoMapa = posicaoNoMapa;
    }
}
