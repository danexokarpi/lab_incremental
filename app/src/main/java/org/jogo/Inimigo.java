package org.jogo;

import java.util.Random;
import java.util.ArrayList;


public class Inimigo extends Entidade {
    private static Random random = new Random();
    private int dano;
    private int cura;
    private int escudoAoProteger;
    private char[] listaDeAcoes;
    private char proximaAcao;
    private Entidade proximoAlvo;
    private FabricaDeEfeito fabricaDeEfeito;
    

    public Inimigo(String nome, int vidaMaxima, int escudo, int dano, int cura, int escudoAoProteger,
            FabricaDeEfeito fabricaDeEfeito ,char[] listaDeAcoes) {
        super(nome, vidaMaxima, escudo);
        this.dano = dano;
        this.escudoAoProteger = escudoAoProteger;
        this.fabricaDeEfeito = fabricaDeEfeito;
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
                Efeito efeito = fabricaDeEfeito.criarEfeito();
                proximoAlvo.aplicarEfeito(efeito);
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
                Efeito efeito = fabricaDeEfeito.criarEfeito();
                return "Causar " + efeito.getNome() + " " + efeito.getAcumulos() + " em " + proximoAlvo.getNome();
            default:
                return "";
        }
    }
    private Entidade acharAlvoValido(Tabuleiro tabuleiro){
        if (proximaAcao == 'U'){
            Efeito efeito = fabricaDeEfeito.criarEfeito();
            if (efeito.getTipoDeEfeito() == "Buff"){
                ArrayList<Inimigo> inimigos = tabuleiro.getInimigos();
                return inimigos.get(random.nextInt(inimigos.size()));
            }else if (efeito.getTipoDeEfeito() == "Debuff"){
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
