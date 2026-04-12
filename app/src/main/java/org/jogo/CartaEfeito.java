package org.jogo;

import java.util.ArrayList;

public class CartaEfeito extends Carta {
    private FabricaDeEfeito fabricaDeEfeito;

    public CartaEfeito(String nome, String descricao, int custo, FabricaDeEfeito fabricaDeEfeito, String areaDeEfeito) {
        super(nome, descricao, custo, areaDeEfeito);
        this.fabricaDeEfeito = fabricaDeEfeito;
    }

    public boolean usar(Batalha batalha) {
        Efeito efeito = fabricaDeEfeito.criarEfeito();

        if (efeito.getTipoDeEfeito().equals("Buff")) {
            batalha.getHeroi().aplicarEfeito(efeito);
            return true;

        } else if (efeito.getTipoDeEfeito().equals("Debuff")) {
            if (getAreaDeEfeito().equals("Todos")) {
                ArrayList<Inimigo> inimigos = batalha.getInimigos();
                for (Inimigo inimigo : inimigos) {
                    inimigo.aplicarEfeito(efeito);
                }
                return true;

            } else if (getAreaDeEfeito().equals("Unico")) {
                Inimigo inimigo = batalha.escolherUmInimigo();
                if (inimigo != null) {
                    inimigo.aplicarEfeito(efeito);
                    return true;
                } else {
                    return false;
                }
            }

        }
        return false;
    }

    public String getEfeitoCustoAoE() {
        Efeito efeito = fabricaDeEfeito.criarEfeito();
        return "(Causa - " + efeito.getNome() + " por " + efeito.getAcumulos() + " turnos) (Custo - " + getCusto()
                + ") " + "(Alvo - " + getAreaDeEfeito()
                + ")";
    }
}
