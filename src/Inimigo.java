public class Inimigo {
    private String nome;
    private int vidaMaxima;
    private int escudo;
    private int vida;
    private int dano;

    public Inimigo(String nome, int vidaMaxima, int escudo, int dano) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.escudo = escudo;
        this.vida = vidaMaxima;
        this.dano = dano;
    }
    // Sistema de dano verdadeiro, basicamente reduz o escudo do dano e aplica o dano absoluto na vida do heroi caso o escudo for menor
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

    public String getNome(){
        return this.nome;
    }

    public int getVidaMaxima(){
        return this.vidaMaxima;
    }

    public int getEscudo(){
        return this.escudo;
    }

    public int getvida(){
        return this.vida;
    }

    public int getDano(){
        return this.dano;
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

