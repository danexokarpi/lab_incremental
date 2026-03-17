import java.util.ArrayList;
import java.util.Random;

public class PilhaDeDescarte {
    private ArrayList<Carta> pilha = new ArrayList<Carta>();
<<<<<<< HEAD
    private Random random = new Random();
=======
>>>>>>> eec19276c9188608eeb640d8b21bf44309deac73

    public PilhaDeDescarte(Carta[] cartas) {
        for (int i = 0; i < cartas.length; i++) {
            this.pilha.add(cartas[i]);
        }
    }

<<<<<<< HEAD
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
=======
    public void push(Carta carta) {
        pilha.addFirst(carta);
    }

    public void reabastecerCompra(PilhaDeCompra pilhaDeCompra){
        Random random = new Random();
        while(pilha.size() != 0){
            int i = random.nextInt(pilha.size());
            pilhaDeCompra.push(pilha.get(i));
            pilha.remove(i);
        }
    }

    public boolean isempty(){
        return pilha.size() == 0;
>>>>>>> eec19276c9188608eeb640d8b21bf44309deac73
    }
}
