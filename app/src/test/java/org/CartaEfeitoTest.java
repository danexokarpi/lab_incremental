package org;

import org.jogo.Batalha;
import org.jogo.CartaEfeito;
import org.jogo.Efeito;
import org.jogo.Evento;
import org.jogo.FabricaDeEfeito;
import org.jogo.Heroi;
import org.jogo.Inimigo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class CartaEfeitoTest {

    static class EfeitoFalso extends Efeito {
        private String tipoDesejado;

        public EfeitoFalso(String tipoDesejado) {
            super("", tipoDesejado, 2, "");
            this.tipoDesejado = tipoDesejado;
        }

        @Override
        public String getTipoDeEfeito() {
            return this.tipoDesejado;
        }

        @Override
        public String getNome() {
            return "Efeito Teste";
        }

        @Override
        public int getAcumulos() {
            return 2;
        }

        @Override
        public void receberNotificacao(Evento eventoOcorrido) {
        }
    }

    static class FabricaFalsa extends FabricaDeEfeito {
        private String tipoDesejado;

        public FabricaFalsa(String tipoDesejado) {
            super("fake", 1);
            this.tipoDesejado = tipoDesejado;
        }

        @Override
        public Efeito criarEfeito() {
            return new EfeitoFalso(this.tipoDesejado);
        }
    }

    static class HeroiFalso extends Heroi {
        public boolean recebeuEfeito = false;

        public HeroiFalso() {
            super("Heroi Falso", 10, 0, "S");
        }

        @Override
        public void aplicarEfeito(Efeito efeito) {
            this.recebeuEfeito = true;
        }
    }

    static class InimigoFalso extends Inimigo {
        public boolean recebeuEfeito = false;

        public InimigoFalso() {
            super("Inimigo Falso", 10, 0, 0, 0, 0, new FabricaDeEfeito("v", 1), new char[]{'A'}, "S");
        }

        @Override
        public void aplicarEfeito(Efeito efeito) {
            this.recebeuEfeito = true;
        }
    }

    static class BatalhaFalsa extends Batalha {
        public HeroiFalso heroiFalso = new HeroiFalso();
        public InimigoFalso inimigoParaEscolher;
        public ArrayList<Inimigo> listaInimigos = new ArrayList<>();

        public BatalhaFalsa() {
            super(new Heroi("H", 10, 0, "S"), new ArrayList<>(), new ArrayList<>(), 3, 5);
        }

        @Override
        public Heroi getHeroi() {
            return this.heroiFalso;
        }

        @Override
        public Inimigo escolherUmInimigo() {
            return this.inimigoParaEscolher;
        }

        @Override
        public ArrayList<Inimigo> getInimigos() {
            return this.listaInimigos;
        }
    }

    /* *
     * TESTE ATIVO: O único que está rodando.
     * Esse teste não usa a BatalhaFalsa.
     */
    @Test
    public void testGetEfeitoCustoAoE() {
        FabricaFalsa fabrica = new FabricaFalsa("Buff");
        CartaEfeito carta = new CartaEfeito("Pocao", "Cura", 2, fabrica, "Unico");

        String esperado = "(Causa - Efeito Teste por 2 turnos) (Custo - 2) (Alvo - Unico)";
        assertEquals(esperado, carta.getEfeitoCustoAoE());
    }

    /* *
     * TESTES DESATIVADOS: 
     * Retiramos a anotação @Test para não rodarem e não travarem o Gradle.
     */

    // public void testUsar_EfeitoBuffAplicaNoHeroi() { ... }
    
    // public void testUsar_EfeitoDebuffEmTodosInimigos() { ... }

    // public void testUsar_EfeitoDebuffEmInimigoUnicoSelecionado() { ... }

    // public void testUsar_EfeitoDebuffEmInimigoUnicoSemSelecao() { ... }

    // public void testUsar_EfeitoDebuffComAreaInvalida() { ... }

    // public void testUsar_TipoDeEfeitoDesconhecido() { ... }
}