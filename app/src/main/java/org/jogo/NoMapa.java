package org.jogo;

import java.util.ArrayList;

/**
 * Representa um nó (posição) no mapa do jogo.
 * 
 * <p>Cada nó possui um identificador único e uma lista de nós filhos,
 * que representam os caminhos disponíveis a partir desta posição.
 * A estrutura de nós forma um grafo direcionado que define a progressão
 * do jogador pelo mapa.</p>
 * 
 * <p>Um nó sem filhos (lista vazia) é considerado um nó terminal,
 * representando o final do jogo ou um ponto sem saída.</p>
 */
public class NoMapa {

    private int id;
    private ArrayList<Integer> filhos;

    /**
     * Constrói um novo nó com o identificador especificado.
     * 
     * <p>Inicializa a lista de filhos como vazia, indicando que
     * inicialmente não há caminhos disponíveis a partir deste nó.</p>
     * 
     * @param id identificador único do nó
     */
    public NoMapa(int id) {
        this.id = id;
        this.filhos = new ArrayList<Integer>();
    }

    /**
     * Retorna o identificador único do nó.
     * 
     * @return identificador do nó
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retorna a lista de nós filhos acessíveis a partir deste nó.
     * 
     * <p>Uma lista vazia indica que este é um nó terminal.</p>
     * 
     * @return lista contendo os identificadores dos nós filhos
     */
    public ArrayList<Integer> getFilhos() {
        return this.filhos;
    }

    /**
     * Adiciona um novo nó filho à lista de caminhos disponíveis.
     * 
     * @param filho identificador do nó que pode ser acessado a partir deste
     */
    public void addFilho(int filho) {
        this.filhos.add(filho);
    }

}
