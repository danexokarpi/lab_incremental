package org;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.jogo.Heroi;
import org.jogo.FabricaDeEfeito;
import org.jogo.Batalha;
import org.jogo.Carta;
import org.jogo.Inimigo;

public class BatalhaTest {

    private Batalha criarBatalhaPadrao() {
        Heroi heroi = new Heroi("Herói Teste", 100, 0, "H");
        
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        inimigos.add(new Inimigo("Barata", 10, 0, 2, 0, 2, new FabricaDeEfeito(null, 0),new char[] {'A'}, "I"));
        inimigos.add(new Inimigo("Abobora", 10, 0, 2, 0, 2, new FabricaDeEfeito(null, 0),new char[] {'A'}, "I"));
        
        ArrayList<Carta> inventario = new ArrayList<>();


        int energiaMaxima = 3;
        int capacidadeMao = 5;

        return new Batalha(heroi, inimigos, inventario, energiaMaxima, capacidadeMao);
    }

    @Test
    public void testConstrutorInicializaCorretamente() {
        Batalha batalha = criarBatalhaPadrao();

        // Verifica se tudo foi guardado no lugar certo
        assertNotNull(batalha.getHeroi(), "O herói não pode ser nulo.");
        assertEquals(2, batalha.getInimigos().size(), "Devem haver 2 inimigos na batalha.");
        assertEquals(3, batalha.getEnergiaMaxima(), "A energia máxima deve ser 3.");
        
        // Verifica se as listas e pilhas foram instanciadas
        assertNotNull(batalha.getPilhaDeCompra(), "Pilha de compra deve ser criada.");
        assertNotNull(batalha.getPilhaDeDescarte(), "Pilha de descarte deve ser criada.");
        assertNotNull(batalha.getMaoDoJogador(), "Mão do jogador deve ser criada.");
    }

    @Test
    public void testTodosInimigosMortosRetornaFalseSeAlguemEstiverVivo() {
        Batalha batalha = criarBatalhaPadrao();
        
        boolean estaoMortos = batalha.todosInimigosMortos();

        assertFalse(estaoMortos, "Ainda há inimigos vivos, o método deve retornar false.");
    }

    @Test
    public void testTodosInimigosMortosRetornaTrueSeNinguemEstiverVivo() {
        Batalha batalha = criarBatalhaPadrao();
        
        for (Inimigo inimigo : batalha.getInimigos()) {
            inimigo.receberDano(1000); 
        }

        boolean estaoMortos = batalha.todosInimigosMortos();

        assertTrue(estaoMortos, "Todos os inimigos receberam dano letal, deve retornar true.");
    }
}