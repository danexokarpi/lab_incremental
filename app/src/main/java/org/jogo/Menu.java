package org.jogo;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    private static Scanner scan = new Scanner(System.in);

    public void status(Tabuleiro tabuleiro, MaoDoJogador maoDoJogador,
            int energia,
            int energiaMaxima) {
        Heroi heroi = tabuleiro.getHeroi();
        ArrayList<Inimigo> inimigos = tabuleiro.getInimigos();
        System.out.printf("=-=\n");
        System.out.printf("%s (%d/%d) (%d de escudo)\n", heroi.getNome(),
                heroi.getVida(), heroi.getVidaMaxima(), heroi.getEscudo());
        System.out.printf("vs\n");
        for(Inimigo inimigo : inimigos){
            System.out.printf("%s (%d/%d) (%d de escudo)\n", inimigo.getNome(),
                inimigo.getVida(), inimigo.getVidaMaxima(), inimigo.getEscudo());
            if(inimigo.estaVivo()){
                System.out.printf("Irá %s\n\n", inimigo.imprimirProxAcao(tabuleiro));
            }else{
                System.out.printf("Está morto.\n\n");
            }
            
        }
        System.out.printf("%d/%d de energia disponível\n", energia, energiaMaxima);
    }

    public void escolhas(MaoDoJogador mao) {
        int i = 0;
        while (i < mao.getTamanho()) {
            System.out.printf("%d - %s %s \n", i + 1, mao.getCarta(i).getNome(), mao.getCarta(i).getEfeitoCusto());
            i++;
        }
        System.out.printf("%d - Encerrar Turno\n", i + 1);
    }

    public void escolhasDeInimigos(ArrayList<Inimigo> inimigos){
        int i = 0;
        for (Inimigo inimigo : inimigos){
            System.out.printf("%d - %s", i + 1, inimigo.getNome());
            i++;
            if (!inimigo.estaVivo()){
                System.out.printf("(Morto)");
            }
            System.out.printf("\n");
        }
        System.out.printf("%d - Cancelar\n", i + 1);
    }

    public void escolhaForaDeAlcance() {
        System.out.printf("ATENÇÃO: escolha uma opção dentre os números listados.\n");
    }

    public void energiaInsuficiente() {
        System.out.printf(
                "ENERGIA INSUFICIENTE: a carta selecionada possui custo de energia superior ao nível de energia atual.\n");
    }

    public void inimigoEstaMorto(){
        System.out.printf(
            "INIMIGO JÁ ESTÁ MORTO: o inimigo selecionado já foi derrotado, essa acao não terá efeito\n");
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
