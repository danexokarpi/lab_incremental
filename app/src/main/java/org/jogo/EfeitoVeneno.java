package org.jogo;

/**
 * O efeito de veneno, em todo fim de turno, da dano no seu recepetor de acordo com a quantidade de stacks desse efeito que ele possui, e a cada turno uma stack é consumida.
*/
public class EfeitoVeneno extends Efeito {
    public EfeitoVeneno (int acumulos){
        super("Veneno", "Debuff", acumulos, "Veneno causa dano equivalente ao número de stacks e perde um stack todo fim de round");
    }

    public void receberNotificacao(Evento eventoOcorrido){
        if (isAtivo() && eventoOcorrido == Evento.FimDoRound){
            getDono().receberDanoVerdadeiro(getAcumulos());
            subtrairAcumulo();
        }
    }
}   
