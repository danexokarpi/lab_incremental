import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class PilhaDeCompra {
    private Deque<Carta> pilha = new ArrayDeque<Carta>();

    public PilhaDeCompra(Carta[] cartasDisponiveis) {
        Random random = new Random();
        int[] indexesList = new int[cartasDisponiveis.length];
        for (int i = 0; i < cartasDisponiveis.length; i++) indexesList[i] = i;
        for (int  = 0; i < cartasDisponiveis.length; i++){
            int j = random.nextInt((temp_list.length-1));
            pilha.push(temp_list[j]);
            temp_list.remove();
        }
        
    }

    public Carta pop() {
        return pilha.pollFirst();
    }

    public void push(Carta carta) {
        pilha.push(carta);
    }
}
