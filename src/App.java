import java.util.ArrayList;
import java.util.Arrays;

public class App {

    private static ArrayList<Carta> criaBaralho() {
        CartaDano laserCinético = new CartaDano("Laser Cinético",
                "Atira luz estimulada por emissão de radiação, pesquise a sigla!", 1, 3);
        CartaDano sabreDeFotons = new CartaDano("Sabre de Fótons",
                "Consegue atravessar quase qualquer coisa. \nQualquer semelhança é mera coencidência", 2, 4);
        CartaEscudo escudoEletromagneticoGrande = new CartaEscudo("Escudo Eletromagnético Grande",
                "Crie um campo magnético em volta de si; a única fraqueza do sabre de fótons", 2, 5);
        CartaEscudo escudoEletromagneticoPequeno = new CartaEscudo("Escudo Eletromagnético Pequeno",
                "Crie um campo magnético em volta de si; a única fraqueza do sabre de fótons", 1, 2);

        Carta[] listaBaralho = { laserCinético, sabreDeFotons,
                escudoEletromagneticoGrande, escudoEletromagneticoPequeno };
        return new ArrayList<Carta>(Arrays.asList(listaBaralho));
    }

    public static void main(String[] args) {
        Heroi heroi = new Heroi("Capitão Cabra", 10, 0);
        Inimigo inimigo = new Inimigo("Gosma", 10, 0, 2);
        int energiaMaxima = 3;

        ArrayList<Carta> baralho = criaBaralho();

        Tabuleiro tabuleiro = new Tabuleiro(heroi, inimigo, baralho, energiaMaxima, 2);
        tabuleiro.novaBatalha();
    }

}
