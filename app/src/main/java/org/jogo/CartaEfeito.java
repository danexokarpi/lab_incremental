package org.jogo;

import java.util.ArrayList;

public class CartaEfeito extends Carta {
    private FabricaDeEfeito fabricaDeEfeito;
    private String areaDeEfeito;

    public CartaEfeito(String nome, String descricao, int custo, FabricaDeEfeito fabricaDeEfeito, String areaDeEfeito) {
        super(nome, descricao, custo);
        this.fabricaDeEfeito = fabricaDeEfeito;
        this.areaDeEfeito = areaDeEfeito;
    }

    public boolean usar(Batalha batalha) {
        Efeito efeito = fabricaDeEfeito.criarEfeito();

        if (efeito.getTipoDeEfeito().equals("Buff")) {
            batalha.getHeroi().aplicarEfeito(efeito);
            return true;
        } else if (efeito.getTipoDeEfeito().equals("Debuff")) {
            if (this.areaDeEfeito.equals("Todos")) {
                ArrayList<Inimigo> inimigos = batalha.getInimigos();
                for (Inimigo inimigo : inimigos) {
                    inimigo.aplicarEfeito(efeito);
                }
            } else if (this.areaDeEfeito.equals("Unico")) {
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

    public String getEfeitoCusto() {
        Efeito efeito = fabricaDeEfeito.criarEfeito();
        return "(Causa - " + efeito.getNome() + " por " + efeito.getAcumulos() + " turnos) (Custo - " + getCusto()
                + ")";
    }
}
