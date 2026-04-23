package org.jogo;

/**
 * O efeito de regeneração, em todo fim de turno, concede vida a seu recepetor
 * de acordo com a quantidade de stacks desse efeito que ele possui, e a cada
 * turno uma stack é consumida.
 */
public class EfeitoRegeneracao extends Efeito {

    public EfeitoRegeneracao(int acumulos) {
        super("Regeneração", "Buff", acumulos,
                "Regeneração cura o equivalente ao número de stacks e perde um stack todo fim de round");
    }

    public void receberNotificacao(EventoDeBatalha eventoOcorrido) {
        if (isAtivo() && eventoOcorrido == EventoDeBatalha.FimDoRound) {
            getDono().curar(getAcumulos());
            subtrairAcumulo();
        }
    }

}
