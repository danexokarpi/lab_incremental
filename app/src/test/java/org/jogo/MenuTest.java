package org.jogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MenuTest {

    private Menu menu;

    /*
     * *
     * CLASSES FALSAS PARA ISOLAMENTO
     * Simulamos entidades e efeitos genéricos apenas para testar as strings.
     */
    static class EntidadeFalsa extends Entidade {
        private String nomeFalso;

        public EntidadeFalsa(String nome) {
            super(nome, 10, 0, "S");
            this.nomeFalso = nome;
        }

        @Override
        public String getNome() {
            return this.nomeFalso;
        }

        @Override
        public void aplicarEfeito(Efeito efeito) {
        }
    }

    static class EfeitoFalso extends Efeito {
        private String nomeEfeito;

        public EfeitoFalso(String nomeEfeito) {
            super(nomeEfeito, "Status", 1, "");
            this.nomeEfeito = nomeEfeito;
        }

        @Override
        public String getNome() {
            return this.nomeEfeito;
        }

        @Override
        public int getAcumulos() {
            return 1;
        }

        @Override
        public String getTipoDeEfeito() {
            return "Falso";
        }

        @Override
        public void receberNotificacao(EventoDeBatalha eventoOcorrido) {
        }
    }

    /*
     * *
     * PREPARAÇÃO ANTES DE CADA TESTE
     */
    @BeforeEach
    public void setUp() {
        menu = new Menu();
    }

    /*
     * *
     * TESTES DO ALGORITMO DE QUEBRA DE TEXTO
     */
    @Test
    public void testQuebrarTexto_TextoCurtoNaoQuebra() {
        String texto = "Ataque rápido";
        List<String> resultado = menu.quebrarTexto(texto, 20);

        assertEquals(1, resultado.size(), "Texto menor que a largura máxima deve caber em 1 linha.");
        assertEquals("Ataque rápido", resultado.get(0));
    }

    @Test
    public void testQuebrarTexto_TextoLongoQuebraCorretamente() {
        String texto = "Causa dano e aplica veneno no inimigo";
        int larguraMaxima = 15;

        List<String> resultado = menu.quebrarTexto(texto, larguraMaxima);

        // O que esperamos baseado na sua lógica:
        // Linha 1: "Causa dano e" (12 chars)
        // Linha 2: "aplica veneno" (13 chars)
        // Linha 3: "no inimigo" (10 chars)
        assertEquals(3, resultado.size());
        assertEquals("Causa dano e", resultado.get(0));
        assertEquals("aplica veneno", resultado.get(1));
        assertEquals("no inimigo", resultado.get(2));
    }

    /*
     * *
     * TESTES DA GERAÇÃO DE MENSAGENS (AÇÕES)
     */
    @Test
    public void testCriarMensagemDeAcao_Ataque() {
        EntidadeFalsa heroi = new EntidadeFalsa("Guerreiro");
        EntidadeFalsa inimigo = new EntidadeFalsa("Slime");

        String mensagem = menu.criarMensagemDeAcao('A', heroi, inimigo, 15);

        assertEquals("Guerreiro causou 15 de dano a Slime\n", mensagem);
    }

    @Test
    public void testCriarMensagemDeAcao_Cura() {
        EntidadeFalsa heroi = new EntidadeFalsa("Mago");
        EntidadeFalsa nulo = new EntidadeFalsa("Ninguém");

        String mensagem = menu.criarMensagemDeAcao('U', nulo, heroi, 10);

        assertEquals("Mago recebeu 10 pontos de vida\n", mensagem);
    }

    @Test
    public void testCriarMensagemDeAcao_AcaoInvalidaGeraErro() {
        EntidadeFalsa heroi = new EntidadeFalsa("Guerreiro");

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            menu.criarMensagemDeAcao('X', heroi, heroi, 5);
        });

        assertEquals("Ação do tipo 'X' inválida", erro.getMessage());
    }

    /*
     * *
     * TESTES DA GERAÇÃO DE MENSAGENS (EFEITOS)
     */
    @Test
    public void testCriarMensagemDeAcao_EfeitoVeneno() {
        EntidadeFalsa inimigo = new EntidadeFalsa("Goblin");
        EfeitoFalso veneno = new EfeitoFalso("Veneno");

        String mensagem = menu.criarMensagemDeAcao('E', inimigo, 3, veneno);

        assertEquals("Goblin recebeu 3 de dano de veneno\n", mensagem);
    }

    @Test
    public void testCriarMensagemDeAcao_EfeitoInvalidoGeraErro() {
        EntidadeFalsa inimigo = new EntidadeFalsa("Goblin");
        EfeitoFalso efeitoEstranho = new EfeitoFalso("Choque");

        assertThrows(RuntimeException.class, () -> {
            menu.criarMensagemDeAcao('E', inimigo, 5, efeitoEstranho);
        });
    }
}
