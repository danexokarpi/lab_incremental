import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class PilhaDeCompra {
    private Deque<Carta> pilha = new ArrayDeque<Carta>();

    public PilhaDeCompra(Carta[] cartasDisponiveis) {
        Random random = new Random();
        int numeroDeCartasDisponiveis = cartasDisponiveis.length;
        int[] indexesList = new int[numeroDeCartasDisponiveis];
        for (int i = 0; i < numeroDeCartasDisponiveis; i++) {
            indexesList[i] = i;
        }
        for (int tamanhoDaPilha = 0; tamanhoDaPilha < numeroDeCartasDisponiveis; tamanhoDaPilha++){
            int j = random.nextInt(numeroDeCartasDisponiveis - tamanhoDaPilha);
            indexesList[j] = indexesList[numeroDeCartasDisponiveis];
            pilha.push(cartasDisponiveis[indexesList[j]]);
        }
        
    }

    public Carta pop() {
        return pilha.pollFirst();
    }

    public void push(Carta carta) {
        pilha.push(carta);
    }
}
