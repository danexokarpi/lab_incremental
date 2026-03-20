package org.jogo;

import java.util.ArrayList;

public class Tabuleiro {
    private Menu menu;
    private Heroi heroi;
    private Inimigo inimigo;
    private PilhaDeCompra pilhaDeCompra;
    private PilhaDeDescarte pilhaDeDescarte;
    private MaoDoJogador maoDoJogador;
    private int energia, energiaMaxima;

    public Tabuleiro(Heroi heroi, Inimigo inimigo, ArrayList<Carta> cartasInventário,
            int energiaMaxima, int capacidadeDaMao) {
        this.menu = new Menu();
        this.heroi = heroi;
        this.inimigo = inimigo;
        this.pilhaDeCompra = new PilhaDeCompra(cartasInventário);
        this.pilhaDeDescarte = new PilhaDeDescarte();
        this.maoDoJogador = new MaoDoJogador(capacidadeDaMao);
        this.energiaMaxima = energiaMaxima;
    }

    /**
     * Computa a próxima jogada feita pelo Player. Pergunta jogada
     * novamente, caso Player escolha uma opção fora de alcance (número
     * não especificado no menu) ou uma carta com custo acima da energia
     * atual.
     *
     * @return false caso o turno do jogador tenha encerrado, true caso contrário
     */
    private boolean jogadaDoPlayer() {
        boolean heroiEmTurno = true;
        boolean escolhaEhValida = false;
        int escolhaDeEncerrar = maoDoJogador.getTamanho() + 1;
        menu.clearScreen();
        menu.status(heroi, inimigo, maoDoJogador, energia, energiaMaxima);
        while (!escolhaEhValida) {
            menu.escolhas(maoDoJogador);
            int escolhaPlayer = menu.leEscolhaPlayer();
            if (escolhaPlayer < 0 || escolhaPlayer > escolhaDeEncerrar) {
                menu.clearScreen();
                menu.status(heroi, inimigo, maoDoJogador, energia, energiaMaxima);
                menu.escolhaForaDeAlcance();
                continue;
            }
            if (escolhaPlayer == escolhaDeEncerrar) {
                maoDoJogador.descartarTudo(pilhaDeDescarte);
                heroiEmTurno = false;
                escolhaEhValida = true;
                continue;
            }
            Carta cartaEscolhida = maoDoJogador.getCarta(escolhaPlayer - 1);
            if (cartaEscolhida.getCusto() > energia) {
                ;
                menu.clearScreen();
                menu.status(heroi, inimigo, maoDoJogador, energia, energiaMaxima);
                menu.energiaInsuficiente();
                continue;
            }
            cartaEscolhida.usar(this);
            energia -= cartaEscolhida.getCusto();
            pilhaDeDescarte.push(cartaEscolhida);
            maoDoJogador.removeCarta(escolhaPlayer - 1);
            escolhaEhValida = true;
        }
        return heroiEmTurno;
    }

    public void novoTurno() {
        if (pilhaDeCompra.isEmpty()) {
            pilhaDeDescarte.reabastecerCompra(pilhaDeCompra);
        }
        pilhaDeCompra.reabastecerMao(maoDoJogador);
        heroi.setarEscudo(0);
        energia = energiaMaxima;
        boolean heroiEmTurno = true;
        while (heroiEmTurno && inimigo.estaVivo()) {
            heroiEmTurno = this.jogadaDoPlayer();
        }

        if (inimigo.estaVivo()) {
            inimigo.agir(this);
        }
    }

    public void novaBatalha() {
        while (heroi.estaVivo() && inimigo.estaVivo()) {
            this.novoTurno();
        }
        menu.clearScreen();
        menu.status(heroi, inimigo, maoDoJogador, energia, energiaMaxima);
        if (heroi.estaVivo()) {
            menu.playerGanhou();
        } else {
            menu.playerPerdeu();
        }
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public Inimigo getInimigo() {
        return inimigo;
    }

    public PilhaDeCompra getPilhaDeCompra() {
        return pilhaDeCompra;
    }

    public PilhaDeDescarte getPilhaDeDescarte() {
        return pilhaDeDescarte;
    }

    public MaoDoJogador getMaoDoJogador() {
        return maoDoJogador;
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

}
