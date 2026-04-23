package org.jogo;

/**
 * Representa um efeito que pode ser aplicado a uma entidade em batalha.
 *
 * Cada efeito possui um nome, um tipo (Buff ou Debuff), uma quantidade de
 * acumulos (intensidade ou pilhas do efeito), uma descrição e um estado
 * ativo/inativo. A classe é abstrata e obriga subclasses a implementar a
 * forma como o efeito reage a eventos durante a partida.
 *
 */
public abstract class Efeito {
    private String nome;
    private String tipoDeEfeito;
    private Entidade dono;
    private int acumulos;
    private boolean ativo;
    private String descricao;

    /**
     * Construtor protegido para inicializar um efeito com parâmetros específicos.
     *
     * @param nome         Nome do efeito.
     * @param tipoDeEfeito Tipo do efeito ("Buff" ou "Debuff").
     * @param acumulos     Quantidade inicial de acumulos.
     * @param descricao    Descrição textual do efeito.
     */
    protected Efeito(String nome, String tipoDeEfeito, int acumulos, String descricao) {
        this.nome = nome;
        this.tipoDeEfeito = tipoDeEfeito;
        this.dono = null;
        this.acumulos = acumulos;
        this.descricao = descricao;
        this.ativo = acumulos > 0;
    }

    /**
     * Retorna uma representação textual resumida do efeito, incluindo nome e
     * número de acumulos.
     *
     * @return String no formato "[ Nome(acumulosx) ]".
     */
    public String getString() {
        return "[ " + nome + "(" + acumulos + "x) ]";
    }

    /** @return Nome do efeito. */
    public String getNome() {
        return this.nome;
    }

    /** @return Quantidade de acumulos do efeito. */
    public int getAcumulos() {
        return this.acumulos;
    }

    /** @return Tipo do efeito ("Buff" ou "Debuff"). */
    public String getTipoDeEfeito() {
        return this.tipoDeEfeito;
    }

    /** @return Descrição textual do efeito. */
    public String getDescricao() {
        return descricao;
    }

    /** @return Entidade que possui o efeito. */
    public Entidade getDono() {
        return this.dono;
    }

    /**
     * Associa o efeito a uma entidade.
     *
     * @param dono Entidade que será proprietária do efeito.
     */
    public void setDono(Entidade dono) {
        this.dono = dono;
    }

    /**
     * Adiciona acumulos ao efeito existente, aumentando sua intensidade.
     *
     * @param acumulosRecebidos Quantidade de acumulos a somar.
     */
    public void somaAcumulos(int acumulosRecebidos) {
        this.acumulos += acumulosRecebidos;
    }

    /**
     * Verifica se o efeito ainda está ativo.
     *
     * @return {@code true} se ativo, {@code false} caso contrário.
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Reduz o número de acumulos do efeito em 1.
     *
     * Caso os acumulos cheguem a 0, o efeito é desativado.
     */
    public void subtrairAcumulo() {
        if (acumulos > 0) {
            acumulos--;
            if (acumulos == 0) {
                this.ativo = false;
            }
        }
    }

    /**
     * Método abstrato que deve ser implementado por cada efeito concreto.
     *
     * Define como o efeito reage quando um evento ocorre no jogo.
     *
     * @param eventoOcorrido Evento que ocorreu na batalha.
     */
    public abstract void receberNotificacao(EventoDeBatalha eventoOcorrido);
}
