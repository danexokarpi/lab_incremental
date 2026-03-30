package org.jogo;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe que opera os comandos na tela do jogador.
 *
 * O Menu printa as informações do jogo na tela do terminal, assim como recebe
 * inputs
 * do jogador.
 */
public class Menu {
    private static Scanner scan = new Scanner(System.in);

    public void status(Tabuleiro tabuleiro, MaoDoJogador maoDoJogador,
            int energia,
            int energiaMaxima) {
        Heroi heroi = tabuleiro.getHeroi();
        Inimigo inimigo = tabuleiro.getInimigo();
        System.out.printf("=-=\n");
        System.out.printf("%s (%d/%d) (%d de escudo)\n", heroi.getNome(),
                heroi.getVida(), heroi.getVidaMaxima(), heroi.getEscudo());
        System.out.printf("vs\n");
        System.out.printf("%s (%d/%d) (%d de escudo)\n", inimigo.getNome(),
                inimigo.getVida(), inimigo.getVidaMaxima(), inimigo.getEscudo());
        System.out.printf("Irá %s\n\n", inimigo.imprimirProxAcao(tabuleiro));
        System.out.printf("%d/%d de energia disponível\n", energia, energiaMaxima);
    }

    public void historico(Entidade emissor, char acao, Entidade receptor, int intensidadeDaAcao) {
        switch (acao) {
            case 'A':
                System.out.printf("%s causou %d de dano a %s\n", emissor.getNome(),
                        intensidadeDaAcao, receptor.getNome());
                break;
            case 'E':
                System.out.printf("%s recebeu %d de escudo\n", receptor.getNome(),
                        intensidadeDaAcao);
                break;
            case 'U':
                System.out.printf("%s recebeu %d pontos de vida\n", receptor.getNome(),
                        intensidadeDaAcao);
                break;
            default:
                break;
        }
    }

    public void historico(Entidade emissor, char acao, Entidade receptor, Efeito efeitoCausado) {

    }

    public void escolhas(MaoDoJogador mao) {
        int i = 0;
        while (i < mao.getTamanho()) {
            System.out.printf("%d - %s %s \n", i + 1, mao.getCarta(i).getNome(), mao.getCarta(i).getEfeitoCusto());
            i++;
        }
        System.out.printf("%d - Encerrar Turno\n", i + 1);
    }

    public void escolhaForaDeAlcance() {
        System.out.printf("ATENÇÃO: escolha uma opção dentre os números listados.\n");
    }

    public void energiaInsuficiente() {
        System.out.printf(
                "ENERGIA INSUFICIENTE: a carta selecionada possui custo de energia superior ao nível de energia atual.\n");
    }

    public void playerGanhou() {
        System.out.println("\nParabéns! Você GANHOU\n");
    }

    public void playerPerdeu() {
        System.out.println("\nQue pena! Você perdeu\n");
    }

    public void estradaNaoNumerica() {
        System.out.println("\n ATENÇÂO: A escolha deve ser um número dentre os listados");
    }

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

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
