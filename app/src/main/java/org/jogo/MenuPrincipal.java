package org.jogo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPrincipal {
    private static final Scanner scanner = new Scanner(System.in);

    public static void titulo() {
        InputStream titulo = MenuPrincipal.class.getResourceAsStream("/titulo.txt");
        Scanner scannerTitulo = new Scanner(titulo);
        clearScreen();
        while (scannerTitulo.hasNextLine()) {
            System.out.println(scannerTitulo.nextLine());
        }
        scannerTitulo.close();
        scanner.nextLine();
    }

    public static int perguntaProximaPosicao(ArrayList<Integer> opcoesDeCaminho) {
        clearScreen();
        System.out.println("Qual caminho você deseja seguir agora?");
        int opcaoEscolhida = 0;
        while (opcaoEscolhida <= 0 || opcaoEscolhida >= opcoesDeCaminho.size()) {
            switch (opcoesDeCaminho.size()) {
                case 1:
                    System.out.println("1 - Seguir em frente");
                    break;
                case 2:
                    System.out.println("1 - Seguir pela esquerda");
                    System.out.println("2- Seguir pela direita");
                    break;
                case 3:
                    System.out.println("1 - Seguir pela esquerda");
                    System.out.println("2 - Seguir pelo meio");
                    System.out.println("3 - Seguir pela direita");
                    break;
            }
            opcaoEscolhida = scanner.nextInt();
        }
        return opcaoEscolhida - 1;
    }

    public static void finalDoJogo() {
        clearScreen();
        System.out.println("Parabéns!!! Você completou o jogo!\n" +
                "Muito obrigado por jogar.\n" +
                "Produzido por: Danilo e Vítor");
    }

    /**
     * Limpa a tela do console.
     *
     * Utiliza códigos ANSI para limpar a saída do terminal.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
