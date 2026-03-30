package org.jogo;

import java.util.ArrayList;

public abstract class Entidade {
    private String nome;
    private int vida;
    private int vidaMaxima;
    private int escudo;
    private ArrayList<Efeito> efeitos;

    public Entidade(String nome, int vidaMaxima, int escudo) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.escudo = escudo;
        this.vida = vidaMaxima;
        this.efeitos = new ArrayList<Efeito>();
    }

    // Sistema de dano verdadeiro. Basicamente reduz o escudo do dano e aplica
    // o dano absoluto na vida do heroi caso o escudo for menor
    public void receberDano(int dano) {
        int dano_verdadeiro = escudo - dano;
        if (dano_verdadeiro < 0) {
            vida -= Math.abs(dano_verdadeiro);
            if (vida <= 0)
                vida = 0;
            escudo = 0;
        } else if (dano_verdadeiro == 0) {
            escudo = 0;
        } else {
            escudo = dano_verdadeiro;
        }
    }

    public void curar(int cura) {
        this.vida += cura;
        if (vida > vidaMaxima)
            vida = vidaMaxima;
    }

    public void receberEscudo(int escudoRecebido) {
        escudo += escudoRecebido;
    }

    public void setarEscudo(int escudoDefinido) {
        escudo = escudoDefinido;
    }

    private Efeito getEffect(Efeito efeitoRecebido) {
        for (Efeito efeitoPortado : this.efeitos) {
            if (efeitoPortado.getNome() == efeitoRecebido.getNome())
                return efeitoPortado;
        }
        return null;
    }

    public void aplicarEfeito(Efeito efeitoRecebido) {
        Efeito efeitoPortado = this.getEffect(efeitoRecebido);
        if (efeitoPortado == null) {
            this.efeitos.add(efeitoRecebido);
            efeitoRecebido.setDono(this);
        } else {
            efeitoPortado.somaAcumulos(efeitoRecebido.getAcumulos());
        }
    }

    public void notificarSeusEfeitos(Evento eventoOcorrido){
        for (int i = efeitos.size()-1; i > 0; i --){
            efeitos.get(i).receberNotificacao(eventoOcorrido);
            if (!efeitos.get(i).isAtivo()){
                efeitos.remove(i);
            }
        }
    }

    public void limparEfeitos(){
        for (int i = efeitos.size()-1; i > 0; i --){
                efeitos.remove(i);
            }
    }

    public boolean estaVivo() {
        if (vida <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getNome() {
        return nome;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getEscudo() {
        return escudo;
    }

    public int getVida() {
        return vida;
    }

}
