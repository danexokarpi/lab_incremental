public class MaoDoJogador {
    private int capacidade;
    private int tamanho;
    private Carta[] mao;
    // Usaremos lista mesmo ou não?

    public MaoDoJogador(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.mao = new Carta[capacidade];
    }

    public boolean estaCheia() {
        return tamanho == capacidade;
    }

    public void addCarta(Carta carta) {
        if (estaCheia()) {
            throw new java.lang.RuntimeException("Mão já está cheia. Não é possível adicionar mais cartas.");
        }
        this.mao[tamanho] = carta;
        this.tamanho++;
    }

}
