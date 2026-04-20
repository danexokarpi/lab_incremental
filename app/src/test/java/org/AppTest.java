package org; 

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jogo.Carta;
import org.jogo.App;

//Método de teste que usa o reflection do java para análisar métodos privados
public class AppTest {

    @Test
    public void testCriaBaralho_RetornaTodasAsCartas() throws Exception {
        Method metodo = App.class.getDeclaredMethod("criaBaralho");
        metodo.setAccessible(true);
       
        @SuppressWarnings("unchecked")
        ArrayList<Carta> baralho = (ArrayList<Carta>) metodo.invoke(null);
        
        assertNotNull(baralho);
        assertEquals(11, baralho.size());
        
       
        assertEquals("Revolver", baralho.get(0).getNome());
        assertEquals("Gás Mostarda", baralho.get(10).getNome());
    }
}