package org.jogo;

/**
 * Fábrica de efeitos que permite criar instâncias de efeitos específicos de
 * forma padronizada.
 */
public class FabricaDeEfeito {
    private int acumulos;
    private String nomeDoEfeito;

    /**
     * Construtor da fábrica.
     *
     * @param nomeDoEfeito Nome do efeito que será criado.
     * @param acumulos     Quantidade inicial de acumulos do efeito.
     */
    public FabricaDeEfeito(String nomeDoEfeito, int acumulos) {
        this.nomeDoEfeito = nomeDoEfeito;
        this.acumulos = acumulos;
    }

    /**
     * Cria e retorna uma instância concreta de {@link Efeito} de acordo com o nome
     * definido na fábrica.
     *
     * @return Uma instância de {@link Efeito} correspondente ao nome do efeito.
     * @throws IllegalArgumentException Se o nome do efeito não for reconhecido.
     */
    public Efeito criarEfeito() {
        return switch (nomeDoEfeito.toLowerCase()) {
            case "veneno" -> new EfeitoVeneno(acumulos);
            case "regeneracao" -> new EfeitoRegeneracao(acumulos);
            default -> throw new IllegalArgumentException("Efeito desconhecido: " + nomeDoEfeito);
        };
    }
}
