package org.jogo;

public class CartaDano extends Carta {
    private int dano;

    public CartaDano(String nome, String descricao, int custo, int dano) {
        super(nome, descricao, custo);
        this.dano = dano;
    }

    public String getEfeitoCusto() {
        return "(Dano - " + this.dano + ") (Custo - " + getCusto() + ")";
    }

    public void usar(Tabuleiro tabuleiro) {
        tabuleiro.getInimigo().receberDano(dano);
    }

    public int getDano() {
        return dano;
    }

}
