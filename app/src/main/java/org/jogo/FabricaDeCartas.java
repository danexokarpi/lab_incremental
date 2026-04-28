package org.jogo;

import java.util.List;
import java.util.Random;

public class FabricaDeCartas {
    Random random = new Random();

    public Carta getCartaAleatoria() {
        List<Carta> cartas = List.of(
                new CartaDano("Revolver",
                        "O bom o velho conquistador do Oeste.", 1, 3, "Unico", 3),
                new CartaDano("Espingarda",
                        "Nada melhor do que chumbo grosso para acabar com eles.", 2, 7, "Unico", 4),
                new CartaDano("Escopeta Cerrada",
                        "Algum gênio cerrou o cano dessa escopeta, apesar de causar menos dano, acerta multiplos alvos.",
                        2, 2,
                        "Todos", 6),
                new CartaDano("Granada",
                        "3,2,1....KABUMMM!!!!!!.", 3, 4, "Todos", 8),
                new CartaDano("Mina Antipessoal",
                        "Ótima para OBLITERAR um indivídue em específico, manual de intruções não incluido", 3, 12,
                        "Unico", 12),
                new CartaEscudo("Escudo Eletromagnético Sobrecarregado",
                        "Crie um campo magnético em volta de si, bloquei quase todo tipo de dano", 3, 10, 5),
                new CartaEscudo("Escudo Eletromagnético Grande",
                        "Crie um campo eletromagnético em volta de si, bloquei quase todo tipo de dano", 2, 5, 3),
                new CartaEscudo("Escudo Eletromagnético Pequeno",
                        "Crie um campo magnético em volta de si, bloquei quase todo tipo de dano", 1, 2, 2),
                new CartaEfeito("Stimpack",
                        "Uma grande dose de seilá o que direto nas suas veias, vai curar quase qualquer ferida," +
                                "menos as psicológicas",
                        2, new FabricaDeEfeito("regeneracao", 3), "Unico", 15),
                new CartaEfeito("Frasco de Veneno",
                        "Um pequeno fraco com veneno concentrado, lance apenas em inimigos!",
                        2, new FabricaDeEfeito("veneno", 3), "Unico", 9),
                new CartaEfeito("Gás Mostarda",
                        "Uma arma quimica extremamente tóxica que afeta uma grande área.",
                        3, new FabricaDeEfeito("veneno", 2), "Todos", 6));
        return cartas.get(random.nextInt(cartas.size() - 1));
    }

    public Carta getCartaEspecialAleatoria() {
        List<Carta> cartas = List.of(
                new CartaDano("Granada",
                        "3,2,1....KABUMMM!!!!!!.", 3, 4, "Todos", 8),
                new CartaDano("Mina Antipessoal",
                        "Ótima para OBLITERAR um indivídue em específico, manual de intruções não incluido", 3, 12,
                        "Unico", 12),
                new CartaEfeito("Stimpack",
                        "Uma grande dose de seilá o que direto nas suas veias, vai curar quase qualquer ferida," +
                                "menos as psicológicas",
                        2, new FabricaDeEfeito("regeneracao", 3), "Unico", 15),
                new CartaEfeito("Frasco de Veneno",
                        "Um pequeno fraco com veneno concentrado, lance apenas em inimigos!",
                        2, new FabricaDeEfeito("veneno", 3), "Unico", 9));
        return cartas.get(random.nextInt(cartas.size() - 1));

    }

}
