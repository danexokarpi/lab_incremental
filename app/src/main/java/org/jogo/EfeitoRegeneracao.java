package org.jogo;
public class EfeitoRegeneracao extends Efeito {

    public EfeitoRegeneracao(int acumulos){
        super("Regeneração", "Buff" , acumulos, "Regeneração cura o equivalente ao número de stacks e perde um stack todo fim de round");
    }

    public void receberNotificacao(Evento eventoOcorrido){
        if (isAtivo() && eventoOcorrido == Evento.FimDoRound){
            getDono().curar(getAcumulos());
            subtrairAcumulo();
        }
    }

}
