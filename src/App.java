import java.util.ArrayList;
import java.util.Scanner;

public class App {
    // IDEIA É, ENTREGAR A LISTA DE NOMES DE CARTAS, DAI PARA CADA TURNO DE JOGADOR
    // A GENTE TIRA O NOME DE DENTRO DA LISTA E A LISTA DE ESCOLHA DEVOLVE O NOME DA
    // CARTA ESCOLHIDA
    // ACHO QUE ASSIM DA CERTO SEM TRABALHO
    // :)
    public int escolher(int quantidadeDecisoes, List listaOpcoes) {
        return 0;
    }

    public static void printaMenu(Heroi heroi, Inimigo inimigo, CartaDano cartaDano, CartaEscudo cartaEscudo,
            int energia,
            int energiaMaxima) {
        System.out.println("=-=\n" +
                heroi.nome + " (" + heroi.vida + '/' + heroi.vidaMaxima + ") (" + heroi.escudo + " de escudo)\n" +
                "vs\n" +
                inimigo.nome + " (" + inimigo.vida + '/' + inimigo.vidaMaxima + ")\n" +
                "\n" +
                energia + '/' + energiaMaxima + " de Energia disponível\n" +
                "1 - Usar" + cartaDano.nome + "\n" +
                "2 - Usar " + cartaEscudo.nome + "\n" +
                "3 - Encerrar turno\n");

        System.out.println("Escolha: ");
    }

    public static int leEscolhaPlayer(Scanner scan) {
        int escolhaPlayer = scan.nextInt();
        return escolhaPlayer;
    }

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);

        Heroi heroi = new Heroi("Capitão Cabra", 10, 0);
        Inimigo inimigo = new Inimigo("Gosma", 10, 10);
        CartaDano espadaSuprema = new CartaDano("Espadada Suprema", 1, 3);
        CartaEscudo escudoSupremo = new CartaEscudo("Escudo Supremo", 1, 3);

        int energia, energiaMaxima = 3;

        while (heroi.estaVivo() || inimigo.estaVivo()) {
            energia = energiaMaxima;

            boolean emTurno = true;
            while (emTurno) {
                printaMenu(heroi, inimigo, espadaSuprema, escudoSupremo, energia, energiaMaxima);
                int escolhaPlayer = leEscolhaPlayer(scan);
                switch (escolhaPlayer) {
                    case 1:
                        // CartaDano
                        break;
                    case 2:
                        // CartaEscudo
                        break;
                    case 3:
                        // EncerrarTurno
                        break;

                    default:
                        // comando inválido
                        break;
                }

            }

        }

    }

}
