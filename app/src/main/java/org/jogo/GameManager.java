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

    /**
     * Carrega um jogo existente a partir dos dados salvos.
     * Inicializa o estado do jogo, configura o menu e inicia o loop principal.
     */
    public void carregarJogo() {
        System.out.println("O Gradle está rodando nesta pasta: " + System.getProperty("user.dir"));
        heroi.setarVida(dados.vida);
        this.energiaMaxima = dados.energiaMaxima;
        this.baralho = dados.inventarioHeroi;
        this.posicaoNoMapa = dados.posicaoNoMapa;
        this.capacidadeDaMao = 4;
        this.menu = new Menu();
        FabricaDeBatalha fabricaDeBatalha = new FabricaDeBatalha(heroi, baralho, energiaMaxima, capacidadeDaMao, menu);
        this.mapa = new Mapa(fabricaDeBatalha);
        menu.incializarTela();
        iniciarJogo();
    }

    /**
     * Inicia o loop principal do jogo.
     * Aguarda input do jogador na tela de título e executa as batalhas sequencialmente.
     */
    private void iniciarJogo() {
        boolean esperandoInput = true;
        while (esperandoInput) {
            menu.limparDesenho();
            menu.desenharTitulo();
            menu.aplicarDesenho();
            KeyStroke key = menu.receberInputTeclado();
            if (key != null){
                esperandoInput = false;
            }
        }
        
        boolean ganhou = false;
        while (!mapa.ehUltimoNo(posicaoNoMapa) && !ganhou) {
            //Batalha batalhaAtual = mapa.getBatalha(posicaoNoMapa);
            ganhou = true;
            if (!ganhou) {
                menu.limparDesenho();
                menu.desenharMensagemFinal("Você Perdeu! Seu Save será apagado, até mais!");
                menu.aplicarDesenho();
                menu.esperarFeedback();
                gerenciador.apagarSaveAtual();
                menu.desligarTela();
            }
            
            posicaoNoMapa = escolhaDeProximaPosicao();
            DadosDoSave dadosNovos = new DadosDoSave(heroi.getVida(), energiaMaxima, baralho, posicaoNoMapa);
            gerenciador.salvar(dadosNovos);
        }
        Batalha ultimaBatalha = mapa.getBatalha(posicaoNoMapa);
        ganhou = ultimaBatalha.novaBatalha();
        if (!ganhou) {
            menu.limparDesenho();
                menu.desenharMensagemFinal("Você Perdeu! Seu Save será apagado, até mais!");
                menu.aplicarDesenho();
                menu.esperarFeedback();
                gerenciador.apagarSaveAtual();
                menu.desligarTela();
            }else{
                menu.limparDesenho();
                menu.desenharMensagemFinal("Você Ganhou! Parabéns\nAgora você pode apreveitar tudo que se pode fazer em um deserto desolado!");
                menu.aplicarDesenho();
                menu.esperarFeedback();
                menu.desligarTela();
            }
        
        
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
        while(!escolhaConfirmada) {
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

    /**
     * Inicializa cartas e retorna um baralho em forma de ArrayList.
     *
     * @return lista contendo todas as cartas disponíveis para o baralho
     *         do jogador
     */
    private static ArrayList<Carta> criaCartasDoJogo() {
        CartaDano revolver = new CartaDano("Revolver",
                "O bom o velho conquistador do Oeste.", 1, 3, "Unico");
        CartaDano espingarda = new CartaDano("Espingarda",
                "Nada melhor do que chumbo grosso para acabar com eles.", 2, 4, "Unico");
        CartaDano escopeta = new CartaDano("Escopeta Cerrada",
                "Algum gênio cerrou o cano dessa escopeta, apesar de causar menos dano, acerta multiplos alvos.", 2, 2,
                "Todos");
        CartaDano granada = new CartaDano("Granada",
                "3,2,1....KABUMMM!!!!!!.", 3, 4, "Todos");
        CartaDano mina_antipessoal = new CartaDano("Mina Antipessoal",
                "Ótima para OBLITERAR um indivídue em específico, manual de intruções não incluido", 3, 8, "Unico");
        CartaEscudo escudoEletromagneticoSobrecarregado = new CartaEscudo("Escudo Eletromagnético Sobrecarregado",
                "Crie um campo magnético em volta de si, bloquei quase todo tipo de dano", 3, 10);
        CartaEscudo escudoEletromagneticoGrande = new CartaEscudo("Escudo Eletromagnético Grande",
                "Crie um campo eletromagnético em volta de si, bloquei quase todo tipo de dano", 2, 5);
        CartaEscudo escudoEletromagneticoPequeno = new CartaEscudo("Escudo Eletromagnético Pequeno",
                "Crie um campo magnético em volta de si, bloquei quase todo tipo de dano", 1, 2);
        CartaEfeito stimPack = new CartaEfeito("Stimpack",
                "Uma grande dose de seilá o que direto nas suas veias, vai curar quase qualquer ferida," +
                        "menos as psicológicas",
                2, new FabricaDeEfeito("regeneracao", 3), "Unico");
        CartaEfeito frascoDeVeneno = new CartaEfeito("Frasco de Veneno",
                "Um pequeno fraco com veneno concentrado, lance apenas em inimigos!",
                2, new FabricaDeEfeito("veneno", 3), "Unico");
        CartaEfeito gas_mostarda = new CartaEfeito("Gás Mostarda",
                "Uma arma quimica extremamente tóxica que afeta uma grande área.",
                3, new FabricaDeEfeito("veneno", 2), "Todos");
        Carta[] listaBaralho = { revolver, espingarda, escopeta, mina_antipessoal, granada,
                escudoEletromagneticoGrande, escudoEletromagneticoPequeno, escudoEletromagneticoSobrecarregado,
                stimPack, frascoDeVeneno, gas_mostarda };
        return new ArrayList<Carta>(Arrays.asList(listaBaralho));
    }

}
