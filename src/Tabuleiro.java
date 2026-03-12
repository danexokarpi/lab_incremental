public class Tabuleiro {
    private Heroi heroi;
    private Inimigo inimigo;

    public Tabuleiro (Heroi heroi, Inimigo inimigo){
        this.heroi = heroi;
        this.inimigo = inimigo;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public Inimigo getinimigo() {
        return inimigo;
    }


}
