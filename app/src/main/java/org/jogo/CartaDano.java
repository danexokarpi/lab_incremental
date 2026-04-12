package org.jogo;

import java.util.ArrayList;

public class CartaDano extends Carta {
    private int dano;

    public CartaDano(String nome, String descricao, int custo, int dano, String areaDeEfeito) {
        super(nome, descricao, custo, areaDeEfeito);
        this.dano = dano;
    }

    public boolean usar(Batalha batalha) {
        if (getAreaDeEfeito().equals("Unico")) {
            Inimigo inimigo = batalha.escolherUmInimigo();
            if (inimigo != null) {
                inimigo.receberDano(dano);
                batalha.adicionarAoHistorico('A', batalha.getHeroi(), inimigo, dano);
                return true;
            } else {
                return false;
            }
        } else if (getAreaDeEfeito().equals("Todos")) {
            ArrayList<Inimigo> inimigos = batalha.getInimigos();
            for (Inimigo inimigo : inimigos) {
                inimigo.receberDano(dano);
                batalha.adicionarAoHistorico('A', batalha.getHeroi(), inimigo, dano);
            }
            return true;
        }
        return false;
    }

    public String getEfeitoCustoAoE() {
        return "(Dano - " + this.dano + ") (Custo - " + getCusto() + ") " + "(Alvo - " + getAreaDeEfeito()
                + ")";
    }

    public int getDano() {
        return dano;
    }

}
