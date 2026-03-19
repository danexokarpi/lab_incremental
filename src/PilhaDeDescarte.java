import java.util.ArrayList;
import java.util.Random;

public class PilhaDeDescarte {
    private ArrayList<Carta> pilha = new ArrayList<Carta>();
    private static Random random = new Random();

    public Carta popRandom() {
        int randomIndex = random.nextInt(pilha.size());
        return this.pilha.remove(randomIndex);
    }

    public void push(Carta carta) {
        pilha.add(carta);
    }

    public void reabastecerCompra(PilhaDeCompra pilhaDeCompra) {
        while (this.pilha.size() != 0) {
            pilhaDeCompra.push(this.popRandom());
        }
    }

    public boolean isEmpty() {
        return pilha.size() == 0;
    }
}
