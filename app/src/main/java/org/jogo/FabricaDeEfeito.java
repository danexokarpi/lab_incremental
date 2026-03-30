package org.jogo;

public class FabricaDeEfeito {
    private int acumulos;
    private String nomeDoEfeito;

    public FabricaDeEfeito(String nomeDoEfeito, int acumulos){
        this.nomeDoEfeito = nomeDoEfeito;
        this.acumulos = acumulos;
    }

    public Efeito criarEfeito(){
        return switch(nomeDoEfeito){
            case "veneno" -> new EfeitoVeneno(acumulos);
            case "regeneracao" -> new EfeitoRegeneracao(acumulos);
            default -> null;
        };
    }

}
