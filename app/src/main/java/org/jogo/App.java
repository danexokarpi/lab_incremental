package org.jogo;

import java.util.ArrayList;
import java.util.Arrays;

public class App {

    /**
     * Inicializa cartas e retorna um baralho em forma de ArrayList.
     *
     * @return lista contendo todas as cartas disponíveis para o baralho
     *         do jogador
     */
    private static ArrayList<Carta> criaBaralho() {
        CartaDano laserCinético = new CartaDano("Laser Cinético",
                "Atira luz estimulada por emissão de radiação, pesquise a sigla!", 1, 3, "Unico");
        CartaDano sabreDeFotons = new CartaDano("Sabre de Fótons",
                "Consegue atravessar quase qualquer coisa. \nQualquer semelhança é mera coincidência", 2, 4, "Unico");
        CartaEscudo escudoEletromagneticoGrande = new CartaEscudo("Escudo Eletromagnético Grande",
                "Crie um campo magnético em volta de si; a única fraqueza do sabre de fótons", 2, 5);
        CartaEscudo escudoEletromagneticoPequeno = new CartaEscudo("Escudo Eletromagnético Pequeno",
                "Crie um campo magnético em volta de si; a única fraqueza do sabre de fótons", 1, 2);
        CartaEfeito stimPack = new CartaEfeito("Stimpack",
                "Uma grande dose de seilá o que direto nas suas veias, vai curar quase qualquer ferida," +
                "menos as psicológicas", 2, new FabricaDeEfeito("regeneracao", 3), "Unico");
        CartaEfeito frascoDeVeneno = new CartaEfeito("Frasco de Veneno", "Um pequeno fraco com veneno concentrado, lance apenas em inimigos!",
                2 , new FabricaDeEfeito("veneno", 3), "Unico");
        Carta[] listaBaralho = { laserCinético, sabreDeFotons,
                escudoEletromagneticoGrande, escudoEletromagneticoPequeno,
                stimPack, frascoDeVeneno};
        return new ArrayList<Carta>(Arrays.asList(listaBaralho));
    }

    public static void main(String[] args) {
        Heroi heroi = new Heroi("Capitão Cabra", 15, 0);
        Inimigo inimigo1 = new Inimigo("Escorpião Gigante", 20, 0, 4, 0, 2, new FabricaDeEfeito("veneno", 3),
                new char[] { 'A', 'E', 'U' });
        Inimigo inimigo2 = new Inimigo("Barata Radioativa", 10, 0, 2, 0, 2, new FabricaDeEfeito(null, 0),
                new char[] { 'A', 'E' });
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        inimigos.add(inimigo1);
        inimigos.add(inimigo2);
        int energiaMaxima = 3;

        ArrayList<Carta> baralho = criaBaralho();

        Tabuleiro tabuleiro = new Tabuleiro(heroi, inimigos, baralho, energiaMaxima, 2);
        tabuleiro.novaBatalha();
    }

}
