package org.jogo;

public class ComandoPegarCarta {
    private Heroi heroi;
    private Carta carta;

    public ComandoPegarCarta(Heroi heroi, Carta carta) {
        this.heroi = heroi;
        this.carta = carta;
    }

    public void iniciar() {
        heroi.getInventario().add(carta);
    }
}
