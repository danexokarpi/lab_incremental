public class Inimigo extends Entidade {
    private int dano;

    public Inimigo(String nome, int vidaMaxima, int escudo, int dano) {
        super(nome, vidaMaxima, escudo);
        this.dano = dano;
    }

    public int getDano() {
        return this.dano;
    }

}
