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

    public boolean usar(Batalha batalha) {
        batalha.getHeroi().receberEscudo(escudo);
        batalha.adicionarAoHistorico('E', batalha.getHeroi(), batalha.getHeroi(), escudo);
        return true;
    }

    public int getEscudo() {
        return escudo;
    }

}
