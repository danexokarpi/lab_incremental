package org.jogo;

public abstract class Efeito {
    private String nome;
    private String tipoDeEfeito;
    private Entidade dono;
    private int acumulos;
    private boolean ativo;
    private String descricao;

    protected Efeito (String nome, String tipoDeEfeito, int acumulos, String descricao){
        this.nome = nome;
        this.tipoDeEfeito = tipoDeEfeito;
        this.dono = null;
        this.acumulos = acumulos;
        this.descricao = descricao;
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

    public String getDescricao(){
        return descricao;
    }

    public Entidade getDono(){
        return this.dono;
    }

    public void setDono(Entidade dono){
        this.dono = dono;
    }
 
    public void somaAcumulos(int acumulosRecebidos) {
        this.acumulos += acumulosRecebidos;
    }

    public boolean isAtivo(){
        return ativo;
    }

    public void subtrairAcumulo(){
        if(acumulos > 0){
            acumulos--;
            if(acumulos == 0){
                this.ativo = false;
            }
        }
    }
    
    public abstract void receberNotificacao(Evento eventoOcorrido);
}
