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

    public boolean usar(Batalha batalha) {
        if (areaDeEfeito.equals("Unico")) {
            Inimigo inimigo = batalha.escolherUmInimigo();
            if (inimigo != null) {
                inimigo.receberDano(dano);
                batalha.adicionarAoHistorico('A', batalha.getHeroi(), inimigo, dano);
                return true;
            } else {
                return false;
            }
        } else if (areaDeEfeito.equals("Todos")) {
            ArrayList<Inimigo> inimigos = batalha.getInimigos();
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
