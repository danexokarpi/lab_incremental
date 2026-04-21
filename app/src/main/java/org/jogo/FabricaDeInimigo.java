package org.jogo;

import java.util.ArrayList;
import java.util.Random;

public class FabricaDeInimigo {
    private static final int quantidadeDeInimigosDisponiveis = 2;

    public static Inimigo criaInimigoAleatorio() {
        Random random = new Random();
        int randomInteger = random.nextInt(quantidadeDeInimigosDisponiveis);
        Inimigo inimigo = null;
        switch (randomInteger) {
            case 0:
                inimigo = new Inimigo("Escorpião Gigante", 20, 0, 4, 0, 2, new FabricaDeEfeito("veneno", 3),
                        new char[] { 'A', 'E', 'U' },
                        "" + //
                                "                          \n" + //
                                "              .++%#+-     \n" + //
                                "              *+    *.    \n" + //
                                "                    .%    \n" + //
                                "                   .*=    \n" + //
                                "             .-.*-.+*     \n" + //
                                "         +* *@*.--+*%.    \n" + //
                                "     .:+. ..-:-+%#%#  #   \n" + //
                                "    .* + #:*++#%@:. *  =. \n" + //
                                "  -*        .+. : =  *    \n" + //
                                "           .----= .       \n" + //
                                "         =-.++. -         \n" + //
                                "       .=++               \n" + //
                                "                          \n" + //
                                "");
                break;
            case 1:
                inimigo = new Inimigo("Barata Radioativa", 10, 0, 2, 0, 2, new FabricaDeEfeito(null, 0),
                        new char[] { 'A', 'E' },
                        "" + //
                                "        - @.                     \n" + //
                                "            %*@+=:$$ %           \n" + //
                                "               @@ *@@@@%         \n" + //
                                "             %    %#     =@      \n" + //
                                "            @      .       $     \n");
                break;
        }
        return inimigo;
    }

    public static ArrayList<Inimigo> criaListaDeInimigos(int tamanhoDaLista) {
        ArrayList<Inimigo> listaDeInimigos = new ArrayList<Inimigo>(tamanhoDaLista);
        for (int i = 0; i < tamanhoDaLista; i++) {
            listaDeInimigos.add(criaInimigoAleatorio());
        }
        return listaDeInimigos;

    }
}
