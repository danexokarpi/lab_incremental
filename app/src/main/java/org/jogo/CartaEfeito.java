public class CartaEfeito extends Carta{
    private Efeito efeito;
    public CartaEfeito(String Nome, String descricao, int custo, Efeito efeito){
        super(nome, descricao, custo);
        this.efeito = efeito;
    }


    public usar(Tabuleiro tabuleiro){
        if (efeito.getTipoDeEfeito() == "Buff"){
            tabuleiro.getHeroi().aplicarEfeito(efeito);
        }else if (efeito.getTipoDeEfeito() == "Debuff"){
            tabuleiro.getInimigo().aplicarEfeito(efeito);
        }
    }
}  
