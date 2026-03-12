import java.util.ArrayDeque;
import java.util.Deque;

public class PilhaDeDescarte {
    private Deque<Carta> pilha = new ArrayDeque<Carta>();

    public PilhaDeDescarte() {
        //
    }

    public Carta pop() {
        return pilha.pollFirst();
    }

    public void push(Carta carta) {
        pilha.push(carta);
    }
}
