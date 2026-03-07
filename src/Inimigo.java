public class Inimigo {
    public String nome;
    public int vidaMaxima;
    public int escudo;
    public int vida;
    public int dano;

    public Inimigo(String nome, int vidaMaxima, int escudo, int dano) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.escudo = escudo;
        this.vida = vidaMaxima;
        this.dano = dano;
    }

    public void receberDano(int dano) {
        int danoVerdadeiro = escudo - dano;
        if (danoVerdadeiro < 0) {
            vida -= Math.abs(danoVerdadeiro);
            escudo = 0;
        } else if (danoVerdadeiro == 0) {
            escudo = 0;
        } else {
            escudo = danoVerdadeiro;
        }

    }

    public void receberEscudo(int escudoRecebido) {
        escudo += escudoRecebido;
    }

    public boolean estaVivo() {
        if (vida <= 0) {
            return false;
        } else {
            return true;
        }
    }
}

