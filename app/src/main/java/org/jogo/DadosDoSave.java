package org.jogo;
import java.util.ArrayList;

public class DadosDoSave {
    int vidaHeroi;
    int energiaMaxima;
    ArrayList<Carta> inventarioHeroi;
    //  ArrayList<No> arvore;
    public DadosDoSave(){}

    public DadosDoSave(int vidaHeroi, int energiaMaxima, ArrayList<Carta> inventarioHeroi){
        this.vidaHeroi = vidaHeroi;
        this.energiaMaxima = energiaMaxima;
        this.inventarioHeroi = inventarioHeroi;
    }
}
