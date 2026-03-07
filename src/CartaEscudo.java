public class CartaEscudo {
    private String nome;
    private int custo;
    private int escudo;

    public CartaEscudo(String nome, int custo, int escudo) {
        this.nome = nome;
        this.custo = custo;
        this.escudo = escudo;
    }

    public boolean usarSePossivel(Heroi heroi, int energiaDisponivel) {
        if (energiaDisponivel < custo) {
            return false;
        }
        heroi.receberEscudo(escudo);
        return true;
    }

    public String getNome() {
        return nome;
    }

    public int getCusto () {
        return custo;
    }

    public int getEscudo() {
        return escudo;
    }

}
