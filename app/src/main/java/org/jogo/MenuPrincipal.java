package org.jogo;

import java.io.InputStream;
import java.util.Scanner;

public class MenuPrincipal {
    private static final Scanner scanner = new Scanner(System.in);

    public void titulo() {
        InputStream titulo = MenuPrincipal.class.getResourceAsStream("/titulo.txt");
        Scanner scannerTitulo = new Scanner(titulo);
        clearScreen();
        while (scannerTitulo.hasNextLine()) {
            System.out.println(scannerTitulo.nextLine());
        }
        scannerTitulo.close();
        scanner.nextLine();
    }

    /**
     * Limpa a tela do console.
     *
     * Utiliza códigos ANSI para limpar a saída do terminal.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
