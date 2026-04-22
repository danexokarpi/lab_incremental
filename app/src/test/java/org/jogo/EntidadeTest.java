package org.jogo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class EntidadeTest {
    //Como a Entidade é abstrata precisamos criar uma dummy para testar
    static class EntidadeTeste extends Entidade {
        public EntidadeTeste(String nome, int vidaMaxima, int escudo, String ascii){
            super(nome, vidaMaxima, escudo, ascii);
        }
        public EntidadeTeste(){
            super();
        }
    }
    
    
    @Test
    public void testeConstrutorInicializaValoresCorretamente(){
        EntidadeTeste entidade = new EntidadeTeste("Goblin", 30, 5, "O");
        assertEquals("Goblin", entidade.getNome());
        assertEquals(30, entidade.getVidaMaxima());
        assertEquals(30, entidade.getVida(), "A vida inicial deve ser igual à vida máxima.");
        assertEquals(5, entidade.getEscudo());
        assertEquals("O", entidade.getAscii());
        assertNotNull(entidade.getEfeitos(), "A lista de efeitos não deve ser nula.");
        assertTrue(entidade.estaVivo());
    }
    @Test
    public void testeGetEfeitosInicializacaoTardia(){
        // Vamos criar uma entidade com contrutor vaziu para saber se a lista de efeitos ainda é inicializada
        EntidadeTeste entidade = new EntidadeTeste();
        assertNotNull(entidade.getEfeitos(), "O getter tem que instanciar a lista se ela for null");
        assertTrue(entidade.getEfeitos().isEmpty());
    }
    @Test
    public void testeReceberDanoMenorQueEscudo() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 20, "X");
        
        entidade.receberDano(15);

        assertEquals(5, entidade.getEscudo());
        assertEquals(50, entidade.getVida());
    }
    @Test
    public void testeReceberDanoIgualAoEscudo() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 20, "X");
        
        entidade.receberDano(20);

        assertEquals(0, entidade.getEscudo());
        assertEquals(50, entidade.getVida());
    }
    @Test
    public void testeReceberDanoMaiorQueEscudo() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 20, "X");
        
        entidade.receberDano(25);

        assertEquals(0, entidade.getEscudo());
        assertEquals(45, entidade.getVida());
    }
    @Test
    public void testeReceberDanoFatalNaoDeixaVidaNegativa() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        
        entidade.receberDano(100);

        assertEquals(0, entidade.getVida());
        assertFalse(entidade.estaVivo());
    }
    @Test
    public void testeReceberDanoVerdadeiroIgnoraEscudo() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 20, "X");
        
        entidade.receberDanoVerdadeiro(20);

        assertEquals(20, entidade.getEscudo());
        assertEquals(30, entidade.getVida());
    }
    @Test
    public void testeReceberDanoVerdadeiroFatalNaoDeixaVidaNegativa() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        
        entidade.receberDanoVerdadeiro(60);

        assertEquals(0, entidade.getVida());
        assertFalse(entidade.estaVivo());
    }
    @Test
    public void testCurarNaoUltrapassaVidaMaxima() {
        
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        entidade.setarVida(20); 

        
        entidade.curar(100); 

        
        assertEquals(50, entidade.getVida());
    }

    @Test
    public void testCurarRestauraVidaCorretamente() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        entidade.setarVida(20);

        
        entidade.curar(15);

        assertEquals(35, entidade.getVida());
    }

    @Test
    public void testReceberEscudoESetarEscudo() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 10, "X");

        
        entidade.receberEscudo(15);
        
    
        assertEquals(25, entidade.getEscudo());


        entidade.setarEscudo(5);

        assertEquals(5, entidade.getEscudo());
    }
    @Test
    public void testeAplicarEfeitosSomaOsAcumulosCasoAEntidadeOPossua() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 20, "X");
        FabricaDeEfeito efeito = new FabricaDeEfeito("veneno", 3);

        entidade.aplicarEfeito(efeito.criarEfeito());
        entidade.aplicarEfeito(efeito.criarEfeito());

        assertEquals(6, entidade.getEfeitos().get(0).getAcumulos());
    }
    @Test
    public void testeLimparEfeitosDeixaEfeitosVaziu() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 20, "X");
        FabricaDeEfeito efeito = new FabricaDeEfeito("veneno", 3);

        entidade.aplicarEfeito(efeito.criarEfeito());
        entidade.limparEfeitos();

        assertTrue(entidade.getEfeitos().isEmpty());
    }
    
    // Simula um efeito para podermos controlar quando ele expira
    static class EfeitoTeste extends Efeito {

        public EfeitoTeste(String nome, String tipoDeEfeito, int acumulos, String descricao) {
            super(nome, tipoDeEfeito, acumulos, descricao);
        }

        @Override public void setDono(Entidade dono) { /* ignora pro teste */ }
        

        @Override 
        public void receberNotificacao(Evento evento) {
            // Simulando um efeito que perde 1 acúmulo por turno e desativa quando chega a 0
            this.subtrairAcumulo();
        }
    }
    @Test
    public void testAplicarEfeitoNovoEmEntidadeViva() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        EfeitoTeste veneno = new EfeitoTeste("Veneno", "veneno",  3, "");

        entidade.aplicarEfeito(veneno);

        assertEquals(1, entidade.getEfeitos().size(), "O efeito deve ser adicionado à lista.");
        assertEquals("Veneno", entidade.getEfeitos().get(0).getNome());
    }

    @Test
    public void testAplicarEfeitoRepetidoSomaAcumulos() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        EfeitoTeste veneno1 = new EfeitoTeste("Veneno", "veneno",  2, "");
        EfeitoTeste veneno2 = new EfeitoTeste("Veneno", "veneno",  3, "");

        entidade.aplicarEfeito(veneno1); // Aplica a primeira vez
        entidade.aplicarEfeito(veneno2); // Aplica de novo

        assertEquals(1, entidade.getEfeitos().size(), "Não deve duplicar o efeito na lista.");
        assertEquals(5, entidade.getEfeitos().get(0).getAcumulos());
    }
    @Test
    public void testNotificarSeusEfeitosMantemEfeitosAtivos() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        
        EfeitoTeste veneno = new EfeitoTeste("Veneno", "veneno",  5, "");
        entidade.aplicarEfeito(veneno);

        entidade.notificarSeusEfeitos(Evento.FimDoRound);

        assertEquals(1, entidade.getEfeitos().size(), "O efeito ainda está ativo, não deve ser removido.");
        assertEquals(4, entidade.getEfeitos().get(0).getAcumulos(), "O efeito deve ter recebido a notificação e perdido 1 acúmulo.");
    }

    @Test
    public void testNotificarSeusEfeitosRemoveEfeitosInativos() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        // Efeito que logo vai acabar
        EfeitoTeste veneno = new EfeitoTeste("Veneno", "veneno",  1, "");
        // Efeito forte que vai sobreviver
        EfeitoTeste regeneracao = new EfeitoTeste("Regeneracao", "regeneracao",  5, "");

        entidade.aplicarEfeito(veneno);
        entidade.aplicarEfeito(regeneracao);

        assertEquals(2, entidade.getEfeitos().size(), "Começa com 2 efeitos.");

        
        entidade.notificarSeusEfeitos(Evento.FimDoRound);

        assertEquals(1, entidade.getEfeitos().size());
        assertEquals("Regeneracao", entidade.getEfeitos().get(0).getNome());
    }

    @Test
    public void testNotificarSeusEfeitosLimpaTudoSeEntidadeEstiverMorta() {
        EntidadeTeste entidade = new EntidadeTeste("Alvo", 50, 0, "X");
        entidade.aplicarEfeito(new EfeitoTeste("Veneno", "veneno",  5, ""));
        entidade.aplicarEfeito(new EfeitoTeste("Regeneracao", "regeneracao",  5, ""));

        // Matamos a entidade
        entidade.receberDano(100); 
        assertFalse(entidade.estaVivo(), "A entidade deve estar morta.");

        // Notificamos
        entidade.notificarSeusEfeitos(Evento.FimDoRound);

        assertEquals(0, entidade.getEfeitos().size(), "Como a entidade morreu, todos os efeitos devem ser limpos (limparEfeitos).");
    }
}

