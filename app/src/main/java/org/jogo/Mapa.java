package org.jogo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Mapa {

    private ArrayList<NoMapa> listaDeNos;
    private ArrayList<Batalha> listaDeBatalhas;

    public Mapa(FabricaDeBatalha fabricaDeBatalha) {
        InputStream esqueletoDoMapa = MenuPrincipal.class.getResourceAsStream("/esqueletoDoMapa.txt");
        if (esqueletoDoMapa == null) {
            throw new IllegalArgumentException("Nenhum arquivo com nome correspondente");
        }
        Scanner scannerMapa = new Scanner(esqueletoDoMapa);
        int index = 0;
        while (scannerMapa.hasNextLine()) {
            NoMapa noAtual = new NoMapa(index);
            Batalha batalhaDoNoAtual = fabricaDeBatalha.criaBatalha(quantidadeDeInimigosPeloIndex(index));
            listaDeNos.add(noAtual);
            listaDeBatalhas.add(batalhaDoNoAtual);
            String[] filhosDoNo = scannerMapa.nextLine().split(" ");
            for (String filho : filhosDoNo) {
                noAtual.addFilho(Integer.parseInt(filho));
            }
            index++;
        }
        scannerMapa.close();
        NoMapa ultimoNo = new NoMapa(index);
        Batalha batalhaDoUltimoNo = fabricaDeBatalha.criaBatalha(quantidadeDeInimigosPeloIndex(index));
        listaDeNos.add(ultimoNo);
        listaDeBatalhas.add(batalhaDoUltimoNo);
    }

    private int quantidadeDeInimigosPeloIndex(int index) {
        return (index + 1) / 5;
    }

    public Batalha getBatalha(int idNo) {
        return listaDeBatalhas.get(idNo);
    }

    public boolean ehUltimoNo(int idNo) {
        return listaDeNos.get(idNo).getFilhos().size() == 0;
    }

    public ArrayList<Integer> getOpcoesDeCaminho(int idNo) {
        return listaDeNos.get(idNo).getFilhos();
    }

}
