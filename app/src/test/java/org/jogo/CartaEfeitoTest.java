package org.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class CartaEfeitoTest {

    private Batalha batalhaMock;
    private Heroi heroiMock;
    private FabricaDeEfeito fabricaMock;
    private Efeito efeitoMock;

    @BeforeEach
    public void setup() {
        batalhaMock = mock(Batalha.class);
        heroiMock = mock(Heroi.class);
        fabricaMock = mock(FabricaDeEfeito.class);
        efeitoMock = mock(Efeito.class);

        when(batalhaMock.getHeroi()).thenReturn(heroiMock);
        when(fabricaMock.criarEfeito()).thenReturn(efeitoMock);
    }

    @Test
    public void testGetEfeitoCustoAoE() {
        when(efeitoMock.getNome()).thenReturn("Veneno");
        when(efeitoMock.getAcumulos()).thenReturn(3);

        CartaEfeito carta = new CartaEfeito("Pote de Veneno", "Causa veneno", 2, fabricaMock, "Unico", 0);
        String esperado = "(Causa - Veneno por 3 turnos) (Custo - 2) (Alvo - Unico)";
        
        assertEquals(esperado, carta.getEfeitoCustoAoE());
    }

    @Test
    public void testUsarBuffAplicadoAoHeroi() {
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Buff");
        CartaEfeito carta = new CartaEfeito("Poção de Força", "Aumenta dano", 1, fabricaMock, "Unico", 0);

        assertTrue(carta.usar(batalhaMock));
        verify(heroiMock, times(1)).aplicarEfeito(efeitoMock);
    }

    @Test
    public void testUsarDebuffEmTodosOsInimigos() {
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Debuff");
        CartaEfeito carta = new CartaEfeito("Névoa Tóxica", "Envenena todos", 3, fabricaMock, "Todos", 0);

        Inimigo inimigo1 = mock(Inimigo.class);
        Inimigo inimigo2 = mock(Inimigo.class);
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        inimigos.add(inimigo1);
        inimigos.add(inimigo2);

        when(batalhaMock.getInimigos()).thenReturn(inimigos);

        assertTrue(carta.usar(batalhaMock));
        verify(inimigo1, times(1)).aplicarEfeito(efeitoMock);
        verify(inimigo2, times(1)).aplicarEfeito(efeitoMock);
    }

    @Test
    public void testUsarDebuffUnicoComSucesso() {
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Debuff");
        CartaEfeito carta = new CartaEfeito("Dardo Fraco", "Reduz ataque", 1, fabricaMock, "Unico", 0);
        Inimigo inimigoMock = mock(Inimigo.class);

        when(batalhaMock.escolherUmInimigo()).thenReturn(inimigoMock);

        assertTrue(carta.usar(batalhaMock));
        verify(inimigoMock, times(1)).aplicarEfeito(efeitoMock);
    }

    @Test
    public void testUsarDebuffUnicoCancelado() {
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Debuff");
        CartaEfeito carta = new CartaEfeito("Dardo Fraco", "Reduz ataque", 1, fabricaMock, "Unico", 0);

        when(batalhaMock.escolherUmInimigo()).thenReturn(null);

        assertFalse(carta.usar(batalhaMock));
        verify(batalhaMock, never()).getInimigos();
    }

    @Test
    public void testUsarDebuffAreaDesconhecida() {
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Debuff");
        CartaEfeito carta = new CartaEfeito("Erro", "Falha", 1, fabricaMock, "Aleatoria", 0);

        assertFalse(carta.usar(batalhaMock));
    }

    @Test
    public void testUsarTipoEfeitoDesconhecido() {
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Neutro");
        CartaEfeito carta = new CartaEfeito("Nada", "Sem efeito", 0, fabricaMock, "Unico", 0);

        assertFalse(carta.usar(batalhaMock));
    }
}