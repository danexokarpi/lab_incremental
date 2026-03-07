public class Heroi {
    private String nome;
    private int vidaMaxima;
    private int escudo;
    private int vida;
    

    public Heroi (String nome, int vidaMaxima, int escudo){
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.escudo = escudo;
        this.vida = vidaMaxima;
    }

    // Sistema de dano verdadeiro, basicamente reduz o escudo do dano e aplica o dano absoluto na vida do heroi caso o escudo for menor
    public void receberDano (int dano){
        int dano_verdadeiro = escudo - dano;
        if (dano_verdadeiro < 0){
            vida -= Math.abs(dano_verdadeiro);
            escudo = 0;
        }else if(dano_verdadeiro == 0){
            escudo = 0;
        }else{
            escudo = dano_verdadeiro;
        }
        
    }
    
    public void receberEscudo (int escudoRecebido){
        escudo += escudoRecebido;
    }

    public void setEscudo (int escudoDefinido) {
        escudo = escudoDefinido;
    }

    public boolean estaVivo(){
        if (vida <= 0){
            return false;
        } else {
            return true;
        }
    }

    public String getNome() {
        return nome;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getEscudo() {
        return escudo;
    }

    public int getVida() {
        return vida;
    }

}

    