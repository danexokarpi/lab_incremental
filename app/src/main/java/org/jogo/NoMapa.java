package org.jogo;

import java.util.ArrayList;

public class NoMapa {

    private int id;
    private ArrayList<Integer> filhos;

    public NoMapa(int id) {
        this.id = id;
        this.filhos = new ArrayList<Integer>();
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Integer> getFilhos() {
        return this.filhos;
    }

    public void addFilho(int filho) {
        this.filhos.add(filho);
    }

}
