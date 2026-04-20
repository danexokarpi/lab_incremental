package org.jogo;
import java.util.ArrayList;

public class DadosDoSave {
    int vida;
    int energiaMaxima;
    ArrayList<Carta> inventarioHeroi;
    //  ArrayList<No> arvore;
    public DadosDoSave(){}

    public DadosDoSave(int vida, int energiaMaxima, ArrayList<Carta> inventarioHeroi){
        this.vida = vida;
        this.energiaMaxima = energiaMaxima;
        this.inventarioHeroi = inventarioHeroi;
    }
}
