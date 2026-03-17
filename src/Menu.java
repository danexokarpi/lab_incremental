import java.util.Scanner;

public class Menu {
    private Scanner scan = new Scanner(System.in);

    public void status(Heroi heroi, Inimigo inimigo, MaoDoJogador maoDoJogador,
            int energia,
            int energiaMaxima) {
        System.out.println("\n=-=\n" +
                heroi.getNome() + " (" + heroi.getVida() + '/' + heroi.getVidaMaxima() + ") (" + heroi.getEscudo()
                + " de escudo)\n" +
                "vs\n" +
                inimigo.getNome() + " (" + inimigo.getVida() + '/' + inimigo.getVidaMaxima() + ") ("
                + inimigo.getEscudo() + " de escudo)\n" +
                "\n" +
                energia + '/' + energiaMaxima + " de Energia disponível\n");
    }

    public void mao(MaoDoJogador mao) {
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
}
