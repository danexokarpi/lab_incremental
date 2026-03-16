
//import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;

public class App {

    // IDEIA É, ENTREGAR A LISTA DE NOMES DE CARTAS, DAI PARA CADA TURNO DE JOGADOR
    // A GENTE TIRA O NOME DE DENTRO DA LISTA E A LISTA DE ESCOLHA DEVOLVE O NOME DA
    // CARTA ESCOLHIDA
    // ACHO QUE ASSIM DA CERTO SEM TRABALHO
    // :)

    // public int escolher(int quantidadeDecisoes, ArrayList listaOpcoes) {
    // return 0;
    // }
    //public static void printaTitulo(){
    //    
    //}

    public static void printaMenu(Heroi heroi, Inimigo inimigo, MaoDoJogador maoDoJogador,
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
        int i = 0;
        for (; i < maoDoJogador.getTamanho(); i++){
            System.out.println((i+1) + " - " + maoDoJogador.getCarta(i).getNome());
        }
        System.out.println((i+1) + " - Encerrar Turno" );
        System.out.println("Escolha: ");
    }

    public static int leEscolhaPlayer(Scanner scan) {
        int escolhaPlayer = scan.nextInt();
        return escolhaPlayer;
    }

    private static void novoTurno(Scanner scan, Tabuleiro tabuleiro, PilhaDeCompra pilhaDeCompra, PilhaDeDescarte pilhaDeDescarte, int energiaMaxima) {
        Heroi heroi = tabuleiro.getHeroi();
        Inimigo inimigo = tabuleiro.getinimigo();

        heroi.setarEscudo(0);
        if(pilhaDeCompra.isempty()){
            pilhaDeDescarte.reabastecerCompra(pilhaDeCompra);
        }
        
        MaoDoJogador maoDoJogador = new MaoDoJogador(2); // DEPOIS PRECISAMOS FAZER UMA VARIÁVEL PARA A CAPACIDADE

        while(!maoDoJogador.estaCheia()){
            maoDoJogador.addCarta(pilhaDeCompra.pop());
        }

        int energia = energiaMaxima;
        boolean emTurno = true;

        while (emTurno) {
            clearScreen();
            printaMenu(heroi, inimigo, maoDoJogador, energia, energiaMaxima);
            int escolhaPlayer = leEscolhaPlayer(scan) - 1;
            if (escolhaPlayer >= 0 && escolhaPlayer != maoDoJogador.getTamanho()){
                Carta carta = maoDoJogador.getCarta(escolhaPlayer);
                
                if(carta.usarSePossivel(tabuleiro, energia)){
                    energia -= carta.getCusto();
                    pilhaDeDescarte.push(carta);
                    maoDoJogador.removeCarta(escolhaPlayer);
                }
                continue;

            }else if(escolhaPlayer >= 0 && escolhaPlayer == maoDoJogador.getTamanho()){
                emTurno = false;
                continue;
            }

            if (!inimigo.estaVivo()) {
                printaMenu(heroi, inimigo, maoDoJogador, energia, energiaMaxima);
                continue;
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

        ArrayList<Carta> listaInventarioJogador = new ArrayList<Carta>();


        Heroi heroi = new Heroi("Capitão Cabra", 10, 0);
        Inimigo inimigo = new Inimigo("Gosma", 10, 0, 2);
        Tabuleiro tabuleiro = new Tabuleiro(heroi, inimigo);

        CartaDano laserCinético = new CartaDano("Laser Cinético", "Atira luz estimulada por emissão de radiação, pesquise a sigla!", 1, 3);
        CartaDano sabreDeFotons = new CartaDano("Sabre de Fótons", "Consegue atravessar quase qualquer coisa. \nQualquer semelhança é mera coencidência", 2, 4);
        CartaEscudo escudoEletromagneticoGrande = new CartaEscudo("Escudo Eletromagnético Grande", "Crie um campo magnético em volta de si, a única fraqueza do sabre de fótons", 2, 5);
        CartaEscudo escudoEletromagneticoPequeno = new CartaEscudo("Escudo Eletromagnético Pequeno", "Crie um campo magnético em volta de si, a única fraqueza do sabre de fótons", 1, 2);
        
        listaInventarioJogador.add(laserCinético); listaInventarioJogador.add(sabreDeFotons); listaInventarioJogador.add(escudoEletromagneticoGrande); listaInventarioJogador.add(escudoEletromagneticoPequeno);

        int energiaMaxima = 3;

        PilhaDeCompra pilhaDeCompra = new PilhaDeCompra(listaInventarioJogador);
        PilhaDeDescarte pilhaDeDescarte = new PilhaDeDescarte();

        while (heroi.estaVivo() && inimigo.estaVivo()) {
            novoTurno(scan, tabuleiro, pilhaDeCompra, pilhaDeDescarte, energiaMaxima);
        }

        if (heroi.estaVivo())
            System.out.println("\nParabéns! Você GANHOU\n");
        else
            System.out.println("\nQue pena! Você perdeu\n");

    }

}