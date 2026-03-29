package org.jogo;

public abstract class Efeito {
    private String nome;
    private String tipoDeEfeito;
    private Entidade dono;
    private int acumulos;
    private boolean ativo;

    protected Efeito (String nome, String tipoDeEfeito, Entidade dono, int acumulos){
        this.nome = nome;
        this.tipoDeEfeito = tipoDeEfeito;
        this.dono = dono;
        this.acumulos = acumulos;
        this.ativo = acumulos > 0;
    }

    public String getString() {
        return "[ " + nome + "(" + acumulos + "x) ]";
    }

    public String getNome() {
        return this.nome;
    }

    public int getAcumulos() {
        return this.acumulos;
    }

    public String getTipoDeEfeito(){
        return this.tipoDeEfeito;
    }
 
    public void somaAcumulos(int acumulosRecebidos) {
        this.acumulos += acumulosRecebidos;
    }

    public void subtrairAcumulo(){
        if(acumulos>0){
            acumulos--;
        if(acumulos == 0){
            this.ativo = false;
        }
        }
    }
    /* Os eventos são so seguintes
    1-Fim do round
    2-Fim do turno do Jogador
    3-Fim do turno de uma Entidade
    4-Ataque de Entidade
    5-Entidade recebe dano
    */
    public abstract void receberNotificacao(int evento);
}
