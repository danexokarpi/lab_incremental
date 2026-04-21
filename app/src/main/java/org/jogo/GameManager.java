package org.jogo;

import java.util.ArrayList;
import java.util.Arrays;

public final class GameManager {
    private static GerenciadorDeJson gerenciador = new GerenciadorDeJson();
    private static DadosDoSave dados = gerenciador.carregarSave();
    private static Heroi heroi = criaHeroi();
    private static int energiaMaxima;
    private static ArrayList<Carta> baralho;
    private static int capacidadeDaMao;
    private static Mapa mapa;
    private static int posicaoNoMapa;

    public static void carregarJogo() {
        System.out.println("O Gradle está rodando nesta pasta: " + System.getProperty("user.dir"));
        heroi.setarVida(dados.vida);
        energiaMaxima = dados.energiaMaxima;
        baralho = dados.inventarioHeroi;
        capacidadeDaMao = 4;
        FabricaDeBatalha fabricaDeBatalha = new FabricaDeBatalha(heroi, baralho, energiaMaxima, capacidadeDaMao);
        mapa = new Mapa(fabricaDeBatalha);
        posicaoNoMapa = 0;
        iniciarJogo();
    }

    private static void iniciarJogo() {
        while (!mapa.ehUltimoNo(posicaoNoMapa)) {
            Batalha batalhaAtual = mapa.getBatalha(posicaoNoMapa);
            batalhaAtual.novaBatalha();
            posicaoNoMapa = perguntaProximaPosicao();
        }
        Batalha ultimaBatalha = mapa.getBatalha(posicaoNoMapa);
        ultimaBatalha.novaBatalha();
        MenuPrincipal.finalDoJogo();
    }

    private static int perguntaProximaPosicao() {
        ArrayList<Integer> opcoesDeCaminho = mapa.getOpcoesDeCaminho(posicaoNoMapa);
        int opcaoEscolhida = MenuPrincipal.perguntaProximaPosicao(opcoesDeCaminho);
        return opcaoEscolhida;
    }

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
