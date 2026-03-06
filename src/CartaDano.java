public class CartaDano {
    public String nome;
    public int custo;
    public int dano;

    public CartaDano(String nome, int custo, int dano) {
        this.nome = nome;
        this.custo = custo;
        this.dano = dano;
    }

    public int usar(Inimigo inimigo, int energiaDisponivel) {
        if (energiaDisponivel < custo) {
            return 1;
        }
        inimigo.receberDano(dano);
        return 0;
    }

}
