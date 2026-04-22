package org.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class InimigoTest {

    private Batalha batalhaMock;
    private Heroi heroiMock;
    private FabricaDeEfeito fabricaMock;
    private Efeito efeitoMock;
    private Entidade alvoMock;
    private Inimigo inimigo;

    @BeforeEach
    public void setup() {
        batalhaMock = mock(Batalha.class);
        heroiMock = mock(Heroi.class);
        fabricaMock = mock(FabricaDeEfeito.class);
        efeitoMock = mock(Efeito.class);
        alvoMock = mock(Entidade.class);

        when(batalhaMock.getHeroi()).thenReturn(heroiMock);
        when(fabricaMock.criarEfeito()).thenReturn(efeitoMock);

        char[] acoes = {'A', 'C', 'E', 'U'};
        inimigo = new Inimigo("Orc", 50, 0, 10, 5, 8, fabricaMock, acoes, "O");
    }

    @Test
    public void testAtacar() {
        inimigo.atacar(alvoMock);
        verify(alvoMock, times(1)).receberDano(10);
        assertEquals(10, inimigo.getDano());
    }

    @Test
    public void testAcharAlvoValidoAtacar() {
        inimigo.setProximaAcao('A');
        Entidade alvo = inimigo.acharAlvoValido(batalhaMock);
        assertEquals(heroiMock, alvo);
    }

    @Test
    public void testAcharAlvoValidoCurarOuProteger() {
        inimigo.setProximaAcao('C');
        assertEquals(inimigo, inimigo.acharAlvoValido(batalhaMock));

        inimigo.setProximaAcao('E');
        assertEquals(inimigo, inimigo.acharAlvoValido(batalhaMock));
    }

    @Test
    public void testAcharAlvoValidoEfeitoDebuff() {
        inimigo.setProximaAcao('U');
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Debuff");
        
        Entidade alvo = inimigo.acharAlvoValido(batalhaMock);
        assertEquals(heroiMock, alvo);
    }

    @Test
    public void testAcharAlvoValidoEfeitoBuff() {
        inimigo.setProximaAcao('U');
        when(efeitoMock.getTipoDeEfeito()).thenReturn("Buff");
        
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        Inimigo aliadoMock = mock(Inimigo.class);
        inimigos.add(aliadoMock);
        when(batalhaMock.getInimigos()).thenReturn(inimigos);
        
        Entidade alvo = inimigo.acharAlvoValido(batalhaMock);
        assertEquals(aliadoMock, alvo);
    }

    @Test
    public void testAcharAlvoValidoDesconhecido() {
        inimigo.setProximaAcao('X');
        assertNull(inimigo.acharAlvoValido(batalhaMock));
    }

    @Test
    public void testAgirAcaoA() {
        inimigo.setProximaAcao('A');
        inimigo.setProximoAlvo(alvoMock);

        inimigo.agir(batalhaMock);

        verify(alvoMock, times(1)).receberDano(10);
        verify(batalhaMock, times(1)).adicionarAoHistorico('A', inimigo, alvoMock, 10);
    }

    @Test
    public void testAgirAcaoC() {
        inimigo.setProximaAcao('C');
        inimigo.setProximoAlvo(alvoMock);

        inimigo.agir(batalhaMock);

        verify(alvoMock, times(1)).curar(5);
        verify(batalhaMock, times(1)).adicionarAoHistorico('C', inimigo, alvoMock, 5);
    }

    @Test
    public void testAgirAcaoE() {
        inimigo.setProximaAcao('E');
        inimigo.setProximoAlvo(alvoMock);

        inimigo.agir(batalhaMock);

        verify(alvoMock, times(1)).receberEscudo(8);
        verify(batalhaMock, times(1)).adicionarAoHistorico('E', inimigo, alvoMock, 8);
    }

    @Test
    public void testAgirAcaoU() {
        inimigo.setProximaAcao('U');
        inimigo.setProximoAlvo(alvoMock);

        inimigo.agir(batalhaMock);

        verify(fabricaMock, times(1)).criarEfeito();
        verify(alvoMock, times(1)).aplicarEfeito(efeitoMock);
    }

    @Test
    public void testGetProxAcaoDescricoes() {
        inimigo.setProximaAcao('A');

    }
}