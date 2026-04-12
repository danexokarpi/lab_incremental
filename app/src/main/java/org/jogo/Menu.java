package org.jogo;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Classe responsável pela interação com o jogador via console.
 *
 * O Menu centraliza toda a lógica de exibição de informações do jogo,
 * leitura de entradas do usuário e geração de mensagens relacionadas às ações
 * realizadas durante a partida.
 */
public class Menu {
    private static Scanner scan = new Scanner(System.in);

    /**
     * Exibe o estado atual da batalha no console.
     *
     * Mostra informações do herói, inimigos e energia disponível.
     *
     * @param batalha       estado atual do jogo.
     * @param maoDoJogador  mão atual do jogador.
     * @param energia       energia atual disponível.
     * @param energiaMaxima energia máxima do jogador.
     */
    public void status(Batalha batalha, MaoDoJogador maoDoJogador,
            int energia,
            int energiaMaxima) {
        Heroi heroi = batalha.getHeroi();
        ArrayList<Inimigo> inimigos = batalha.getInimigos();
        System.out.printf("=-=\n");
        System.out.printf("%s (%d/%d) (%d de escudo)\n", heroi.getNome(),
                heroi.getVida(), heroi.getVidaMaxima(), heroi.getEscudo());
        System.out.printf("vs\n");
        for (Inimigo inimigo : inimigos) {
            System.out.printf("%s (%d/%d) (%d de escudo)\n", inimigo.getNome(),
                    inimigo.getVida(), inimigo.getVidaMaxima(), inimigo.getEscudo());
            if (inimigo.estaVivo()) {
                System.out.printf("Irá %s\n\n", inimigo.imprimirProxAcao(batalha));
            } else {
                System.out.printf("Está morto.\n\n");
            }

        }
        System.out.printf("%d/%d de energia disponível\n", energia, energiaMaxima);
    }

    /**
     * Cria uma mensagem descritiva de uma ação entre duas entidades.
     *
     * @param acao              identificador da ação.
     * @param emissor           entidade que executou a ação.
     * @param receptor          entidade que recebeu a ação.
     * @param intensidadeDaAcao valor numérico da ação.
     * @return mensagem formatada descrevendo a ação.
     * @throws RuntimeException caso o tipo de ação seja inválido.
     */
    public String criarMensagemDeAcao(char acao, Entidade emissor, Entidade receptor, int intensidadeDaAcao) {
        switch (acao) {
            case 'A':
                return String.format("%s causou %d de dano a %s\n", emissor.getNome(),
                        intensidadeDaAcao, receptor.getNome());
            case 'E':
                return String.format("%s recebeu %d de escudo\n", receptor.getNome(),
                        intensidadeDaAcao);
            case 'U':
                return String.format("%s recebeu %d pontos de vida\n", receptor.getNome(),
                        intensidadeDaAcao);
            default:
                throw new RuntimeException(String.format("Ação do tipo '%c' inválida", acao));
        }
    }

    /**
     * Cria uma mensagem descritiva baseada em um efeito aplicado a uma entidade.
     *
     * @param acao              identificador da ação.
     * @param receptor          entidade afetada.
     * @param intensidadeDaAcao intensidade do efeito.
     * @param efeitoCausado     efeito aplicado.
     * @return mensagem formatada descrevendo o efeito.
     * @throws RuntimeException caso o efeito seja inválido.
     */
    public String criarMensagemDeAcao(char acao, Entidade receptor, int intensidadeDaAcao, Efeito efeitoCausado) {
        if (efeitoCausado.getNome().equals("Veneno")) {
            return String.format("%s recebeu %d de dano de veneno\n", receptor.getNome(), intensidadeDaAcao);
        } else if (efeitoCausado.getNome().equals("Regeneração")) {
            return String.format("%s recebeu %d de cura regenerativa\n", receptor.getNome(), intensidadeDaAcao);
        } else {
            throw new RuntimeException(String.format("Efeito '%s' inválido", efeitoCausado.getTipoDeEfeito()));
        }
    }

    /**
     * Exibe o histórico de ações realizadas durante o jogo.
     *
     * @param historico lista de mensagens representando as ações.
     */
    public void historico(ArrayList<String> historico) {
        for (String acao : historico) {
            System.out.printf("%s\n", acao);
        }
    }

    /**
     * Exibe as opções de cartas disponíveis na mão do jogador.
     *
     * @param mao mão atual do jogador.
     */
    public void escolhas(MaoDoJogador mao) {
        int i = 0;
        while (i < mao.getTamanho()) {
            System.out.printf("%d - %s %s \n", i + 1, mao.getCarta(i).getNome(), mao.getCarta(i).getEfeitoCusto());
            i++;
        }
        System.out.printf("%d - Encerrar Turno\n", i + 1);
    }

    /**
     * Exibe as opções de inimigos disponíveis para seleção.
     *
     * @param inimigos lista de inimigos.
     */
    public void escolhasDeInimigos(ArrayList<Inimigo> inimigos) {
        int i = 0;
        for (Inimigo inimigo : inimigos) {
            System.out.printf("%d - %s", i + 1, inimigo.getNome());
            i++;
            if (!inimigo.estaVivo()) {
                System.out.printf("(Morto)");
            }
            System.out.printf("\n");
        }
        System.out.printf("%d - Cancelar\n", i + 1);
    }

    /**
     * Exibe mensagem de erro para escolha fora do intervalo válido.
     */
    public void escolhaForaDeAlcance() {
        System.out.printf("ATENÇÃO: escolha uma opção dentre os números listados.\n");
    }

    /**
     * Exibe mensagem de erro para energia insuficiente.
     */
    public void energiaInsuficiente() {
        System.out.printf(
                "ENERGIA INSUFICIENTE: a carta selecionada possui custo de energia superior ao nível de energia atual.\n");
    }

    /**
     * Informa que o inimigo selecionado já está morto.
     */
    public void inimigoEstaMorto() {
        System.out.printf(
                "INIMIGO JÁ ESTÁ MORTO: o inimigo selecionado já foi derrotado, essa acao não terá efeito\n");
    }

    /**
     * Exibe mensagem de vitória do jogador.
     */
    public void playerGanhou() {
        System.out.println("\nParabéns! Você GANHOU\n");
    }

    /**
     * Exibe mensagem de derrota do jogador.
     */
    public void playerPerdeu() {
        System.out.println("\nQue pena! Você perdeu\n");
    }

    /**
     * Exibe mensagem de erro para entrada não numérica.
     */
    public void estradaNaoNumerica() {
        System.out.println("\n ATENÇÂO: A escolha deve ser um número dentre os listados");
    }

    /**
     * Lê a escolha do jogador via entrada padrão.
     *
     * O método continua solicitando entrada até que um valor numérico válido seja
     * fornecido.
     *
     * @return número escolhido pelo jogador.
     */
    public int leEscolhaPlayer() {
        boolean inputValido = false;
        System.out.printf("Escolha: ");
        while (!inputValido) {
            try {
                int escolhaPlayer = scan.nextInt();
                return escolhaPlayer;
            } catch (InputMismatchException e) {
                scan.next();

            }
        }
        return 0;
    }

    /**
     * Limpa a tela do console.
     *
     * Utiliza códigos ANSI para limpar a saída do terminal.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
