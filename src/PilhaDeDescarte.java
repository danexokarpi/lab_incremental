import java.util.ArrayList;
import java.util.Random;

public class PilhaDeDescarte {
    private ArrayList<Carta> pilha = new ArrayList<Carta>();

    public PilhaDeDescarte() {
        //
    }

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
    }
}
