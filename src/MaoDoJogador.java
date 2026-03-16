import java.util.ArrayList;

public class MaoDoJogador {
    private int capacidade;
    private int tamanho;
    private ArrayList<Carta> mao;
    // Usaremos lista mesmo ou não?

    public MaoDoJogador(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.mao = new ArrayList<Carta>();
    }

    public boolean estaCheia() {
        return tamanho == capacidade;
    }
    public int getCapacidade(){
        return capacidade;
    }
    public int getTamanho() {
        return tamanho;
    }
    public void addCarta(Carta carta) {
        if (estaCheia()) {
            throw new java.lang.RuntimeException("Mão já está cheia. Não é possível adicionar mais cartas.");
        }
        this.mao.add(tamanho, carta);
        this.tamanho++;
    }
    public Carta getCarta(int index){
        return this.mao.get(index);
    }
    public void removeCarta(int index){
        this.mao.remove(index);
        this.tamanho --;
    }

}
