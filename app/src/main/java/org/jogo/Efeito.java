package org.jogo;

public class Efeito {
    private String nome;
    private Entidade dono;
    private int acumulos;

    public String getString() {
        return "[ " + nome + "(" + acumulos + "x) ]";
    }

    public String getNome() {
        return this.nome;
    }

    public int getAcumulos() {
        return this.acumulos;
    }

    public void somaAcumulos(int acumulosRecebidos) {
        this.acumulos += acumulosRecebidos;
    }
}
