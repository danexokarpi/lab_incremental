package org.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class GameManagerTest {

    private GameManager gameManager;
    private Menu menuMock;
    private Mapa mapaMock;
    private GerenciadorDeJson gerenciadorMock;

    @BeforeEach
    public void setup() throws Exception {
        gameManager = new GameManager();
        menuMock = mock(Menu.class);
        mapaMock = mock(Mapa.class);
        gerenciadorMock = mock(GerenciadorDeJson.class);

        setPrivateField(gameManager, "menu", menuMock);
        setPrivateField(null, "mapa", mapaMock);
        setPrivateField(null, "gerenciador", gerenciadorMock);
        setPrivateField(null, "posicaoNoMapa", 0);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = GameManager.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testCriaHeroi() throws Exception {
        Method metodo = GameManager.class.getDeclaredMethod("criaHeroi");
        metodo.setAccessible(true);

        Heroi heroiGerado = (Heroi) metodo.invoke(null);

        assertNotNull(heroiGerado);
        assertEquals("Capitão Cabra", heroiGerado.getNome());
        assertEquals(15, heroiGerado.getVida());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCriaCartasDoJogo() throws Exception {
        Method metodo = GameManager.class.getDeclaredMethod("criaCartasDoJogo");
        metodo.setAccessible(true);

        ArrayList<Carta> cartas = (ArrayList<Carta>) metodo.invoke(null);

        assertNotNull(cartas);
        assertEquals(11, cartas.size(), "O baralho inicial deve conter exatamente 11 cartas.");
    }

    @Test
    public void testEscolhaDeProximaPosicaoMoverParaBaixoEConfirmar() throws Exception {
        ArrayList<Integer> caminhos = new ArrayList<>(Arrays.asList(1, 2));
        when(mapaMock.getOpcoesDeCaminho(0)).thenReturn(caminhos);

        when(menuMock.receberInputTeclado())
            .thenReturn(new KeyStroke(KeyType.ArrowDown))
            .thenReturn(new KeyStroke(KeyType.Enter));

        Method metodo = GameManager.class.getDeclaredMethod("escolhaDeProximaPosicao");
        metodo.setAccessible(true);

        int posicaoEscolhida = (int) metodo.invoke(gameManager);

        assertEquals(2, posicaoEscolhida);
        verify(menuMock, atLeastOnce()).desenharMapa();
    }

    @Test
    public void testEscolhaDeProximaPosicaoMoverParaCimaNoLimite() throws Exception {
        ArrayList<Integer> caminhos = new ArrayList<>(Arrays.asList(5, 6, 7));
        when(mapaMock.getOpcoesDeCaminho(0)).thenReturn(caminhos);

        when(menuMock.receberInputTeclado())
            .thenReturn(new KeyStroke(KeyType.ArrowUp))
            .thenReturn(new KeyStroke(KeyType.Enter));

        Method metodo = GameManager.class.getDeclaredMethod("escolhaDeProximaPosicao");
        metodo.setAccessible(true);

        int posicaoEscolhida = (int) metodo.invoke(gameManager);

    
        assertEquals(7, posicaoEscolhida);
    }
    
    @Test
    public void testEscolhaDeProximaPosicaoMoverParaBaixoNoLimite() throws Exception {
        ArrayList<Integer> caminhos = new ArrayList<>(Arrays.asList(5, 6));
        when(mapaMock.getOpcoesDeCaminho(0)).thenReturn(caminhos);

        when(menuMock.receberInputTeclado())
            .thenReturn(new KeyStroke(KeyType.ArrowDown))
            .thenReturn(new KeyStroke(KeyType.ArrowDown))
            .thenReturn(new KeyStroke(KeyType.Enter));

        Method metodo = GameManager.class.getDeclaredMethod("escolhaDeProximaPosicao");
        metodo.setAccessible(true);

        int posicaoEscolhida = (int) metodo.invoke(gameManager);


        assertEquals(5, posicaoEscolhida);
    }
}