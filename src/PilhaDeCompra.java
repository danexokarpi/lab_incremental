import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ArrayList;
import java.util.Random;

public class PilhaDeCompra {
    private Deque<Carta> pilha = new ArrayDeque<Carta>();

    public PilhaDeCompra(ArrayList<Carta> cartasInventario) {
        Random random = new Random();
        int numeroDeCartasDisponiveis = cartasInventario.size();
        int[] indexesList = new int[numeroDeCartasDisponiveis];
        for (int i = 0; i < numeroDeCartasDisponiveis; i++) {
            indexesList[i] = i;
        }
        for (int tamanhoDaPilha = 0; tamanhoDaPilha < numeroDeCartasDisponiveis; tamanhoDaPilha++){
            int j = random.nextInt(numeroDeCartasDisponiveis - tamanhoDaPilha);
            pilha.push(cartasInventario.get(indexesList[j]));
            indexesList[j] = indexesList[numeroDeCartasDisponiveis-(1+tamanhoDaPilha)];
        }
        
    }
    
    public Carta pop() {
        return pilha.pollFirst();
    }

    public void push(Carta carta) {
        pilha.push(carta);
    }

    public boolean isempty(){
        return pilha.size() == 0;
    }
}
