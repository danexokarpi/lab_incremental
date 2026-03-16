public class CartaEscudo extends Carta {
    private int escudo;

    public CartaEscudo(String nome, String descricao, int custo, int escudo) {
        super(nome, descricao, custo);
        this.escudo = escudo;
    }

    public boolean usarSePossivel(Tabuleiro tabuleiro, int energiaDisponivel){
        if (energiaDisponivel < getCusto()) {
            return false;
        }
        tabuleiro.getHeroi().receberEscudo(escudo);
        return true;
        
    }

    public int getEscudo() {
        return escudo;
    }

}
