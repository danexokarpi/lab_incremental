package org.jogo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * Representa o mapa do jogo, contendo todos os nós (posições) e suas
 * respectivas batalhas.
 * 
 * <p>
 * Esta classe é responsável por carregar a estrutura do mapa a partir de um
 * arquivo
 * texto, onde cada linha define os filhos (conexões) de um nó. Cada nó no mapa
 * possui uma batalha associada, cuja dificuldade (número de inimigos) é
 * determinada
 * pela posição do nó no mapa.
 * </p>
 * 
 * <p>
 * A estrutura do mapa é carregada do arquivo "esqueletoDoMapa.txt", onde cada
 * linha
 * contém os identificadores dos nós filhos separados por espaços. A primeira
 * linha
 * corresponde ao nó 0, a segunda ao nó 1, e assim sucessivamente.
 * </p>
 */
public class Mapa {

    private ArrayList<NoMapa> listaDeNos;
    private ArrayList<Evento> listaDeEventos;

    /**
     * Constrói um novo mapa a partir de um arquivo de definição.
     * 
     * <p>
     * Lê o arquivo "esqueletoDoMapa.txt" linha por linha, criando um nó
     * e uma batalha para cada linha. As conexões entre nós são definidas
     * pelos números presentes em cada linha. Um nó final é aquele que não
     * possui filhos.
     * </p>
     * 
     * @param fabricaDeEvento fábrica utilizada para criar as batalhas de cada nó
     */
    public Mapa(FabricaDeEvento fabricaDeEvento) {
        listaDeNos = new ArrayList<NoMapa>();
        listaDeEventos = new ArrayList<Evento>();

        try (Scanner scannerMapa = new Scanner(new File("esqueletoDoMapa.txt"))) {
            int index = 0;
            while (scannerMapa.hasNextLine()) {
                NoMapa noAtual = new NoMapa(index);
                    //DEBBUGING PESADO LEMBRAR DE APAGAR LEMBRAR
                Evento batalhaDoNoAtual = fabricaDeEvento.criaBatalha(quantidadeDeInimigosPeloIndex(index));
                Evento loja = fabricaDeEvento.criaLoja();
                Evento escolha = fabricaDeEvento.criaEscolha();
                listaDeNos.add(noAtual);
                listaDeEventos.add(batalhaDoNoAtual);
                String[] filhosDoNo = scannerMapa.nextLine().split(" ");
                for (String filho : filhosDoNo) {
                    noAtual.addFilho(Integer.parseInt(filho));
                }
                index++;
            }
            scannerMapa.close();
            NoMapa ultimoNo = new NoMapa(index);
            Evento batalhaDoUltimoNo = fabricaDeEvento.criaBatalha(quantidadeDeInimigosPeloIndex(index));
            listaDeNos.add(ultimoNo);
            listaDeEventos.add(batalhaDoUltimoNo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Calcula a quantidade de inimigos para um nó baseado em seu índice.
     * 
     * <p>
     * A fórmula utilizada é: ((índice + 1) / 5) + 1, garantindo que
     * a dificuldade aumente progressivamente conforme o jogador avança
     * no mapa.
     * </p>
     * 
     * @param index índice do nó no mapa
     * @return número de inimigos que devem estar presentes na batalha do nó
     */
    private int quantidadeDeInimigosPeloIndex(int index) {
        return ((index + 1) / 5) + 1;
    }

    /**
     * Obtém a batalha associada a um nó específico do mapa.
     * 
     * @param idNo identificador do nó (posição no mapa)
     * @return batalha correspondente ao nó informado
     */
    public Evento getEvento(int idNo) {
        return listaDeEventos.get(idNo);
    }

    /**
     * Verifica se um determinado nó é o último do mapa.
     * 
     * <p>
     * Um nó é considerado o último quando não possui filhos,
     * ou seja, quando não há caminhos disponíveis a partir dele.
     * </p>
     * 
     * @param idNo identificador do nó a ser verificado
     * @return true se o nó não possui filhos, false caso contrário
     */
    public boolean ehUltimoNo(int idNo) {
        return listaDeNos.get(idNo).getFilhos().size() == 0;
    }

    /**
     * Retorna todas as opções de caminho disponíveis a partir de um nó.
     * 
     * @param idNo identificador do nó atual
     * @return lista contendo os identificadores dos nós filhos (próximas posições)
     */
    public ArrayList<Integer> getOpcoesDeCaminho(int idNo) {
        return listaDeNos.get(idNo).getFilhos();
    }

}
