package org.jogo;
public class EfeitoVeneno extends Efeito {
    public EfeitoVeneno (int acumulos){
        super("Veneno", "Debuff", acumulos, "Veneno causa dano equivalente ao número de stacks e perde um stack todo fim de round");
    }

    public void receberNotificacao(Evento eventoOcorrido){
        if (isAtivo() && eventoOcorrido == Evento.FimDoRound){
            getDono().receberDano(getAcumulos());
            subtrairAcumulo();
        }
    }
}   
