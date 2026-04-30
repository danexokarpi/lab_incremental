package org.jogo;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;

public class Fogueira extends Evento {
    private Heroi heroi;
    private ArrayList<Carta> inventario;
    private Menu menu;
    private FabricaDeCartas fabricaDeCartas;
    private Carta cartaDeOpcao;

    public Fogueira(Heroi heroi, Menu menu) {
        this.heroi = heroi;
        this.inventario = heroi.getInventario();
        this.menu = menu;
        this.fabricaDeCartas = new FabricaDeCartas();
        this.cartaDeOpcao = fabricaDeCartas.getCartaEspecialAleatoria();
        setGanhou(true);
    }

    public void iniciar() {
        int cursor = 0;
        int escolhaDescansar = 0;
        int escolhaPegarCarta = 1;
        boolean confirmou = false;
        while (!confirmou) {
            menu.limparDesenho();
            menu.desenharFogueira(cursor, cartaDeOpcao);
            menu.aplicarDesenho();
            KeyStroke key = menu.receberInputTeclado();

            if (key.getKeyType() == KeyType.ArrowRight) {
                cursor++;
                if (cursor > escolhaPegarCarta) {
                    cursor = 0;
                }
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                cursor--;
                if (cursor < 0) {
                    cursor = escolhaPegarCarta;
                }
            } else if (key.getKeyType() == KeyType.Enter) {
                confirmou = true;
                if (cursor == escolhaDescansar) {
                    ComandoRegenerar comandoRegenerar = new ComandoRegenerar(heroi);
                    comandoRegenerar.iniciar();
                } else {
                    ComandoPegarCarta comandoPegarCarta = new ComandoPegarCarta(heroi, cartaDeOpcao);
                    comandoPegarCarta.iniciar();
                }
            }
        }
    }
}
