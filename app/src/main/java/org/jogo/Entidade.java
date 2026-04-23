package org.jogo;

import java.util.ArrayList;

/**
 * Representa uma entidade genérica do jogo, podendo ser herói ou inimigo.
 *
 * Possui atributos básicos como vida, escudo, efeitos ativos e métodos para
 * receber dano, cura, escudo e efeitos. Também gerencia notificações de eventos
 * que podem alterar seus efeitos.
 */
public abstract class Entidade {
    private String nome;
    private int vida;
    private int vidaMaxima;
    private int escudo;
    private String ascii;
    private transient ArrayList<Efeito> efeitos;

    protected Entidade() {
    }

    /**
     * Cria uma nova entidade com vida máxima e escudo inicial.
     *
     * @param nome       nome da entidade.
     * @param vidaMaxima quantidade máxima de vida da entidade.
     * @param escudo     quantidade inicial de escudo da entidade.
     */
    public Entidade(String nome, int vidaMaxima, int escudo, String ascci) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.escudo = escudo;
        this.vida = vidaMaxima;
        this.ascii = ascci;
        this.efeitos = new ArrayList<>();
    }

    /**
     * Aplica dano à entidade considerando o escudo.
     *
     * O dano reduz primeiro o escudo. Caso o escudo não seja suficiente, o dano
     * restante é aplicado à vida da entidade. A vida nunca fica abaixo de zero.
     *
     * @param dano valor de dano a ser recebido.
     */
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

    public void receberDanoVerdadeiro(int dano) {
        vida -= dano;
        if (vida < 0) {
            vida = 0;
        }
    }

    /**
     * Cura a entidade, respeitando o limite de vida máxima.
     *
     * @param cura quantidade de vida a ser restaurada.
     */
    public void curar(int cura) {
        this.vida += cura;
        if (vida > vidaMaxima)
            vida = vidaMaxima;
    }

    /**
     * Adiciona escudo à entidade.
     *
     * @param escudoRecebido quantidade de escudo a ser adicionada.
     */
    public void receberEscudo(int escudoRecebido) {
        escudo += escudoRecebido;
    }

    /**
     * Define o valor de escudo da entidade.
     *
     * @param escudoDefinido valor a ser definido como escudo.
     */
    public void setarEscudo(int escudoDefinido) {
        escudo = escudoDefinido;
    }

    public void setarVida(int vida) {
        this.vida = vida;
    }

    /**
     * Retorna um efeito já presente na entidade que tenha o mesmo nome do efeito
     * recebido.
     *
     * @param efeitoRecebido efeito a ser buscado.
     * @return efeito correspondente se encontrado, ou {@code null} caso contrário.
     */
    private Efeito getEfeito(Efeito efeitoRecebido) {
        for (Efeito efeitoPortado : this.efeitos) {
            if (efeitoPortado.getNome() == efeitoRecebido.getNome())
                return efeitoPortado;
        }
        return null;
    }

    /**
     * Aplica um efeito à entidade.
     *
     * Se a entidade já possuir um efeito com o mesmo nome, os acúmulos são somados.
     * Caso contrário, o efeito é adicionado à lista de efeitos ativos.
     *
     * @param efeitoRecebido efeito a ser aplicado.
     */
    public void aplicarEfeito(Efeito efeitoRecebido) {
        Efeito efeitoPortado = this.getEfeito(efeitoRecebido);
        if (estaVivo()) {
            if (efeitoPortado == null) {
                this.efeitos.add(efeitoRecebido);
                efeitoRecebido.setDono(this);
            } else {
                efeitoPortado.somaAcumulos(efeitoRecebido.getAcumulos());
            }
        }

    }

    /**
     * Notifica todos os efeitos da entidade viva sobre a ocorrência de um evento.
     * 
     * Os efeitos podem se modificar ou expirar em função do evento. Efeitos que não
     * estão mais ativos são removidos da lista.
     *
     * @param eventoOcorrido evento que foi disparado no jogo.
     */
    public void notificarSeusEfeitos(EventoDeBatalha eventoOcorrido) {
        if (estaVivo()) {
            for (int i = efeitos.size() - 1; i >= 0; i--) {
                efeitos.get(i).receberNotificacao(eventoOcorrido);
                if (!efeitos.get(i).isAtivo()) {
                    efeitos.remove(i);
                }
            }
        } else if (!estaVivo()) {
            limparEfeitos();
        }
    }

    /**
     * Remove todos os efeitos da entidade.
     */
    public void limparEfeitos() {
        efeitos.clear();
    }

    /**
     * Verifica se a entidade ainda está viva.
     *
     * @return {@code true} se a vida da entidade for maior que zero, {@code false}
     *         caso contrário.
     */
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

    public ArrayList<Efeito> getEfeitos() {
        if (this.efeitos == null) {
            this.efeitos = new ArrayList<>();
        }
        return this.efeitos;
    }

    public String getAscii() {
        return ascii;
    }
}
