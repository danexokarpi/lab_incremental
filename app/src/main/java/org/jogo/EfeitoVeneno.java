package org.jogo;
public class EfeitoVeneno extends Efeito {
    private int intensidade;
    public EfeitoVeneno(Entidade dono, int acumulos, int intensidade){
        super("Veneno", "Debuff", dono, acumulos);
        this.intensidade = intensidade;
    }

    public void receberNotificacao(int notificacao){
        dono.receberDano(intensidade);
        subtrairAcumulo();
    }
}
