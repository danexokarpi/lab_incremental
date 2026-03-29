package org.jogo;
public class EfeitoVeneno extends Efeito {
    public EfeitoVeneno(Entidade dono, int acumulos){
        super("Veneno", "Debuff", dono, acumulos);
    }

    public void receberNotificacao(int notificacao){
        if (isAtivo() && notificacao == this.gatilhos(FimDoRound)){
            dono.receberDano(acumulos);
            subtrairAcumulo();
        }
    }
}   
