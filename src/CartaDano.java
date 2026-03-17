public class CartaDano extends Carta {
    private int dano;

    public CartaDano(String nome, String descricao, int custo, int dano) {
        super(nome, descricao, custo);
        this.dano = dano;
    }

    public boolean usarSePossivel(Tabuleiro tabuleiro, int energiaDisponivel) {
        if (energiaDisponivel < getCusto()) {
            return false;
        }
        tabuleiro.getinimigo().receberDano(dano);
        return true;
    
    }

    public int getDano() {
        return dano;
    }

}
