package org.jogo;

import java.util.ArrayList;
/**
 * Representa o herói controlado pelo jogador no jogo.
 * 
 * Esta classe herda de {@link Entidade}, portanto possui vida, escudo e efeitos
 * aplicáveis. O herói é a entidade principal que o jogador controla durante a
 * batalha.
 */
public class Heroi extends Entidade {
    private int ouro;
    private int energiaMaxima;
    private ArrayList<Carta> inventario = new ArrayList<Carta>();
    /**
     * Construtor para criar um herói com atributos iniciais.
     *
     * @param nome       o nome do herói
     * @param vidaMaxima a quantidade máxima de vida que o herói pode ter
     * @param escudo     a quantidade inicial de escudo do herói
     */
    public Heroi(String nome, int vidaMaxima, int escudo,int ouro, int energiaMaxima, String ascci) {
        super(nome, vidaMaxima, escudo, ascci);
    }

    public void setarInventário(ArrayList<Carta> inventario){
        this.inventario = inventario;
    }
    public void setarOuro(int ouro){
        this.ouro = ouro;
    }
    public void setarEnegiaMaxima(int energiaMaxima){
        this.energiaMaxima = energiaMaxima;
    }
    public ArrayList<Carta> getInventario(){
        return inventario;
    }
    public int getOuro(){
        return this.ouro;
    }
    public int getEnegiaMaxima(){
        return this.energiaMaxima;
    }
    public void alterarOuro(int i){
        this.ouro += i;
    }
    public void alterarEnergiaMaxima(int i){
        this.energiaMaxima += i;
    }
    public void removerCartaDoInventario(int index){
        inventario.remove(index);
    }
    
}
