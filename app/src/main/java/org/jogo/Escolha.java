package org.jogo;

import java.util.Random;

public class Escolha extends Evento {
    private Random random = new Random();
    private boolean ganhou;

    public void iniciar() {
        int indiceDaEscolha = random.nextInt(2);
        switch (indiceDaEscolha) {
            case 0:
                iniciarAAA(); // iniciar tipo de escolha 'AAA'
                break;
            case 1:
                iniciarBBB(); // iniciar tipo de escolha 'BBB'
                break;
        }
    }

    public void iniciarAAA() {

    }

    public void iniciarBBB() {

    }
}
