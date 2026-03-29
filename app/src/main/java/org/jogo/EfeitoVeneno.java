package org.jogo;
public class EfeitoVeneno extends Efeito {
    public EfeitoVeneno (int acumulos){
        super("Veneno", "Debuff", dono, acumulos, "Veneno causa dano equivalente ao número de stacks e perde um stack todo fim de round");
    }

    public void receberNotificacao(int notificacao){
        if (isAtivo() && notificacao == this.gatilhos(FimDoRound)){
            dono.receberDano(acumulos);
            subtrairAcumulo();
        }
    }
}   
