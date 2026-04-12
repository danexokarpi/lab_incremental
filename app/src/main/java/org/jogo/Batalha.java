package org.jogo;

import java.util.ArrayList;

/**
 * Representa o estado e a lógica central de uma batalha no jogo.
 *
 * A classe Batalha é responsável por gerenciar todos os elementos
 * envolvidos em uma partida, incluindo:
 *
 * Herói e inimigos em combate
 * Pilhas de compra e descarte
 * Mão do jogador
 * Energia disponível
 * Histórico de ações
 *
 * Além disso, controla o fluxo da batalha, incluindo rounds, turnos do jogador,
 * turnos dos inimigos e aplicação de efeitos.
 */
public class Batalha {
    private Menu menu;
    private Heroi heroi;
    private ArrayList<Inimigo> inimigos;
    private ArrayList<Entidade> entidadesEmJogo;
    private PilhaDeCompra pilhaDeCompra;
    private PilhaDeDescarte pilhaDeDescarte;
    private MaoDoJogador maoDoJogador;
    private int energia, energiaMaxima;
    private ArrayList<String> historicoDeAcoes;

    /**
     * Cria um novo batalha com os elementos iniciais da batalha.
     *
     * @param heroi            personagem controlado pelo jogador.
     * @param inimigos         lista de inimigos presentes na batalha.
     * @param cartasInventário conjunto inicial de cartas para a pilha de compra.
     * @param energiaMaxima    quantidade máxima de energia do jogador por turno.
     * @param capacidadeDaMao  número máximo de cartas na mão do jogador.
     */
    public Batalha(Heroi heroi, ArrayList<Inimigo> inimigos, ArrayList<Carta> cartasInventário,
            int energiaMaxima, int capacidadeDaMao) {
        this.menu = new Menu();
        this.heroi = heroi;
        this.inimigos = inimigos;
        this.entidadesEmJogo = new ArrayList<Entidade>();
        entidadesEmJogo.add(heroi);
        entidadesEmJogo.addAll(inimigos);
        this.pilhaDeCompra = new PilhaDeCompra(cartasInventário);
        this.pilhaDeDescarte = new PilhaDeDescarte();
        this.maoDoJogador = new MaoDoJogador(capacidadeDaMao);
        this.energiaMaxima = energiaMaxima;
        this.historicoDeAcoes = new ArrayList<String>();
    }

    /**
     * Computa a próxima jogada feita pelo Player.
     *
     * Pergunta jogada novamente, caso Player escolha uma opção fora de alcance
     * (número não especificado no menu) ou uma carta com custo acima da energia
     * atual.
     *
     * @return {@code false} caso o turno do jogador tenha encerrado, {@code true}
     *         caso contrário.
     */
    private boolean jogadaDoPlayer() {
        boolean heroiEmTurno = true;
        boolean escolhaEhValida = false;
        int escolhaDeEncerrar = maoDoJogador.getTamanho() + 1;
        menu.clearScreen();
        menu.historico(historicoDeAcoes);
        this.historicoDeAcoes.clear();
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
                menu.clearScreen();
                menu.status(this, maoDoJogador, energia, energiaMaxima);
                menu.energiaInsuficiente();
                continue;
            }
            if (cartaEscolhida.usar(this)) {
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

    /**
     * Executa a lógica de um novo round no jogo.
     *
     * O método é responsável por controlar todo o fluxo de um turno completo,
     * incluindo preparação, turno do jogador, turno dos inimigos e atualização
     * dos efeitos ativos.
     */
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

        for (Efeito efeito : this.heroi.getEfeitos()) {
            this.adicionarAoHistorico('U', this.heroi, efeito.getAcumulos(), efeito);
        }
        for (Inimigo inimigo : this.inimigos) {
            for (Efeito efeito : inimigo.getEfeitos()) {
                this.adicionarAoHistorico('U', inimigo, efeito.getAcumulos(), efeito);
            }
        }

        notificarEvento(Evento.FimDoRound);
    }

    /**
     * Inicia e controla o fluxo completo de uma batalha.
     *
     * O método executa rounds sucessivos até que uma das condições de término
     * seja atendida: o herói ser derrotado ou todos os inimigos serem eliminados.
     */
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

    /**
     * Permite ao jogador escolher um inimigo válido para interação durante o turno.
     * 
     * O método exibe na interface as opções de inimigos disponíveis e solicita
     * uma escolha ao jogador. A seleção é validada, garantindo que esteja dentro
     * dos limites e que o inimigo escolhido esteja vivo.
     *
     * @return o inimigo escolhido pelo jogador, ou {@code null} caso a seleção seja
     *         cancelada.
     */
    public Inimigo escolherUmInimigo() {
        boolean escolhaEhValida = false;
        int escolhaDeCancelar = inimigos.size() + 1;
        menu.clearScreen();
        menu.status(this, maoDoJogador, energia, energiaMaxima);
        while (!escolhaEhValida) {
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
            if (inimigos.get(escolhaPlayer - 1).estaVivo()) {
                escolhaEhValida = true;
                return inimigos.get(escolhaPlayer - 1);
            } else if (!inimigos.get(escolhaPlayer - 1).estaVivo()) {
                menu.clearScreen();
                menu.status(this, maoDoJogador, energia, energiaMaxima);
                menu.inimigoEstaMorto();
            }
        }
        return null;
    }

    /**
     * Notifica todas as entidades em jogo sobre a ocorrência de um evento.
     *
     * O método percorre a lista de entidades ativas e delega a cada uma delas
     * a responsabilidade de atualizar seus efeitos com base no evento ocorrido.
     *
     * @param eventoOcorrido o evento que será propagado para todas as entidades em
     *                       jogo.
     */
    public void notificarEvento(Evento eventoOcorrido) {
        for (Entidade entidade : entidadesEmJogo) {
            entidade.notificarSeusEfeitos(eventoOcorrido);
        }
    }

    /**
     * Adiciona uma ação ao histórico do jogo envolvendo um emissor e um receptor.
     *
     * O método gera uma mensagem descritiva da ação realizada, com base nos
     * parâmetros fornecidos, e a armazena no histórico de ações.
     *
     * @param acao              identificador da ação realizada.
     * @param emissor           entidade que executou a ação.
     * @param receptor          entidade que recebeu a ação.
     * @param intensidadeDaAcao valor numérico associado à ação.
     */
    public void adicionarAoHistorico(char acao, Entidade emissor, Entidade receptor, int intensidadeDaAcao) {
        String mensagemDaAcao = menu.criarMensagemDeAcao(acao, emissor, receptor, intensidadeDaAcao);
        this.historicoDeAcoes.add(mensagemDaAcao);
    }

    /**
     * Adiciona uma ação ao histórico do jogo relacionada a um efeito aplicado
     * sobre uma entidade.
     *
     * O método gera uma mensagem descritiva da aplicação ou atualização de um
     * efeito e a armazena no histórico de ações.
     *
     * @param acao              identificador da ação realizada.
     * @param receptor          entidade afetada pelo efeito.
     * @param intensidadeDaAcao valor numérico associado ao efeito.
     * @param efeito            efeito aplicado ou atualizado na entidade.
     */
    public void adicionarAoHistorico(char acao, Entidade receptor, int intensidadeDaAcao,
            Efeito efeito) {
        String mensagemDaAcao = menu.criarMensagemDeAcao(acao, receptor, intensidadeDaAcao, efeito);
        this.historicoDeAcoes.add(mensagemDaAcao);
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

    /**
     * Executa o turno de todos os inimigos vivos.
     *
     * O método percorre a lista de inimigos e, para cada inimigo que ainda está
     * vivo, realiza as ações correspondentes ao seu turno.
     */
    private void turnoDosInimigos() {
        for (Inimigo inimigo : inimigos) {
            if (inimigo.estaVivo()) {
                inimigo.setarEscudo(0);
                inimigo.agir(this);
            }
        }
    }

    /**
     * Verifica se todos os inimigos foram derrotados.
     *
     * @return {@code true} se todos os inimigos estiverem mortos; {@code false}
     *         caso contrário.
     */
    private boolean todosInimigosMortos() {
        for (Inimigo inimigo : inimigos) {
            if (inimigo.estaVivo()) {
                return false;
            }
        }
        return true;
    }
}
