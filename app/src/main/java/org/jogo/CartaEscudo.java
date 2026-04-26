package org.jogo;

public class CartaEscudo extends Carta {
    private int escudo;

    public CartaEscudo(String nome, String descricao, int custo, int escudo, int preco) {
        super(nome, descricao, custo, "Unico", preco);
        this.escudo = escudo;
    
    }

    public String getEfeitoCustoAoE() {
        return "(Escudo - " + this.escudo + ") (Custo - " + getCusto() + ")";
    }

    public boolean usar(Batalha tabuleiro) {
        tabuleiro.getHeroi().receberEscudo(escudo);
        tabuleiro.adicionarAoHistorico('E', tabuleiro.getHeroi(), tabuleiro.getHeroi(), escudo);
        return true;
    }

    public int getEscudo() {
        return escudo;
    }

}
