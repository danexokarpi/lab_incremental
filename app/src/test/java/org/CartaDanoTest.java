package org;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.jogo.Inimigo;
import org.jogo.FabricaDeEfeito;
import org.jogo.Heroi;
import org.jogo.CartaDano;
import org.jogo.Entidade;
import org.jogo.Batalha;

public class CartaDanoTest {

    //######################################################################
    //CLASSES FALSAS PARA ISOLAR O TESTE
    //######################################################################

    static class InimigoFalso extends Inimigo {
        public int danoRecebido = 0;

        public InimigoFalso() {
            super("Alvo", 100, 0, 0, 0, 0, new FabricaDeEfeito(null, 0), new char[]{'A'}, "S");
        }

        @Override
        public void receberDano(int quantidade) {
            this.danoRecebido += quantidade;
        }
    }

    static class BatalhaFalsa extends Batalha {
        public InimigoFalso inimigoParaEscolher;
        public ArrayList<Inimigo> listaInimigos = new ArrayList<>();
        public boolean historicoChamado = false;

        public BatalhaFalsa() {
            super(new Heroi("H", 10, 0, "S"), new ArrayList<>(), new ArrayList<>(), 3, 5);
        }

        @Override
        public Inimigo escolherUmInimigo() {
            return inimigoParaEscolher;
        }

        @Override
        public ArrayList<Inimigo> getInimigos() {
            return listaInimigos;
        }

        @Override
        public void adicionarAoHistorico(char acao, Entidade agente, Entidade alvo, int valor) {
            this.historicoChamado = true;
        }
    }
    //######################################################################
    //TESTES DA CARTA DANO
    //######################################################################

    @Test
    public void testConstrutorEGetters() {
        CartaDano carta = new CartaDano("Tiro", "Bang", 2, 10, "Unico");
        
        assertEquals(10, carta.getDano());
        assertEquals("Tiro", carta.getNome());
        assertEquals("Bang", carta.getDescricao());
        assertEquals(2, carta.getCusto());
        assertEquals("Unico", carta.getAreaDeEfeito());
    }

    @Test
    public void testGetEfeitoCustoAoE() {
        CartaDano carta = new CartaDano("Tiro", "Bang", 2, 10, "Unico");
        String esperado = "(Dano - 10) (Custo - 2) (Alvo - Unico)";
        
        assertEquals(esperado, carta.getEfeitoCustoAoE());
    }

    @Test
    public void testUsar_AreaUnicoComInimigoSelecionado() {
        CartaDano carta = new CartaDano("Tiro", "Bang", 2, 15, "Unico");
        BatalhaFalsa batalha = new BatalhaFalsa();
        InimigoFalso inimigo = new InimigoFalso();
        batalha.inimigoParaEscolher = inimigo;

        boolean resultado = carta.usar(batalha);

        assertTrue(resultado);
        assertEquals(15, inimigo.danoRecebido);
        assertTrue(batalha.historicoChamado);
    }

    @Test
    public void testUsar_AreaUnicoSemInimigoSelecionado() {
        CartaDano carta = new CartaDano("Tiro", "Bang", 2, 15, "Unico");
        BatalhaFalsa batalha = new BatalhaFalsa();
        batalha.inimigoParaEscolher = null;

        boolean resultado = carta.usar(batalha);

        assertFalse(resultado);
        assertFalse(batalha.historicoChamado);
    }

    @Test
    public void testUsar_AreaTodos() {
        CartaDano carta = new CartaDano("Granada", "Boom", 3, 20, "Todos");
        BatalhaFalsa batalha = new BatalhaFalsa();
        InimigoFalso inimigo1 = new InimigoFalso();
        InimigoFalso inimigo2 = new InimigoFalso();
        batalha.listaInimigos.add(inimigo1);
        batalha.listaInimigos.add(inimigo2);

        boolean resultado = carta.usar(batalha);

        assertTrue(resultado);
        assertEquals(20, inimigo1.danoRecebido);
        assertEquals(20, inimigo2.danoRecebido);
        assertTrue(batalha.historicoChamado);
    }

    @Test
    public void testUsar_AreaInvalida() {
        CartaDano carta = new CartaDano("Bug", "Erro", 1, 5, "Nenhum");
        BatalhaFalsa batalha = new BatalhaFalsa();

        boolean resultado = carta.usar(batalha);

        assertFalse(resultado);
    }
}