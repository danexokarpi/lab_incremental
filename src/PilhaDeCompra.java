import java.util.ArrayList;
import java.util.Random;

public class PilhaDeCompra {
    private ArrayList<Carta> pilha = new ArrayList<Carta>();
    private static Random random = new Random();

    public PilhaDeCompra(ArrayList<Carta> cartasInventario) {
        for (Carta carta : cartasInventario) {
            this.pilha.add(carta);
        }
    }

    public void reabastecerMao(MaoDoJogador maoDoJogador) {
        while (!maoDoJogador.estaCheia()) {
            maoDoJogador.addCarta(this.popRandom());
        }
    }

    public Carta popRandom() {
        int randomIndex = random.nextInt(pilha.size());
        return this.pilha.remove(randomIndex);
    }

    public void push(Carta carta) {
        this.pilha.add(carta);
    }

    public boolean isEmpty() {
        return pilha.size() == 0;
    }
}
