package org.jogo;

import java.util.ArrayList;

public class ComandoComprarCarta implements ComandoLoja {
    private Heroi heroi;
    private Carta carta;
    private ArrayList<Carta> estoque;
    private int cursor;

    public ComandoComprarCarta(Heroi heroi, int cursor, ArrayList<Carta> estoque){
        this.carta = estoque.get(cursor);
        this.heroi = heroi;
        this.estoque = estoque;
        this.cursor = cursor;
    }
    public boolean podeExecutar(){
        return heroi.getOuro() >= carta.getPreco() && !heroi.getInventario().isEmpty();
    }
    public void executar(){
        int preco = carta.getPreco();
        heroi.alterarOuro(-preco);
        heroi.getInventario().add(carta);
        estoque.remove(cursor);
        
            
    }
}
