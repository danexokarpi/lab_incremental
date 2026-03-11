
//import java.util.ArrayList;
import java.util.Scanner;

public class App {
    // IDEIA É, ENTREGAR A LISTA DE NOMES DE CARTAS, DAI PARA CADA TURNO DE JOGADOR
    // A GENTE TIRA O NOME DE DENTRO DA LISTA E A LISTA DE ESCOLHA DEVOLVE O NOME DA
    // CARTA ESCOLHIDA
    // ACHO QUE ASSIM DA CERTO SEM TRABALHO
    // :)

    // public int escolher(int quantidadeDecisoes, ArrayList listaOpcoes) {
    // return 0;
    // }

    public static void printaMenu(Heroi heroi, Inimigo inimigo, CartaDano cartaDano, CartaEscudo cartaEscudo,
            int energia,
            int energiaMaxima) {
        System.out.println("\n=-=\n" +
                heroi.getNome() + " (" + heroi.getVida() + '/' + heroi.getVidaMaxima() + ") (" + heroi.getEscudo()
                + " de escudo)\n" +
                "vs\n" +
                inimigo.getNome() + " (" + inimigo.getvida() + '/' + inimigo.getVidaMaxima() + ") ("
                + inimigo.getEscudo() + " de escudo)\n" +
                "\n" +
                energia + '/' + energiaMaxima + " de Energia disponível\n" +
                "1 - Usar" + cartaDano.getNome() + "\n" +
                "2 - Usar " + cartaEscudo.getNome() + "\n" +
                "3 - Encerrar turno\n");

        System.out.println("Escolha: ");
    }

    public static int leEscolhaPlayer(Scanner scan) {
        int escolhaPlayer = scan.nextInt();
        return escolhaPlayer;
    }

    private static void novoTurno(Scanner scan, Heroi heroi, Inimigo inimigo, CartaDano cartaDano,
            CartaEscudo cartaEscudo, int energiaMaxima) {
        heroi.setEscudo(0);
        int energia = energiaMaxima;
        boolean emTurno = true;
        while (emTurno) {
            clearScreen();
            printaMenu(heroi, inimigo, cartaDano, cartaEscudo, energia, energiaMaxima);
            int escolhaPlayer = leEscolhaPlayer(scan);
            switch (escolhaPlayer) {
                case 1:
                    if (cartaDano.usarSePossivel(inimigo, energia)) {
                        energia -= cartaDano.getCusto();
                    }
                    break;
                case 2:
                    if (cartaEscudo.usarSePossivel(heroi, energia)) {
                        energia -= cartaEscudo.getCusto();
                    }
                    break;
                case 3:
                    emTurno = false;
                    break;

                default:
                    // comando inválido
                    break;
            }
            if (!inimigo.estaVivo()) {
                break;
            }

        }

        if (inimigo.estaVivo()) {
            heroi.receberDano(inimigo.getDano());
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        Heroi heroi = new Heroi("Capitão Cabra", 10, 0);
        Inimigo inimigo = new Inimigo("Gosma", 10, 0, 2);
        CartaDano espadaSuprema = new CartaDano("Espadada Suprema", 1, 3);
        CartaEscudo escudoSupremo = new CartaEscudo("Escudo Supremo", 1, 3);

        int energiaMaxima = 3;

        while (heroi.estaVivo() && inimigo.estaVivo()) {
            novoTurno(scan, heroi, inimigo, espadaSuprema, escudoSupremo, energiaMaxima);
        }

        if (heroi.estaVivo())
            System.out.println("\nParabéns! Você GANHOU\n");
        else
            System.out.println("\nQue pena! Você perdeu\n");

    }

}
