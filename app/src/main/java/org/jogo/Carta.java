package org.jogo;

public abstract class Carta {
    private String nome;
    private String descricao;
    private int custo;
    private String areaDeEfeito;

    public Carta(String nome, String descricao, int custo, String areaDeEfeito) {
        this.nome = nome;
        this.descricao = descricao;
        this.custo = custo;
        this.areaDeEfeito = areaDeEfeito;
    }

    public abstract boolean usar(Tabuleiro tabuleiro);

    public abstract String getEfeitoCustoAoE();

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAreaDeEfeito(){
        return areaDeEfeito;
    }

    public int getCusto() {
        return custo;
    }

}
