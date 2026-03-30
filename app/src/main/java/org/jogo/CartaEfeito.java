package org.jogo;
import java.util.ArrayList;
public class CartaEfeito extends Carta {
    private FabricaDeEfeito fabricaDeEfeito;
    private String areaDeEfeito;
    public CartaEfeito(String nome, String descricao, int custo, FabricaDeEfeito fabricaDeEfeito, String areaDeEfeito){
        super(nome, descricao, custo);
        this.fabricaDeEfeito = fabricaDeEfeito;
        this.areaDeEfeito = areaDeEfeito;
    }


    public boolean usar(Tabuleiro tabuleiro){
        Efeito efeito = fabricaDeEfeito.criarEfeito();

        if (efeito.getTipoDeEfeito() == "Buff"){
            tabuleiro.getHeroi().aplicarEfeito(efeito);
            return true;
        }else if (efeito.getTipoDeEfeito() == "Debuff"){
            if(this.areaDeEfeito == "Todos"){
                ArrayList<Inimigo> inimigos = tabuleiro.getInimigos();
                for(Inimigo inimigo : inimigos){
                    inimigo.aplicarEfeito(efeito);
                }
            }else if(this.areaDeEfeito == "Unico"){
                Inimigo inimigo = tabuleiro.escolherUmInimigo();
                if(inimigo != null){
                    inimigo.aplicarEfeito(efeito);
                    return true;
                }else{
                    return false;
                }
            }
                
        }
        return false;
    }

    public String getEfeitoCusto(){
        Efeito efeito = fabricaDeEfeito.criarEfeito();
        return "(Causa - " + efeito.getNome() + " por " + efeito.getAcumulos() +" turnos) (Custo - " + getCusto() + ")";
    }
}  
