package org;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.jogo.FabricaDeEfeito;
import org.jogo.Heroi;
import org.jogo.Batalha;
import org.jogo.Entidade;
import org.jogo.Inimigo;

public class InimigoTest {
    static class InimigoMiraBuff extends Inimigo {
        public InimigoMiraBuff() {
            super("Orc Suporte", 50, 3, 2, 4, 2, new FabricaDeEfeito("regeneracao", 3), new char[] {'U'}, "O");
        }
        
        public Entidade testarMira(Batalha tabuleiro) {
            return super.acharAlvoValido(tabuleiro);
        }
    }
    static class InimigoMira extends Inimigo {
        public InimigoMira() {
            super("Orc", 50, 3, 2, 4, 2, new FabricaDeEfeito("veneno", 3), new char[] {'A'}, "O");
        }
        
        public Entidade testarMira(Batalha tabuleiro) {
            return super.acharAlvoValido(tabuleiro);
        }
    }
    static class AlvoFalso extends Entidade {
        public AlvoFalso(String nome) {
            super(nome, 100, 0, "X");
            this.receberDano(50);
        }
    }
    static class BatalhaFalsa extends Batalha {
        public BatalhaFalsa() {
            super(new Heroi("H", 10, 0, "H"), new ArrayList<>(), new ArrayList<>(), 3, 5);
        }

        @Override
        public void adicionarAoHistorico(char acao, Entidade emissor, Entidade receptor, int intensidade) {
        }
    }

    static class InimigoTeste extends Inimigo {
        public Entidade alvoTeste;
        
        public InimigoTeste() {
            super("Orc", 50, 3, 2, 4, 2, new FabricaDeEfeito("veneno", 3), new char[] {'A'}, "O");
            this.alvoTeste = new AlvoFalso("Herói Teste");
            setProximoAlvo(this.alvoTeste);
        }

        @Override
        protected Entidade acharAlvoValido(Batalha tabuleiro) {
            return this.alvoTeste;
        }
    }
    
    @Test
    public void testGetProxAcao_Atacar() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('A');

        String resultado = inimigo.getProxAcao(null); 

        assertEquals("Atacar: 2", resultado);
    }

    @Test
    public void testGetProxAcao_Curar() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('C');

        String resultado = inimigo.getProxAcao(null);

        assertEquals("Curar: 4", resultado);
    }

    @Test
    public void testGetProxAcao_Proteger() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('E');

        String resultado = inimigo.getProxAcao(null);

        assertEquals("Proteger-se: 2", resultado);
    }

    @Test
    public void testGetProxAcao_AplicarEfeito() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('U');

        String resultado = inimigo.getProxAcao(null);

        assertEquals("Causar Veneno 3 em Herói Teste", resultado);
    }

    @Test
    public void testGetProxAcao_Default() {
        InimigoTeste inimigo = new InimigoTeste();

        inimigo.setProximaAcao('Z'); 

        String resultado = inimigo.getProxAcao(null);

        assertEquals("", resultado);
    }
    
    @Test
    public void testAgir_AtacarReduzVidaDoAlvo() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('A'); 
        BatalhaFalsa tabuleiro = new BatalhaFalsa();

        inimigo.agir(tabuleiro);

        assertEquals(48, inimigo.alvoTeste.getVida());
    }

    @Test
    public void testAgir_CurarAumentaVidaDoAlvo() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('C'); 
        BatalhaFalsa tabuleiro = new BatalhaFalsa();

        inimigo.agir(tabuleiro);

        assertEquals(54, inimigo.alvoTeste.getVida());
    }

    @Test
    public void testAgir_ProtegerAumentaEscudoDoAlvo() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('E'); 
        BatalhaFalsa tabuleiro = new BatalhaFalsa();

        inimigo.agir(tabuleiro);

        assertEquals(2, inimigo.alvoTeste.getEscudo());
    }

    @Test
    public void testAgir_AplicarEfeitoAdicionaNaListaDoAlvo() {
        InimigoTeste inimigo = new InimigoTeste();
        inimigo.setProximaAcao('U'); 
        BatalhaFalsa tabuleiro = new BatalhaFalsa();

        inimigo.agir(tabuleiro);

        // O alvo deve passar a ter 1 efeito ativo na sua lista
        assertEquals(1, inimigo.alvoTeste.getEfeitos().size());
        assertEquals("Veneno", inimigo.alvoTeste.getEfeitos().get(0).getNome());
    }
    // 
    @Test
    public void testAcharAlvoValido_AtaqueMiraNoHeroi() {
        InimigoMira inimigo = new InimigoMira();
        inimigo.setProximaAcao('A');
        
        Heroi heroi = new Heroi("H", 100, 0, "H");
        Batalha batalha = new Batalha(heroi, new ArrayList<>(), new ArrayList<>(), 3, 5);
        
        Entidade alvo = inimigo.testarMira(batalha);
        
        assertEquals(heroi, alvo);
    }

    @Test
    public void testAcharAlvoValido_CuraMiraEmSiMesmo() {
        InimigoMira inimigo = new InimigoMira();
        inimigo.setProximaAcao('C');
        
        Entidade alvo = inimigo.testarMira(null); 
        
        assertEquals(inimigo, alvo);
    }

    @Test
    public void testAcharAlvoValido_ProtegerMiraEmSiMesmo() {
        InimigoMira inimigo = new InimigoMira();
        inimigo.setProximaAcao('E');
        
        Entidade alvo = inimigo.testarMira(null); 
        
        assertEquals(inimigo, alvo);
    }

    @Test
    public void testAcharAlvoValido_AcaoInvalidaRetornaNull() {
        InimigoMira inimigo = new InimigoMira();
        inimigo.setProximaAcao('Z'); // Letra que não existe
        
        Entidade alvo = inimigo.testarMira(null); 
        
        assertNull(alvo);
    }

    @Test
    public void testAcharAlvoValido_EfeitoDebuffMiraNoHeroi() {
        InimigoMira inimigo = new InimigoMira();
        inimigo.setProximaAcao('U');
        
        Heroi heroi = new Heroi("H", 100, 0, "H");
        Batalha batalha = new Batalha(heroi, new ArrayList<>(), new ArrayList<>(), 3, 5);
        
        Entidade alvo = inimigo.testarMira(batalha);
         
        assertEquals(heroi, alvo);
    }
    @Test
    public void testAcharAlvoValido_EfeitoBuffMiraEmUmInimigo() {
        InimigoMiraBuff inimigo = new InimigoMiraBuff();
        inimigo.setProximaAcao('U');
        
        ArrayList<Inimigo> listaInimigos = new ArrayList<>();
        listaInimigos.add(inimigo); 
        
        Heroi heroiFalso = new Heroi("H", 100, 0, "H");
        Batalha batalha = new Batalha(heroiFalso, listaInimigos, new ArrayList<>(), 3, 5);
        
        Entidade alvo = inimigo.testarMira(batalha);
        
        
        assertTrue(listaInimigos.contains(alvo));
    }
}