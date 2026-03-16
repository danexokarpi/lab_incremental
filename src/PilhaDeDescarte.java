import java.util.ArrayList;
import java.util.Random;

public class PilhaDeDescarte {
    private ArrayList<Carta> pilha = new ArrayList<Carta>();
    private Random random = new Random();

    public PilhaDeDescarte(Carta[] cartas) {
        for (int i = 0; i < cartas.length; i++) {
            this.pilha.add(cartas[i]);
        }
    }

    public Carta pop() {
        Carta primeiraCarta = pilha.get(0);
        pilha.remove(0);
        return primeiraCarta;
    }

    public Carta popRandom() {
        int randomIndex = random.nextInt();
        Carta cartaEscolhida = pilha.get(randomIndex);
        pilha.remove(randomIndex);
        return cartaEscolhida;
    }

    public void push(Carta carta) {
        pilha.add(carta);
    }
}
