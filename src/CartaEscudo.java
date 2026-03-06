public class CartaEscudo {
    public String nome;
    public int custo;
    public int escudo;

    public CartaEscudo(String nome, int custo, int escudo) {
        this.nome = nome;
        this.custo = custo;
        this.escudo = escudo;
    }

    public int usar(Inimigo inimigo, int energiaDisponivel) {
        if (energiaDisponivel < custo) {
            return 1;
        }
        inimigo.receberEscudo(escudo);
        return 0;
    }
}
