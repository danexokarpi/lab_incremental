import java.util.ArrayDeque;
import java.util.Deque;

public class PilhaDeCompra {
    private Deque<Carta> pilha = new ArrayDeque<Carta>();

    public PilhaDeCompra() {
        //
    }

    public Carta pop() {
        return pilha.pollFirst();
    }

    public void push(Carta carta) {
        pilha.push(carta);
    }
}
