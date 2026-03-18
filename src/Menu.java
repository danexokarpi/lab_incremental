import java.util.Scanner;

public class Menu {
    private Scanner scan = new Scanner(System.in);

    public void status(Heroi heroi, Inimigo inimigo, MaoDoJogador maoDoJogador,
            int energia,
            int energiaMaxima) {
        System.out.printf("=-=\n");
        System.out.printf("%s (%d/%d) (%d de escudo)\n", heroi.getNome(),
                heroi.getVida(), heroi.getVida(), heroi.getEscudo());
        System.out.printf("vs\n");
        System.out.printf("%s (%d/%d) (%d de escudo)\n\n", inimigo.getNome(),
                inimigo.getVida(), inimigo.getVida(), inimigo.getEscudo());
        System.out.printf("%d/%d de energia disponível\n", energia, energiaMaxima);
    }

    public void escolhaForaDeAlcance() {
        // Número escolhido não é uma opção válida.
    }

    public void energiaInsuficiente() {
        // Carta escolhida possui um custo acima da energia atual.
    }

    public void escolhas(MaoDoJogador mao) {
        int i = 0;
        while (i < mao.getTamanho()) {
            System.out.printf("%d - %s\n", i + 1, mao.getCarta(i).getNome());
        }
        System.out.printf("%d - Encerrar Turno\n", i + 1);
    }

    public int leEscolhaPlayer() {
        System.out.printf("Escolha: ");
        int escolhaPlayer = this.scan.nextInt();
        return escolhaPlayer;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
