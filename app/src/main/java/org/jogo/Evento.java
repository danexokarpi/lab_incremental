package org.jogo;

public abstract class Evento {
    private boolean ganhou;

    public abstract void iniciar();

    public boolean ganhou() {
        return ganhou;
    }

    public void setGanhou(boolean ganhou) {
        this.ganhou = ganhou;
    }
}
