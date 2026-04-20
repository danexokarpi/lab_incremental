package org;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.jogo.Carta;
import org.jogo.PilhaDeDescarte;
import org.jogo.CartaDano;
import org.jogo.MaoDoJogador;


public class MaoDoJogadorTest {

    // ########################################################################
    // CLASSES FALSAS E AUXILIARES
    // ########################################################################

    static class PilhaDeDescarteFalsa extends PilhaDeDescarte {
        public ArrayList<Carta> cartasRecebidas = new ArrayList<>();

        public PilhaDeDescarteFalsa() {
            super();
        }

        @Override
        public void push(Carta carta) {
            cartasRecebidas.add(carta);
        }
    }

    private Carta criarCartaTeste(String nome) {
        return new CartaDano(nome, "Descrição genérica", 1, 2, "Unico");
    }

    // ------------------------------------------------------------------------
    // TESTES
    // ------------------------------------------------------------------------

    @Test
    public void testConstrutor_InicializaMaoVaziaComCapacidadeCorreta() {
        MaoDoJogador mao = new MaoDoJogador(5);

        assertEquals(5, mao.getCapacidade(), "A capacidade deve ser igual à definida no construtor.");
        assertEquals(0, mao.getTamanho(), "A mão deve começar com tamanho 0.");
        assertFalse(mao.estaCheia(), "A mão não deve começar cheia.");
    }

    @Test
    public void testAddCarta_AumentaTamanhoEAdicionaNaLista() {
        MaoDoJogador mao = new MaoDoJogador(3);
        Carta carta1 = criarCartaTeste("Carta 1");

        mao.addCarta(carta1);

        assertEquals(1, mao.getTamanho(), "O tamanho da mão deve aumentar para 1.");
        assertEquals(carta1, mao.getCarta(0), "A carta adicionada deve estar no índice 0.");
    }

    @Test
    public void testAddCarta_LancaExcecaoQuandoMaoEstaCheia() {
        MaoDoJogador mao = new MaoDoJogador(2); // Capacidade máxima de 2
        
        mao.addCarta(criarCartaTeste("Carta 1"));
        mao.addCarta(criarCartaTeste("Carta 2"));
        
        assertTrue(mao.estaCheia(), "A mão deve estar cheia agora.");
        Exception excecao = assertThrows(RuntimeException.class, () -> {
            mao.addCarta(criarCartaTeste("Carta 3"));
        });

        assertEquals("Mão já está cheia. Não é possível adicionar mais cartas.", excecao.getMessage());
    }

    @Test
    public void testRemoveCarta_ReduzTamanhoERemoveDaLista() {
        MaoDoJogador mao = new MaoDoJogador(5);
        Carta carta1 = criarCartaTeste("Carta 1");
        Carta carta2 = criarCartaTeste("Carta 2");
        
        mao.addCarta(carta1);
        mao.addCarta(carta2); 

        mao.removeCarta(0);

        assertEquals(1, mao.getTamanho(), "O tamanho da mão deve cair para 1 após remoção.");
        
        assertEquals(carta2, mao.getCarta(0), "A Carta 2 deve ter assumido a posição 0.");
    }

    @Test
    public void testDescartarTudo_EsvaziaAMaoEMandaParaAPilha() {
        MaoDoJogador mao = new MaoDoJogador(5);
        Carta carta1 = criarCartaTeste("Carta 1");
        Carta carta2 = criarCartaTeste("Carta 2");
        
        mao.addCarta(carta1);
        mao.addCarta(carta2);

        PilhaDeDescarteFalsa pilhaFalsa = new PilhaDeDescarteFalsa();

        mao.descartarTudo(pilhaFalsa);

        
        assertEquals(0, mao.getTamanho(), "O tamanho da mão deve ser 0 após descartar tudo.");
        

        assertThrows(IndexOutOfBoundsException.class, () -> {
            mao.getCarta(0);
        }, "A lista interna de cartas deve estar totalmente vazia.");

        assertEquals(2, pilhaFalsa.cartasRecebidas.size(), "A pilha de descarte deve ter recebido 2 cartas.");
        assertTrue(pilhaFalsa.cartasRecebidas.contains(carta1), "A pilha deve conter a Carta 1.");
        assertTrue(pilhaFalsa.cartasRecebidas.contains(carta2), "A pilha deve conter a Carta 2.");
    }
}