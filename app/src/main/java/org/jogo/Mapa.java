package org.jogo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Mapa {

    private ArrayList<NoMapa> listaDeNos;
    private ArrayList<Tabuleiro> listaDeTabuleiros;

    public Mapa(String arquivoDoMapa) {
        InputStream esqueletoDoMapa = MenuPrincipal.class.getResourceAsStream("/esqueletoDoMapa.txt");
        if (esqueletoDoMapa == null) {
            throw new IllegalArgumentException("Nenhum arquivo com nome correspondente");
        }
        Scanner scannerMapa = new Scanner(esqueletoDoMapa);
        int index = 0;
        while (scannerMapa.hasNextLine()) {
            NoMapa noAtual = new NoMapa(index);
            listaDeNos.add(noAtual);
            String[] filhosDoNo = scannerMapa.nextLine().split(" ");
            for (String filho : filhosDoNo) {
                noAtual.addFilho(Integer.parseInt(filho));
            }
            index++;
        }
        scannerMapa.close();
    }

    public Tabuleiro getTabuleiro(int idNo) {
        return listaDeTabuleiros.get(idNo);
    }

}
