package org.jogo;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;

public class Loja extends Evento {
    private ArrayList<Carta> inventario;
    private Menu menu;
    private ArrayList<Carta> estoque = new ArrayList<Carta>();
    private FabricaDeCartas fabricaDeCartas = new FabricaDeCartas();
    private Heroi heroi;

    public Loja(Heroi heroi, Menu menu){
        this.heroi = heroi;
        this.inventario = heroi.getInventario();
        this.menu = menu;
        setGanhou(true);
    }

    public void iniciar() {
        for(int i = 0; i < 5; i++){
            estoque.add(fabricaDeCartas.getCartaAleatoria());
        }
        escolherOpcao(estoque);

    }
    public void escolherOpcao(ArrayList<Carta> estoque){
        int cursor = 0;
        String tipoDeAviso = "";
        boolean confirmou = false;
        while(!confirmou){
            int escolhaRemoverCarta = estoque.size();
            int escolhaSairDaLoja = estoque.size() + 1;
            menu.limparDesenho();
            menu.desenharLoja(estoque, cursor, tipoDeAviso, heroi.getOuro());
            menu.aplicarDesenho();
            KeyStroke key = menu.receberInputTeclado();

            if (key.getKeyType() == KeyType.ArrowDown) {
                cursor++;
                if (cursor > escolhaSairDaLoja) {
                    cursor = 0;
                }
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                cursor--;
                if (cursor < 0) {
                    cursor = escolhaSairDaLoja;
                }
            } else if (key.getKeyType() == KeyType.Enter) {
                if (cursor == escolhaSairDaLoja) {
                    confirmou = true;
                    continue;
                } else if (cursor == escolhaRemoverCarta) {
                    ComandoRemoverCarta comandoRemover = new ComandoRemoverCarta(heroi);
                    if (comandoRemover.podeExecutar()){
                        comandoRemover.setarIndexCarta(escolherCartaParaRemover());
                        comandoRemover.executar();
                    }else{
                        tipoDeAviso = "ouroInsuficiente";
                    }
                } else {
                    ComandoComprarCarta comandoComprar = new ComandoComprarCarta(heroi, cursor, estoque);
                    if (comandoComprar.podeExecutar()){
                        comandoComprar.executar();
                    }else{
                        tipoDeAviso = "ouroInsuficiente";
                    }
                }
            }   
        }
    }

    public int escolherCartaParaRemover(){
        int cursor = 0;
        int escolhaSairRemocao = inventario.size();
        while(true){
            menu.limparDesenho();
            menu.desenharRemocaoCartas(inventario, cursor);
            menu.aplicarDesenho();

            KeyStroke key = menu.receberInputTeclado();

            if (key.getKeyType() == KeyType.ArrowRight) {
                cursor++;
                if (cursor > escolhaSairRemocao) {
                    cursor = 0;
                }
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                cursor--;
                if (cursor < 0) {
                    cursor = escolhaSairRemocao;
                }
            } else if (key.getKeyType() == KeyType.Enter) {
                if (cursor == escolhaSairRemocao) {
                    return -1;
                }else{
                    return cursor;
                }
            }
        }
    }
}

