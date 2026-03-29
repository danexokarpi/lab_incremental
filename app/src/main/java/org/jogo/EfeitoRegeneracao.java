package org.jogo;
public class EfeitoRegeneracao extends Efeito {
    private int intensidade;
    public EfeitoRegeneracao(Entidade dono, int acumulos, int intensidade){
        super("Regeneracao", "Buff" , dono, acumulos);
        this.intensidade = intensidade;
    }
    public void receberNotificacao(int notificacao){
        dono.curar(entensidade);
        subtrairAcumulo();
    }
}
