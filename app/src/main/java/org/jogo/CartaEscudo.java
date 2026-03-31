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

    public boolean usar(Tabuleiro tabuleiro) {
        tabuleiro.getHeroi().receberEscudo(escudo);
        tabuleiro.adicionarAoHistorico('E', tabuleiro.getHeroi(), tabuleiro.getHeroi(), escudo);
        return true;
    }

    public int getEscudo() {
        return escudo;
    }

}
