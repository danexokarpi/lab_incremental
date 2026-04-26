package org.jogo;
import java.util.ArrayList;

public class DadosDoSave {
    public int vida;
    public int vidaMaxima;
    public int energiaMaxima;
    public int ouro;
    public ArrayList<Carta> inventarioHeroi;
    public int posicaoNoMapa;
    public DadosDoSave(){}

    public DadosDoSave(int vida,int vidaMaxima, int energiaMaxima, int ouro, ArrayList<Carta> inventarioHeroi, int posicaoNoMapa){
        this.vida = vida;
        this.vidaMaxima = vidaMaxima;
        this.energiaMaxima = energiaMaxima;
        this.ouro = ouro;
        this.inventarioHeroi = inventarioHeroi;
        this.posicaoNoMapa = posicaoNoMapa;
    }
}
