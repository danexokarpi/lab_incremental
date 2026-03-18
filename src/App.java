import java.util.ArrayList;

public class App {

    public static void main(String[] args) {

        ArrayList<Carta> listaInventarioJogador = new ArrayList<Carta>();

        Heroi heroi = new Heroi("Capitão Cabra", 10, 0);
        Inimigo inimigo = new Inimigo("Gosma", 10, 0, 2);
        int energiaMaxima = 3;
        Tabuleiro tabuleiro = new Tabuleiro(heroi, inimigo, listaInventarioJogador, energiaMaxima);

        CartaDano laserCinético = new CartaDano("Laser Cinético",
                "Atira luz estimulada por emissão de radiação, pesquise a sigla!", 1, 3);
        CartaDano sabreDeFotons = new CartaDano("Sabre de Fótons",
                "Consegue atravessar quase qualquer coisa. \nQualquer semelhança é mera coencidência", 2, 4);
        CartaEscudo escudoEletromagneticoGrande = new CartaEscudo("Escudo Eletromagnético Grande",
                "Crie um campo magnético em volta de si; a única fraqueza do sabre de fótons", 2, 5);
        CartaEscudo escudoEletromagneticoPequeno = new CartaEscudo("Escudo Eletromagnético Pequeno",
                "Crie um campo magnético em volta de si; a única fraqueza do sabre de fótons", 1, 2);

        listaInventarioJogador.add(laserCinético);
        listaInventarioJogador.add(sabreDeFotons);
        listaInventarioJogador.add(escudoEletromagneticoGrande);
        listaInventarioJogador.add(escudoEletromagneticoPequeno);

        while (heroi.estaVivo() && inimigo.estaVivo()) {
            tabuleiro.novoTurno();
        }

        if (heroi.estaVivo()) {
            System.out.println("\nParabéns! Você GANHOU\n");
        } else {
            System.out.println("\nQue pena! Você perdeu\n");
        }

    }

}
