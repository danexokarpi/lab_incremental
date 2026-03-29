package org.jogo;
public class EfeitoRegeneracao extends Efeito {
    public EfeitoRegeneracao(Entidade dono, int acumulos){
        super("Regeneracao", "Buff" , dono, acumulos);
    }
    public void receberNotificacao(int notificacao){
        if (isAtivo() && notificacao == this.gatilhos(FimDoRound)){
            dono.curar(acumulos);
            subtrairAcumulo();
        }
        
    }
}
