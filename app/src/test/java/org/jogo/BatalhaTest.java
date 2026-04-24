package org.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;

public class BatalhaTest {

    private Menu menuMock;

    // Esse método garante que antes de cada @Test, teremos um menu limpinho
    @BeforeEach
    public void setup() {
        menuMock = mock(Menu.class);
    }

    private Batalha criarBatalhaPadrao() {
        Heroi heroi = new Heroi("Herói Teste", 100, 0, "H");

        ArrayList<Inimigo> inimigos = new ArrayList<>();
        inimigos.add(new Inimigo("Barata", 10, 0, 2, 0, 2, new FabricaDeEfeito(null, 0), new char[] { 'A' }, "I"));
        inimigos.add(new Inimigo("Abobora", 10, 0, 2, 0, 2, new FabricaDeEfeito(null, 0), new char[] { 'A' }, "I"));

        ArrayList<Carta> inventario = new ArrayList<>();

        int energiaMaxima = 3;
        int capacidadeMao = 5;

        return new Batalha(heroi, inimigos, inventario, energiaMaxima, capacidadeMao, menuMock);
    }

    @Test
    public void testConstrutorInicializaCorretamente() {
        Batalha batalha = criarBatalhaPadrao();

        assertNotNull(batalha.getHeroi(), "O herói não pode ser nulo.");
        assertEquals(2, batalha.getInimigos().size(), "Devem haver 2 inimigos na batalha.");
        assertEquals(3, batalha.getEnergiaMaxima(), "A energia máxima deve ser 3.");
        assertNotNull(batalha.getPilhaDeCompra(), "Pilha de compra deve ser criada.");
        assertNotNull(batalha.getPilhaDeDescarte(), "Pilha de descarte deve ser criada.");
        assertNotNull(batalha.getMaoDoJogador(), "Mão do jogador deve ser criada.");
    }

    @Test
    public void testTodosInimigosMortosRetornaFalseSeAlguemEstiverVivo() {
        Batalha batalha = criarBatalhaPadrao();
        assertFalse(batalha.todosInimigosMortos(), "Ainda há inimigos vivos, o método deve retornar false.");
    }

    @Test
    public void testTodosInimigosMortosRetornaTrueSeNinguemEstiverVivo() {
        Batalha batalha = criarBatalhaPadrao();
        for (Inimigo inimigo : batalha.getInimigos()) {
            inimigo.receberDano(1000);
        }
        assertTrue(batalha.todosInimigosMortos(), "Todos os inimigos receberam dano letal, deve retornar true.");
    }

    // --- TESTES COM MOCKITO ---

    @Test
    public void testEscolherUmInimigoValido() {
        Batalha batalha = criarBatalhaPadrao();

        when(menuMock.receberInputTeclado()).thenReturn(new KeyStroke(KeyType.Enter));

        Inimigo escolhido = batalha.escolherUmInimigo();

        assertNotNull(escolhido);
        assertEquals("Barata", escolhido.getNome());
    }

    @Test
    public void testEscolherUmInimigoCancelarMovendoParaEsquerda() {
        Batalha batalha = criarBatalhaPadrao();

        when(menuMock.receberInputTeclado())
                .thenReturn(new KeyStroke(KeyType.ArrowLeft))
                .thenReturn(new KeyStroke(KeyType.Enter));

        Inimigo escolhido = batalha.escolherUmInimigo();

        assertNull(escolhido, "A escolha deve ser nula pois o jogador cancelou a ação.");
    }

    @Test
    public void testEscolherUmInimigoMortoExibeAviso() {
        Batalha batalha = criarBatalhaPadrao();

        batalha.getInimigos().get(0).receberDano(1000);

        when(menuMock.receberInputTeclado())
                .thenReturn(new KeyStroke(KeyType.Enter))
                .thenReturn(new KeyStroke(KeyType.ArrowLeft))
                .thenReturn(new KeyStroke(KeyType.Enter));

        batalha.escolherUmInimigo();

        verify(menuMock).desenharAviso("inimigoEstaMorto");
    }

    @Test
    public void testNovoRoundEncerrarTurnoDireto() {
        Batalha batalha = criarBatalhaPadrao();

        when(menuMock.receberInputTeclado()).thenReturn(new KeyStroke(KeyType.Enter));

        batalha.novoRound();

        verify(menuMock, atLeastOnce()).desenharStatus(batalha);
    }

    @Test
    public void testNovaBatalhaVitoria() {
        Batalha batalha = criarBatalhaPadrao();

        for (Inimigo inimigo : batalha.getInimigos()) {
            inimigo.receberDano(1000);
        }

        when(menuMock.receberInputTeclado()).thenReturn(new KeyStroke(KeyType.Enter));

        boolean venceu = batalha.iniciar();

        assertTrue(venceu, "Deveria retornar true indicando vitória");
        verify(menuMock).desenharMensagemFinalBatalha(contains("VITÓRIA"));
    }
}
