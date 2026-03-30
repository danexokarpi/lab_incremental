package org.jogo;

import java.util.Random;

public class Inimigo extends Entidade {
    private static Random random = new Random();
    private int dano;
    private int cura;
    private int escudoAoProteger;
    private char[] listaDeAcoes;
    private char proximaAcao;
    private Entidade proximoAlvo;
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
                atacar(proximoAlvo);
                break;
            case 'C':
                proximoAlvo.curar(cura);
                break;
            case 'E':
                proximoAlvo.receberEscudo(escudoAoProteger);
                break;
            case 'U':
                proximoAlvo.aplicarEfeito(efeitoUtilizavel);
                break;
        }

        proximaAcao = listaDeAcoes[random.nextInt(listaDeAcoes.length)];
    }

    public String imprimirProxAcao(Tabuleiro tabuleiro) {
        this.proximoAlvo = acharAlvoValido(tabuleiro);
        switch (proximaAcao) {
            case 'A':
                return "Atacar: " + this.dano;
            case 'C':
                return "Curar: " + this.cura;
            case 'E':
                return "Proteger-se: " + this.escudoAoProteger;
            case 'U':
                return "Causar " + this.efeitoUtilizavel.getNome() + this.efeitoUtilizavel.getAcumulos() + " em " + proximoAlvo.getNome();
            default:
                return "";
        }
    }
    private Entidade acharAlvoValido(Tabuleiro tabuleiro){
        if (proximaAcao == 'U'){
            if (efeitoUtilizavel.getTipoDeEfeito() == "Buff"){
                return tabuleiro.getInimigo();
            }else if (efeitoUtilizavel.getTipoDeEfeito() == "Debuff"){
                return tabuleiro.getHeroi();
            }
        }else if (proximaAcao == 'A'){
            return tabuleiro.getHeroi();
        }else if (proximaAcao == 'E'){
            return this;
        }else if (proximaAcao == 'A'){
            return this;
        }
        
        return null;
        
}

    public void atacar(Entidade alvo){
        alvo.receberDano(this.dano);
    }

    public int getDano() {
        return this.dano;
    }

}
