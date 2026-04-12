package org.jogo;

import java.util.Random;
import java.util.ArrayList;

/**
 * Representa um inimigo controlado pelo jogo.
 *
 * Herda de {@link Entidade} e possui atributos específicos como dano,
 * cura, escudo e lista de ações possíveis. A cada turno, o inimigo escolhe
 * uma ação aleatória de sua lista de ações e a aplica a um alvo válido.
 */
public class Inimigo extends Entidade {
    private static Random random = new Random();
    private int dano;
    private int cura;
    private int escudoAoProteger;
    private char[] listaDeAcoes;
    private char proximaAcao;
    private Entidade proximoAlvo;
    private FabricaDeEfeito fabricaDeEfeito;

    /**
     * Cria um novo inimigo com atributos específicos e lista de ações possíveis.
     *
     * @param nome             nome do inimigo.
     * @param vidaMaxima       quantidade máxima de vida do inimigo.
     * @param escudo           quantidade inicial de escudo do inimigo.
     * @param dano             valor de dano que o inimigo causa ao atacar.
     * @param cura             valor de cura que o inimigo aplica quando escolhe
     *                         curar.
     * @param escudoAoProteger valor de escudo aplicado quando o inimigo escolhe se
     *                         proteger.
     * @param fabricaDeEfeito  fábrica responsável por criar efeitos (buff ou
     *                         debuff).
     * @param listaDeAcoes     array de caracteres representando as ações possíveis
     *                         do inimigo.
     */
    public Inimigo(String nome, int vidaMaxima, int escudo, int dano, int cura, int escudoAoProteger,
            FabricaDeEfeito fabricaDeEfeito, char[] listaDeAcoes) {
        super(nome, vidaMaxima, escudo);
        this.dano = dano;
        this.escudoAoProteger = escudoAoProteger;
        this.fabricaDeEfeito = fabricaDeEfeito;
        this.listaDeAcoes = listaDeAcoes;
        this.cura = cura;
        this.proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    /**
     * Executa a ação escolhida pelo inimigo durante seu turno.
     *
     * Dependendo da ação, o inimigo pode atacar, curar, se proteger ou aplicar um
     * efeito.
     * Ao final, seleciona aleatoriamente a próxima ação.
     *
     * @param batalha referência à batalha atual para registrar ações e obter
     *                alvos.
     */
    public void agir(Batalha batalha) {
        switch (proximaAcao) {
            case 'A':
                atacar(proximoAlvo);
                batalha.adicionarAoHistorico('A', this, proximoAlvo, dano);
                break;
            case 'C':
                proximoAlvo.curar(cura);
                batalha.adicionarAoHistorico('C', this, proximoAlvo, cura);
                break;
            case 'E':
                proximoAlvo.receberEscudo(escudoAoProteger);
                batalha.adicionarAoHistorico('E', this, proximoAlvo, escudoAoProteger);
                break;
            case 'U':
                Efeito efeito = fabricaDeEfeito.criarEfeito();
                proximoAlvo.aplicarEfeito(efeito);
                break;
        }
        proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    /**
     * Retorna uma descrição da próxima ação do inimigo.
     *
     * Antes de executar a ação, define o alvo válido e retorna uma string
     * descritiva da ação e do valor correspondente (dano, cura, escudo ou efeito).
     *
     * @param batalha referência à batalha atual para determinar o alvo da
     *                ação.
     * @return descrição textual da próxima ação do inimigo.
     */
    public String imprimirProxAcao(Batalha batalha) {
        this.proximoAlvo = acharAlvoValido(batalha);
        switch (proximaAcao) {
            case 'A':
                return "Atacar: " + this.dano;
            case 'C':
                return "Curar: " + this.cura;
            case 'E':
                return "Proteger-se: " + this.escudoAoProteger;
            case 'U':
                Efeito efeito = fabricaDeEfeito.criarEfeito();
                return "Causar " + efeito.getNome() + " " + efeito.getAcumulos() + " em " + proximoAlvo.getNome();
            default:
                return "";
        }
    }

    /**
     * Determina o alvo válido para a ação atual do inimigo.
     *
     * Para ações de dano ou debuff, o alvo geralmente é o herói. Para buffs, o alvo
     * é um inimigo aleatório. Para proteger-se e curar-se, o alvo é ele próprio.
     *
     * @param batalha referência à batalha para acessar o herói e inimigos.
     * @return entidade que será o alvo da ação, ou {@code null} se não houver alvo
     *         válido.
     */
    private Entidade acharAlvoValido(Batalha batalha) {
        if (proximaAcao == 'U') {
            Efeito efeito = fabricaDeEfeito.criarEfeito();
            if (efeito.getTipoDeEfeito().equals("Buff")) {
                ArrayList<Inimigo> inimigos = batalha.getInimigos();
                return inimigos.get(random.nextInt(inimigos.size()));
            } else if (efeito.getTipoDeEfeito().equals("Debuff")) {
                return batalha.getHeroi();
            }
        } else if (proximaAcao == 'A') {
            return batalha.getHeroi();
        } else if (proximaAcao == 'E' || proximaAcao == 'C') {
            return this;
        }
        return null;
    }

    /**
     * Aplica dano a uma entidade alvo.
     *
     * @param alvo entidade que receberá o dano.
     */
    public void atacar(Entidade alvo) {
        alvo.receberDano(this.dano);
    }

    /**
     * Retorna o valor de dano do inimigo.
     *
     * @return valor de dano.
     */
    public int getDano() {
        return this.dano;
    }

}
