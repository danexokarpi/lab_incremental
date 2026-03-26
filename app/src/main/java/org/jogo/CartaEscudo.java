package org.jogo;

public class CartaEscudo extends Carta {
    private int escudo;

    public CartaEscudo(String nome, String descricao, int custo, int escudo) {
        super(nome, descricao, custo);
        this.escudo = escudo;
    }

    public String getEfeitoCusto() {
        return "(Escudo - " + this.escudo + ") (Custo - " + getCusto() + ")";
    }

    public void usar(Tabuleiro tabuleiro) {
        tabuleiro.getHeroi().receberEscudo(escudo);
    }

    public int getEscudo() {
        return escudo;
    }

}
