package org.jogo;

public abstract class Carta {
    private String nome;
    private String descricao;
    private int custo;

    public Carta(String nome, String descricao, int custo) {
        this.nome = nome;
        this.descricao = descricao;
        this.custo = custo;
    }

    public abstract boolean usar(Batalha batalha);

    public abstract String getEfeitoCusto();

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCusto() {
        return custo;
    }

}
