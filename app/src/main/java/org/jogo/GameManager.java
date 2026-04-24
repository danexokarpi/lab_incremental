package org.jogo;

import java.util.ArrayList;
import java.util.Arrays;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * Gerencia o fluxo principal do jogo, controlando o estado global,
 * carregamento de saves, navegação pelo mapa e execução das batalhas.
 * 
 * Esta classe atua como o controlador central do jogo, orquestrando
 * a interação entre o herói, o baralho de cartas, o mapa e o menu.
 * Utiliza o padrão Singleton implícito através de seus atributos estáticos.
 */
public class GameManager {

    private static GerenciadorDeJson gerenciador = new GerenciadorDeJson();
    private static DadosDoSave dados = gerenciador.carregarSave();
    private static Heroi heroi = criaHeroi();
    private static int energiaMaxima;
    private static ArrayList<Carta> baralho;
    private static int capacidadeDaMao;
    private static Mapa mapa;
    private Menu menu;
    private static int posicaoNoMapa;

    public GameManager() {
        System.out.println("O Gradle está rodando nesta pasta: " + System.getProperty("user.dir"));
        heroi.setarVida(dados.vida);
        this.energiaMaxima = dados.energiaMaxima;
        this.baralho = dados.inventarioHeroi;
        this.posicaoNoMapa = dados.posicaoNoMapa;
        this.capacidadeDaMao = 4;
        this.menu = new Menu();
        FabricaDeEvento fabricaDeEvento = new FabricaDeEvento(heroi, baralho, energiaMaxima, capacidadeDaMao, menu);
        this.mapa = new Mapa(fabricaDeEvento);
    }

    public void carregarJogo() {
        menu.incializarTela();
        tituloDoJogo();
        iniciarJogo();
    }

    private void tituloDoJogo() {
        boolean esperandoInput = true;
        while (esperandoInput) {
            menu.limparDesenho();
            menu.desenharTitulo();
            menu.aplicarDesenho();
            KeyStroke key = menu.receberInputTeclado();
            if (key != null) {
                esperandoInput = false;
            }
        }
    }

    /**
     * Inicia o loop principal do jogo.
     * Inicializa os eventos do mapa sequencialmente de acordo com as escolhas do
     * player, até final do jogo (tanto nos casos de derrota, quanto de vitória)
     */
    private void iniciarJogo() {
        boolean visitouUltimoNo = false;
        while (!visitouUltimoNo) {
            Evento eventoAtual = mapa.getEvento(posicaoNoMapa);
            eventoAtual.iniciar();
            if (!eventoAtual.ganhou()) {
                acabarJogoPerdido();
                return;
            }
            if (mapa.ehUltimoNo(posicaoNoMapa)) {
                visitouUltimoNo = true;
                continue;
            }
            posicaoNoMapa = escolhaDeProximaPosicao();
            DadosDoSave dadosNovos = new DadosDoSave(heroi.getVida(), energiaMaxima, baralho, posicaoNoMapa);
            gerenciador.salvar(dadosNovos);
        }
        acabarJogoGanho();
    }

    private void acabarJogoPerdido() {
        menu.limparDesenho();
        menu.desenharMensagemFinal("Você Perdeu! Seu Save será apagado, até mais!");
        menu.aplicarDesenho();
        menu.esperarFeedback();
        gerenciador.apagarSaveAtual();
        menu.desligarTela();
    }

    private void acabarJogoGanho() {
        menu.limparDesenho();
        menu.desenharMensagemFinal(
                "Você ganhou! Parabéns\nAgora você pode apreveitar tudo que se pode fazer em um deserto desolado!");
        menu.aplicarDesenho();
        menu.esperarFeedback();
        menu.desligarTela();
    }

    /**
     * Permite ao jogador escolher a próxima posição no mapa.
     * 
     * @return o índice da posição escolhida pelo jogador
     */
    private int escolhaDeProximaPosicao() {
        ArrayList<Integer> opcoesDeCaminho = mapa.getOpcoesDeCaminho(posicaoNoMapa);
        int opcao = 0;
        boolean escolhaConfirmada = false;
        while (!escolhaConfirmada) {
            menu.limparDesenho();
            menu.desenharMapa();
            menu.desenharPersonagemNoMapa(opcoesDeCaminho.get(opcao));
            menu.aplicarDesenho();

            KeyStroke key = menu.receberInputTeclado();

            if (key.getKeyType() == KeyType.ArrowUp) {
                opcao--;
                if (opcao < 0) {
                    opcao = opcoesDeCaminho.size() - 1;
                }
            } else if (key.getKeyType() == KeyType.ArrowDown) {
                opcao++;
                if (opcao >= opcoesDeCaminho.size()) {
                    opcao = 0;
                }
            } else if (key.getKeyType() == KeyType.Enter) {
                escolhaConfirmada = true;
            }
        }
        int posicaoEscolhida = opcoesDeCaminho.get(opcao);
        return posicaoEscolhida;
    }

    /**
     * Cria e configura o herói principal do jogo.
     * 
     * @return instância do herói configurada
     */
    private static Heroi criaHeroi() {
        return new Heroi("Capitão Cabra", 15, 0,
                "" + //
                        "            +$;     X            \n" + //
                        "           &X:                   \n" + //
                        "           $;.  +         x  ;   \n" + //
                        "          :X&;       + $&XX+;    \n" + //
                        "    :&&&$&&X&x$&&&&X X+&&&&$     \n" + //
                        "     X&&&&&$$;$ & .:  ++&X       \n" + //
                        "       &&&&X+X  x .   :          \n" + //
                        "           $&&$;X$+X X           \n" + //
                        "           &$&&&$x; X            \n" + //
                        "       x..&+$$&&xXXX:            \n" + //
                        "    . + ;& +.X+&$&  & :          \n" + //
                        "          &;:+&.   + x : x       \n" + //
                        "                  &x+:x :    ");
    }
}
