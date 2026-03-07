public class CartaDano {
    public String nome;
    public int custo;
    public int dano;

    public CartaDano(String nome, int custo, int dano) {
        this.nome = nome;
        this.custo = custo;
        this.dano = dano;
    }

    public boolean usar(Inimigo inimigo, int energiaDisponivel) {
        if (energiaDisponivel < custo) {
            return false;
        }
        inimigo.receberDano(dano);
        return true;
    }

}
