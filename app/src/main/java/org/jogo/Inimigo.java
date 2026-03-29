package org.jogo;

import java.util.Random;

public class Inimigo extends Entidade {
    private static Random random = new Random();
    private int dano;
    private int cura;
    private int escudoAoProteger;
    private char[] listaDeAcoes;
    private char proximaAcao;
    private Efeito efeitoUtilizavel; 

    public Inimigo(String nome, int vidaMaxima, int escudo, int dano, int cura, int escudoAoProteger,
            Efeito efeitoUtilizavel ,char[] listaDeAcoes) {
        super(nome, vidaMaxima, escudo);
        this.dano = dano;
        this.escudoAoProteger = escudoAoProteger;
        this.efeitoUtilizavel = efeitoUtilizavel;
        this.listaDeAcoes = listaDeAcoes;
        this.cura = cura;
        this.proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    public void agir(Tabuleiro tabuleiro) {
        switch (proximaAcao) {
            case 'A':
                atacar(tabuleiro.getHeroi());
                break;
            case 'C':
                this.curar(cura);
                break;
            case 'E':
                this.receberEscudo(escudoAoProteger);
                break;
            case 'U':
                acharEntidadeValida(tabuleiro).aplicarEfeito(efeitoUtilizavel);
                break;
        }

        proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    public String imprimirProxAcao() {
        switch (proximaAcao) {
            case 'A':
                return "Atacar: " + this.dano;
            case 'C':
                return "Curar: " + this.cura;
            case 'E':
                return "Proteger-se: " + this.escudoAoProteger;
            default:
                return "";
        }
    }
    private Entidade acharEntidadeValida(Tabuleiro tabuleiro){
        if (efeitoUtilizavel.getTipoDeEfeito() == "Buff"){
            return tabuleiro.getInimigo();
        }else if (efeitoUtilizavel.getTipoDeEfeito() == "Debuff"){
            return tabuleiro.getHeroi();
        }else{
            return null;
        }
    }


    public void atacar(Entidade alvo){
        alvo.receberDano(this.dano);
    }

    public int getDano() {
        return this.dano;
    }

}
