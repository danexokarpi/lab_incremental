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
        
        when(batalhaMock.getHeroi()).thenReturn(heroiMock);
    }

    @Test
    public void testConstrutorEGetters() {
        CartaDano carta = new CartaDano("Ataque Básico", "Causa dano", 1, 10, "Unico", 0);

        assertEquals("Ataque Básico", carta.getNome());
        assertEquals("Causa dano", carta.getDescricao());
        assertEquals(1, carta.getCusto());
        assertEquals(10, carta.getDano());
        assertEquals("Unico", carta.getAreaDeEfeito());
    }

    @Test
    public void testGetEfeitoCustoAoE() {
        CartaDano carta = new CartaDano("Corte", "Causa dano com espada", 2, 15, "Unico", 0);
        
        String esperado = "(Dano - 15) (Custo - 2) (Alvo - Unico)";
        assertEquals(esperado, carta.getEfeitoCustoAoE());
    }

    @Test
    public void testUsarCartaAreaUnicoComSucesso() {
        CartaDano carta = new CartaDano("Ataque", "Dano", 1, 10, "Unico", 0);
        Inimigo inimigoMock = mock(Inimigo.class);

        when(batalhaMock.escolherUmInimigo()).thenReturn(inimigoMock);

        boolean usou = carta.usar(batalhaMock);

        assertTrue(usou);
        
        verify(inimigoMock, times(1)).receberDano(10);
        
        verify(batalhaMock, times(1)).adicionarAoHistorico('A', heroiMock, inimigoMock, 10);
    }

    @Test
    public void testUsarCartaAreaUnicoCanceladaPeloJogador() {
        CartaDano carta = new CartaDano("Ataque", "Dano", 1, 10, "Unico", 0);

        when(batalhaMock.escolherUmInimigo()).thenReturn(null);

        boolean usou = carta.usar(batalhaMock);

        assertFalse(usou);
        
        verify(batalhaMock, never()).adicionarAoHistorico(anyChar(), any(), any(), anyInt());
    }

    @Test
    public void testUsarCartaAreaTodos() {
        CartaDano carta = new CartaDano("Terremoto", "Dano em área", 3, 20, "Todos", 0);
        
        Inimigo inimigoMock1 = mock(Inimigo.class);
        Inimigo inimigoMock2 = mock(Inimigo.class);
        ArrayList<Inimigo> listaInimigos = new ArrayList<>();
        listaInimigos.add(inimigoMock1);
        listaInimigos.add(inimigoMock2);

        when(batalhaMock.getInimigos()).thenReturn(listaInimigos);

        boolean usou = carta.usar(batalhaMock);

        assertTrue(usou);

        verify(inimigoMock1, times(1)).receberDano(20);
        verify(inimigoMock2, times(1)).receberDano(20);

        verify(batalhaMock, times(1)).adicionarAoHistorico('A', heroiMock, inimigoMock1, 20);
        verify(batalhaMock, times(1)).adicionarAoHistorico('A', heroiMock, inimigoMock2, 20);
        
        verify(batalhaMock, never()).escolherUmInimigo();
    }

    @Test
    public void testUsarCartaAreaDeEfeitoInvalida() {
        CartaDano carta = new CartaDano("Bug", "Dano falho", 1, 10, "Aleatorio", 0);

        boolean usou = carta.usar(batalhaMock);

        assertFalse(usou);
        
        verify(batalhaMock, never()).escolherUmInimigo();
        verify(batalhaMock, never()).getInimigos();
    }
}