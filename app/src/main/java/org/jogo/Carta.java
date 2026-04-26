package org.jogo;

public abstract class Carta {
    private String nome;
    private String descricao;
    private int custo;
    private String areaDeEfeito;
    private int preco;


    protected Carta(){}

    public Carta(String nome, String descricao, int custo, String areaDeEfeito, int preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.custo = custo;
        this.areaDeEfeito = areaDeEfeito;
        this.preco = preco;
    }

    public abstract boolean usar(Batalha tabuleiro);

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

    public int getPreco(){
        return preco;
    }
}
