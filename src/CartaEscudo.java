public class CartaEscudo extends Carta {
    private int escudo;

    public CartaEscudo(String nome, String descricao, int custo, int escudo) {
        super(nome, descricao, custo);
        this.escudo = escudo;
    }

    public void usar(Tabuleiro tabuleiro){
        tabuleiro.getHeroi().receberEscudo(escudo);
    }

    public boolean usarSePossivel(Heroi heroi, int energiaDisponivel) {
        if (energiaDisponivel < getCusto()) {
            return false;
        }
        heroi.receberEscudo(escudo);
        return true;
    }

    public int getEscudo() {
        return escudo;
    }

}
