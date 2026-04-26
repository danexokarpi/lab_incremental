package org.jogo;

import java.util.Random;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Escolha extends Evento {
    private Random random = new Random();
    private Menu menu;
    private Heroi heroi;

    public Escolha(Menu menu, Heroi heroi){
        this.menu = menu;
        this.heroi = heroi;
        setGanhou(true);
    }

    public void iniciar() {
        int indiceDaEscolha = random.nextInt(1);
        switch (indiceDaEscolha) {
            case 0:
                iniciarAcharAmbulancia();
                break;
            case 1:
                iniciarBBB(); // iniciar tipo de escolha 'BBB'
                break;
        }
    }

    public void iniciarAcharAmbulancia() {
        int cursor = 0;
        while(true){
            menu.limparDesenho();
            menu.desenharEventoAmbulancia(cursor);
            menu.aplicarDesenho();
            KeyStroke key = menu.receberInputTeclado();
            if(key.getKeyType() == KeyType.ArrowRight){
                cursor ++;
                if (cursor > 1){
                    cursor = 0;
                }
            }else if(key.getKeyType() == KeyType.ArrowLeft){
                cursor--;
                if (cursor < 0){
                    cursor = 1;
                }
            }else if (key.getKeyType() == KeyType.Enter){
                if (cursor == 0){
                    heroi.alterarVidaMaxima(15);
                }else if (cursor == 1){
                    heroi.alterarEnergiaMaxima(1);
                }
                break;
            }
        }
        
    }

    public void iniciarBBB() {

    }
}
