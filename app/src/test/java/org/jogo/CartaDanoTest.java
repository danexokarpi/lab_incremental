package org.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class CartaDanoTest {

    private Batalha batalhaMock;
    private Heroi heroiMock;

    @BeforeEach
    public void setup() {
        batalhaMock = mock(Batalha.class);
        heroiMock = mock(Heroi.class);
        
        // Sempre que o tabuleiro pedir o herói para o historico, devolve o mock
        when(batalhaMock.getHeroi()).thenReturn(heroiMock);
    }

    @Test
    public void testConstrutorEGetters() {
        CartaDano carta = new CartaDano("Ataque Básico", "Causa dano", 1, 10, "Unico");

        assertEquals("Ataque Básico", carta.getNome());
        assertEquals("Causa dano", carta.getDescricao());
        assertEquals(1, carta.getCusto());
        assertEquals(10, carta.getDano());
        assertEquals("Unico", carta.getAreaDeEfeito());
    }

    @Test
    public void testGetEfeitoCustoAoE() {
        CartaDano carta = new CartaDano("Corte", "Causa dano com espada", 2, 15, "Unico");
        
        String esperado = "(Dano - 15) (Custo - 2) (Alvo - Unico)";
        assertEquals(esperado, carta.getEfeitoCustoAoE());
    }

    @Test
    public void testUsarCartaAreaUnicoComSucesso() {
        CartaDano carta = new CartaDano("Ataque", "Dano", 1, 10, "Unico");
        Inimigo inimigoMock = mock(Inimigo.class);

        // Jogador escolhe um inimigo válido
        when(batalhaMock.escolherUmInimigo()).thenReturn(inimigoMock);

        boolean usou = carta.usar(batalhaMock);

        assertTrue(usou, "A carta deveria retornar true ao ser usada com sucesso.");
        
        verify(inimigoMock, times(1)).receberDano(10);
        
        verify(batalhaMock, times(1)).adicionarAoHistorico('A', heroiMock, inimigoMock, 10);
    }

    @Test
    public void testUsarCartaAreaUnicoCanceladaPeloJogador() {
        CartaDano carta = new CartaDano("Ataque", "Dano", 1, 10, "Unico");

        // Jogador apertou em cancelar
        when(batalhaMock.escolherUmInimigo()).thenReturn(null);

        boolean usou = carta.usar(batalhaMock);

        assertFalse(usou, "A carta deveria retornar false pois o uso foi cancelado.");
        
        // Garante que não tentou registrar no histórico
        verify(batalhaMock, never()).adicionarAoHistorico(anyChar(), any(), any(), anyInt());
    }

    @Test
    public void testUsarCartaAreaTodos() {
        CartaDano carta = new CartaDano("Terremoto", "Dano em área", 3, 20, "Todos");
        
        // Lista com 2 inimigos mockados
        Inimigo inimigoMock1 = mock(Inimigo.class);
        Inimigo inimigoMock2 = mock(Inimigo.class);
        ArrayList<Inimigo> listaInimigos = new ArrayList<>();
        listaInimigos.add(inimigoMock1);
        listaInimigos.add(inimigoMock2);

        //Tabuleiro retorna a lista Mockada
        when(batalhaMock.getInimigos()).thenReturn(listaInimigos);

        boolean usou = carta.usar(batalhaMock);

        assertTrue(usou, "A carta AoE deveria retornar true ao ser usada.");

        // Verifica o dano recebido
        verify(inimigoMock1, times(1)).receberDano(20);
        verify(inimigoMock2, times(1)).receberDano(20);

        // Verifica se gerou o histórico para ambos
        verify(batalhaMock, times(1)).adicionarAoHistorico('A', heroiMock, inimigoMock1, 20);
        verify(batalhaMock, times(1)).adicionarAoHistorico('A', heroiMock, inimigoMock2, 20);
        
        // Garante que o método de escolher UM inimigo nunca foi chamado
        verify(batalhaMock, never()).escolherUmInimigo();
    }

    @Test
    public void testUsarCartaAreaDeEfeitoInvalida() {
        // Criamos uma carta com uma área de efeito errada
        CartaDano carta = new CartaDano("Bug", "Dano falho", 1, 10, "Aleatorio");

        boolean usou = carta.usar(batalhaMock);

        assertFalse(usou, "A carta deveria retornar false se a área de efeito for desconhecida.");
        
        // Garante que não chamou os métodos de alvo
        verify(batalhaMock, never()).escolherUmInimigo();
        verify(batalhaMock, never()).getInimigos();
    }
}