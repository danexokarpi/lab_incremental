public class Inimigo {
    public String nome;
    public int vidaMaxima;
    public int escudo;
    public int vida;

    public Inimigo(String nome, int vidaMaxima, int escudo) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.escudo = escudo;
        this.vida = vidaMaxima;
    }

    public void receberDano(int dano) {
        int dano_verdadeiro = escudo - dano;
        if (dano_verdadeiro < 0) {
            vida -= Math.abs(dano_verdadeiro);
            escudo = 0;
        } else if (dano_verdadeiro == 0) {
            escudo = 0;
        } else {
            escudo = dano_verdadeiro;
        }

    }

    public void receberEscudo(int escudoRecebido) {
        escudo += escudoRecebido;
    }

    public boolean estaVivo() {
        if (vida <= 0) {
            return true;
        } else {
            return false;
        }
    }
}

