package org.jogo;

/**
 * Representa os diferentes tipos de eventos que podem ocorrer durante uma
 * batalha.
 *
 * Estes eventos são utilizados para notificar efeitos ativos nas entidades,
 * permitindo que eles reajam de acordo com o tipo de evento.
 */
public enum EventoDeBatalha {
    FimDoRound,
    FimDoTurnoDoJogador,
    FimDoTurnoDeUmaEntidade,
    AtaqueDeEntidade,
    EntidadeRecebeDano
}
