import java.util.Random;

public class Inimigo extends Entidade {
    private static Random random = new Random();
    private int dano;
    private int cura;
    private int escudoRecebido;
    private char[] listaDeAcoes;
    private char proximaAcao;

    public Inimigo(String nome, int vidaMaxima, int escudo, int dano, int cura, char[] listaDeAcoes) {
        super(nome, vidaMaxima, escudo);
        this.dano = dano;
        this.listaDeAcoes = listaDeAcoes; 
        this.cura = cura;
        this.proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    public void agir(Tabuleiro tabuleiro) {
        switch (proximaAcao) {
            case 'A':
                tabuleiro.getHeroi().receberDano(dano);
                break;
            case 'C':
                this.curar(cura);
                break;
            case 'E':
                this.receberEscudo(escudoRecebido);
                break;
        }
    }



    public int getDano() {
        return this.dano;
    }

}
