package org.jogo;

import java.util.ArrayList;

public class CartaDano extends Carta {
    private int dano;
    private String areaDeEfeito;

    public CartaDano(String nome, String descricao, int custo, int dano, String areaDeEfeito) {
        super(nome, descricao, custo);
        this.dano = dano;
        this.areaDeEfeito = areaDeEfeito;
    }

    public String getEfeitoCusto() {
        return "(Dano - " + this.dano + ") (Custo - " + getCusto() + ")";
    }

    public boolean usar(Tabuleiro tabuleiro) {
        if (areaDeEfeito.equals("Unico")) {
            Inimigo inimigo = tabuleiro.escolherUmInimigo();
            if (inimigo != null) {
                inimigo.receberDano(dano);
                tabuleiro.adicionarAoHistorico('A', tabuleiro.getHeroi(), inimigo, dano);
                return true;
            } else {
                return false;
            }
        } else if (areaDeEfeito.equals("Todos")) {
            ArrayList<Inimigo> inimigos = tabuleiro.getInimigos();
            for (Inimigo inimigo : inimigos) {
                inimigo.receberDano(dano);
            }
        }
        return false;
    }

    public int getDano() {
        return dano;
    }

}
