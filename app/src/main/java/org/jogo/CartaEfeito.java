package org.jogo;
public class CartaEfeito extends Carta {
    private Efeito efeito;
    public CartaEfeito(String nome, String descricao, int custo, Efeito efeito){
        super(nome, descricao, custo);
        this.efeito = efeito;
    }


    public void usar(Tabuleiro tabuleiro){
        if (efeito.getTipoDeEfeito() == "Buff"){
            tabuleiro.getHeroi().aplicarEfeito(efeito);
        }else if (efeito.getTipoDeEfeito() == "Debuff"){
            tabuleiro.getInimigo().aplicarEfeito(efeito);
        }
    }

    public String getEfeitoCusto(){
        return "(Causa - " + efeito.getNome() + " por " + efeito.getAcumulos() +" turnos) (Custo - " + getCusto() + ")";
    }
}  
