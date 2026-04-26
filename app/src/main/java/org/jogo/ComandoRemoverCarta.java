package org.jogo;

import java.util.ArrayList;

public class ComandoRemoverCarta implements ComandoLoja {
    private Heroi heroi;
    private int indexCartaInventario = -1; 
    private int precoRemocao = 10;

    public ComandoRemoverCarta(Heroi heroi) {
        this.heroi = heroi;
    }

    public void setarIndexCarta(int index) {
        this.indexCartaInventario = index;
    }

    
    public boolean podeExecutar() {
        return heroi.getOuro() >= precoRemocao && !heroi.getInventario().isEmpty();
    }

    public void executar() {
        if (indexCartaInventario != -1 && podeExecutar()) {
            heroi.alterarOuro(-precoRemocao);
            heroi.removerCartaDoInventario(indexCartaInventario);
        }
    }
}
