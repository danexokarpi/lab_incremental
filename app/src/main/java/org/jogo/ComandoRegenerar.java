package org.jogo;

public class ComandoRegenerar {
    private Entidade alvoDaRegeneracao;

    public ComandoRegenerar(Entidade alvoDaRegeneracao) {
        this.alvoDaRegeneracao = alvoDaRegeneracao;
    }

    public void iniciar() {
        alvoDaRegeneracao.setarVida(alvoDaRegeneracao.getVidaMaxima());
    }
}
