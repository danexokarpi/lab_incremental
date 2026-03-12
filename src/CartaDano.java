public class CartaDano extends Carta {
    private int dano;

    public CartaDano(String nome, String descricao, int custo, int dano) {
        super(nome, descricao, custo);
        this.dano = dano;
    }

    public void usar(Tabuleiro tabuleiro) {
        tabuleiro.getinimigo().receberDano(dano);
    }

    public boolean usarSePossivel(Inimigo inimigo, int energiaDisponivel) {
        if (energiaDisponivel < getCusto()) {
            return false;
        }
        inimigo.receberDano(dano);
        return true;
    }

    public int getDano() {
        return dano;
    }

}
