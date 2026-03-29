package org.jogo;
public class EfeitoRegeneracao extends Efeito {

    public EfeitoRegeneracao(int acumulos){
        super("Regeneracao", "Buff" , dono, acumulos, "Regeneração cura o equivalente ao número de stacks e perde um stack todo fim de round");
    }

    public void receberNotificacao(int notificacao){
        if (isAtivo() && notificacao == this.gatilhos(FimDoRound)){
            dono.curar(acumulos);
            subtrairAcumulo();
        }
    }

}
