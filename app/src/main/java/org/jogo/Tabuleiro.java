package org.jogo;

import java.util.ArrayList;
public class Tabuleiro {
    private Menu menu;
    private Heroi heroi;
    private ArrayList<Inimigo> inimigos;
    private ArrayList<Entidade> entidadesEmJogo;
    private PilhaDeCompra pilhaDeCompra;
    private PilhaDeDescarte pilhaDeDescarte;
    private MaoDoJogador maoDoJogador;
    private int energia, energiaMaxima;
    

    public Tabuleiro(Heroi heroi, ArrayList<Inimigo> inimigos, ArrayList<Carta> cartasInventário,
            int energiaMaxima, int capacidadeDaMao) {
        this.menu = new Menu();
        this.heroi = heroi;
        this.inimigos = inimigos;
        this.entidadesEmJogo = new ArrayList<>();
        entidadesEmJogo.add(heroi);
        entidadesEmJogo.addAll(inimigos);
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
        menu.status(this, maoDoJogador, energia, energiaMaxima);
        while (!escolhaEhValida) {
            menu.escolhas(maoDoJogador);
            int escolhaPlayer = menu.leEscolhaPlayer();
            
            if (escolhaPlayer < 0 || escolhaPlayer > escolhaDeEncerrar) {
                menu.clearScreen();
                menu.status(this, maoDoJogador, energia, energiaMaxima);
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
                menu.status(this, maoDoJogador, energia, energiaMaxima);
                menu.energiaInsuficiente();
                continue;
            }
            if (cartaEscolhida.usar(this)){
                energia -= cartaEscolhida.getCusto();
                pilhaDeDescarte.push(cartaEscolhida);
                maoDoJogador.removeCarta(escolhaPlayer - 1);
                escolhaEhValida = true;
            } else {
                menu.clearScreen();
                menu.status(this, maoDoJogador, energia, energiaMaxima);
                continue;
            }
            
        }
        return heroiEmTurno;
    }

    public void novoRound() {
        if (pilhaDeCompra.isEmpty()) {
            pilhaDeDescarte.reabastecerCompra(pilhaDeCompra);
        }
        pilhaDeCompra.reabastecerMao(maoDoJogador, pilhaDeDescarte);
        heroi.setarEscudo(0);
        energia = energiaMaxima;
        boolean heroiEmTurno = true;
        while (heroiEmTurno && !todosInimigosMortos()) {
            heroiEmTurno = this.jogadaDoPlayer();
        }

        turnoDosInimigos();

        notificarEvento(Evento.FimDoRound);
    }

    public void novaBatalha() {
        while (heroi.estaVivo() && !todosInimigosMortos()) {
            this.novoRound();
        }
        menu.clearScreen();
        menu.status(this, maoDoJogador, energia, energiaMaxima);
        if (heroi.estaVivo()) {
            menu.playerGanhou();
        } else {
            menu.playerPerdeu();
        }
    }

    public Inimigo escolherUmInimigo(){
        boolean escolhaEhValida = false;
        int escolhaDeCancelar = inimigos.size() + 1;
        menu.clearScreen();
        menu.status(this, maoDoJogador, energia, energiaMaxima);
        while(!escolhaEhValida){
            menu.escolhasDeInimigos(inimigos);
            int escolhaPlayer = menu.leEscolhaPlayer();
            if (escolhaPlayer < 0 || escolhaPlayer > escolhaDeCancelar) {
                menu.clearScreen();
                menu.status(this, maoDoJogador, energia, energiaMaxima);
                menu.escolhaForaDeAlcance();
                continue;
            }
            if (escolhaPlayer == escolhaDeCancelar) {
                escolhaEhValida = true;
                continue;
            }
            if(inimigos.get(escolhaPlayer - 1).estaVivo()){
                escolhaEhValida = true;
                return inimigos.get(escolhaPlayer - 1);
            }else if(!inimigos.get(escolhaPlayer - 1).estaVivo()){
                menu.clearScreen();
                menu.status(this, maoDoJogador, energia, energiaMaxima);
                menu.inimigoEstaMorto();
            }
                
        }
        return null;
    }

    public void notificarEvento(Evento eventoOcorrido){
        for(Entidade entidade : entidadesEmJogo){
            entidade.notificarSeusEfeitos(eventoOcorrido);
        }
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public ArrayList<Inimigo> getInimigos() {
        return inimigos;
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

    private void turnoDosInimigos(){
        for(Inimigo inimigo : inimigos){
            if (inimigo.estaVivo()) {
                inimigo.setarEscudo(0);
                inimigo.agir(this);
            }
        }
    }

    private boolean todosInimigosMortos(){
        int quantidadeDeMortos = 0;
        for (Inimigo inimigo : inimigos){
            if(!inimigo.estaVivo()){
                quantidadeDeMortos++;
            }
            if(quantidadeDeMortos == inimigos.size()) return true;
        }
        return false;
    }
}
