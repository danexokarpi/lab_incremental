package org.jogo;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


/**
 * Classe responsável pela interação com o jogador via console.
 *
 * O Menu centraliza toda a lógica de exibição de informações do jogo,
 * leitura de entradas do usuário e geração de mensagens relacionadas às ações
 * realizadas durante a partida.
 */
public class Menu {
    private static Scanner scan = new Scanner(System.in);
    private Screen screen;

    private static final int xHeroi = 5;
    private static final int yStatus = 2;
    private static final int xLogs = 5;
    private static final int yLogs = 20;
    private static final int xAviso = 5;
    private static final int yAviso = 34;
    private static final int xEscolhas = 5;
    private static final int yEscolhas = 35;

    public void incializarTela(){
        try{
            DefaultTerminalFactory fabrica = new DefaultTerminalFactory();
            this.screen = fabrica.createScreen();
            this.screen.startScreen();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    private void printarEntidade(Screen screen, int xBase, int yBase, Entidade entidade, Tabuleiro tabuleiro){
        TextGraphics textG = screen.newTextGraphics();
        String[] linhasAscci = entidade.getAscci().split("\n");

        int larguraAscci = 0;
        for (String linha : linhasAscci){
            larguraAscci = Math.max(larguraAscci, linha.length());
        }
        
        String nome = entidade.getNome();
        String status = "Vida: " + entidade.getVida() + "(escudo " + entidade.getEscudo() + ")";
        
        textG.putString(xBase + (larguraAscci/2) - (nome.length() / 2), yBase, nome);
        
        textG.putString(xBase + (larguraAscci / 2) - (status.length() / 2), yBase + 1, status);

        if(!entidade.getEfeitos().isEmpty()){
            StringBuilder sb = new StringBuilder();
            ArrayList<Efeito> efeitos = entidade.getEfeitos();
            for(int i = 0; i < efeitos.size(); i++){
                Efeito efeito = efeitos.get(i);
                sb.append(efeito.getNome()).append(" ").append(efeito.getAcumulos());

                if(i < efeitos.size() - 1){
                    sb.append(" | ");
                }
            }
            String linhaEfeitos = sb.toString();
            textG.putString(xBase + (larguraAscci / 2) - (linhaEfeitos.length() / 2), yBase + 2, linhaEfeitos);
        }

        if(entidade instanceof Inimigo inimigo){
            String proxAcao = "Irá " + inimigo.getProxAcao(tabuleiro);
            textG.putString(xBase + (larguraAscci / 2) - (proxAcao.length()), yBase + 3, status);
        }

        for(int i = 0; i < linhasAscci.length; i++){
            textG.putString(xBase, yBase + 4 + i, linhasAscci[i]);
        }

    }
    public void desenharStatus(Tabuleiro tabuleiro, int energia, int energiaMaxima){
        TextGraphics textG = screen.newTextGraphics();
            
        int incremento = 0;
        printarEntidade(screen, xHeroi, yStatus, tabuleiro.getHeroi(), tabuleiro);

        for(Inimigo inimigo : tabuleiro.getInimigos()){
            incremento += 15;
            printarEntidade(screen, xHeroi + incremento, yStatus, inimigo, tabuleiro);
        }

        String stringEnergia = energia + "/" + energiaMaxima + " de energia disponível" ;

        textG.putString(xHeroi, yStatus + 16, stringEnergia);
        
    }
    public void desenharLogs(ArrayList<String> historico){
        TextGraphics textG = screen.newTextGraphics();
        for(int i = 0; i < historico.size(); i++){
            textG.putString(xLogs, yLogs + i, historico.get(i));
        }
    }
    public void desenharAviso(String tipoAviso){
        TextGraphics textG = screen.newTextGraphics();
        switch(tipoAviso){
            case "energiaInsuficiente" :
                textG.putString(xAviso, yAviso, "ENERGIA INSUFICIENTE: a carta selecionada possui custo de energia superior ao nível de energia atual.");
            case "inimigoEstaMorto" :
                textG.putString(xAviso, yAviso, "INIMIGO JÁ ESTÁ MORTO: o inimigo selecionado já foi derrotado, essa ação não terá efeito");
            default:
        }
    }
    public void desenharSelecaoCartas(MaoDoJogador maoDoJogador, int posiçãoCursor){
        TextGraphics textG = screen.newTextGraphics();
        int espacamento = 5;
        for(int i = 0; i < maoDoJogador.getTamanho(); i++){
            Carta carta = maoDoJogador.getCarta(i);
            String nome = carta.getNome();
            String custoEfeitoAoE = carta.getEfeitoCustoAoE();
            String descricao = carta.getDescricao();
            int xEspacado = xEscolhas + espacamento;
            int yEspacado = yEscolhas + 1;
            textG.putString(xEspacado, yEscolhas, "Escolha sua ação:");
            if(posiçãoCursor == i){
                textG.setForegroundColor(TextColor.ANSI.GREEN);
                textG.putString(xEspacado, yEscolhas, "-> " + carta.getNome());
                desenharTextoCentralizado(textG, xEspacado, yEspacado, nome.length(), custoEfeitoAoE);
                desenharFrasesCentralizadas(textG, xEspacado, yEspacado + 1, nome.length(), descricao);
            }else{
                textG.setForegroundColor(TextColor.ANSI.WHITE);
                textG.putString(xEspacado, yEscolhas, "   " + carta.getNome());
                desenharTextoCentralizado(textG, xEspacado, yEspacado, nome.length(), custoEfeitoAoE);
                desenharFrasesCentralizadas(textG, xEspacado, yEspacado + 1, nome.length(), descricao);
            }
            espacamento += 5 ;
        }
        if(posiçãoCursor == maoDoJogador.getTamanho()){
            textG.setForegroundColor(TextColor.ANSI.GREEN);
            textG.putString(xEscolhas + espacamento, yEscolhas + 1, "Encerrar Turno");
        }else{
            textG.putString(xEscolhas + espacamento, yEscolhas + 1, "Encerrar Turno");
        }
        
        
    }

    public void desligarTela(){
        try{
            screen.stopScreen();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void aplicarDesenho(){
        try{
            screen.refresh();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void limparDesenho(){
        screen.clear();
    }

    public KeyStroke receberInputTeclado(){
        try{
            return screen.readInput();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        
    }





    /**
     * Exibe o estado atual da batalha no console.
     *
     * Mostra informações do herói, inimigos e energia disponível.
     *
     * @param tabuleiro     estado atual do jogo.
     * @param maoDoJogador  mão atual do jogador.
     * @param energia       energia atual disponível.
     * @param energiaMaxima energia máxima do jogador.
     */
    public void status(Tabuleiro tabuleiro, int energia, int energiaMaxima) {
        Heroi heroi = tabuleiro.getHeroi();
        ArrayList<Inimigo> inimigos = tabuleiro.getInimigos();
        System.out.printf("=-=\n");
        for (Efeito efeito : heroi.getEfeitos()){
            System.out.printf(efeito.getNome() + " " + efeito.getAcumulos() + " ");
        }
        System.out.printf("\n");
        System.out.printf("%s (%d/%d) (%d de escudo)\n", heroi.getNome(),
                heroi.getVida(), heroi.getVidaMaxima(), heroi.getEscudo());
        System.out.printf("\nvs\n");
        for (Inimigo inimigo : inimigos) {
            for (Efeito efeito : inimigo.getEfeitos()){
            System.out.printf(efeito.getNome() + " " + efeito.getAcumulos() + " ");
            }
            System.out.printf("\n");
            System.out.printf("%s (%d/%d) (%d de escudo)\n", inimigo.getNome(),
                    inimigo.getVida(), inimigo.getVidaMaxima(), inimigo.getEscudo());
            if (inimigo.estaVivo()) {
                System.out.printf("Irá %s\n\n", inimigo.getProxAcao(tabuleiro));
            }else{
                System.out.printf("Está morto.\n\n");
            }

        }
        System.out.printf("%d/%d de energia disponível\n", energia, energiaMaxima);
    }

    /**
     * Cria uma mensagem descritiva de uma ação entre duas entidades.
     *
     * @param acao              identificador da ação.
     * @param emissor           entidade que executou a ação.
     * @param receptor          entidade que recebeu a ação.
     * @param intensidadeDaAcao valor numérico da ação.
     * @return mensagem formatada descrevendo a ação.
     * @throws RuntimeException caso o tipo de ação seja inválido.
     */
    public String criarMensagemDeAcao(char acao, Entidade emissor, Entidade receptor, int intensidadeDaAcao) {
        switch (acao) {
            case 'A':
                return String.format("%s causou %d de dano a %s\n", emissor.getNome(),
                        intensidadeDaAcao, receptor.getNome());
            case 'E':
                return String.format("%s recebeu %d de escudo\n", receptor.getNome(),
                        intensidadeDaAcao);
            case 'U':
                return String.format("%s recebeu %d pontos de vida\n", receptor.getNome(),
                        intensidadeDaAcao);
            default:
                throw new RuntimeException(String.format("Ação do tipo '%c' inválida", acao));
        }
    }

    public List<String> quebrarTexto(String texto, int larguraMaxima) {
    List<String> linhas = new ArrayList<>();
    String[] palavras = texto.split(" ");
    StringBuilder linhaAtual = new StringBuilder();

    for (String palavra : palavras) {
        if (linhaAtual.length() + palavra.length() + 1 <= larguraMaxima) {
            if (linhaAtual.length() > 0) {
                linhaAtual.append(" ");
            }
            linhaAtual.append(palavra);
        } else {
            linhas.add(linhaAtual.toString());
            linhaAtual = new StringBuilder(palavra);
        }
    }
    if (linhaAtual.length() > 0) {
        linhas.add(linhaAtual.toString());
    }

    return linhas;
    }

    public void desenharTextoCentralizado(TextGraphics tg, int xBase, int yBase, int larguraTotal, String texto){
        int posicaoX = xBase + (larguraTotal / 2) - (texto.length() / 2);
        tg.putString(posicaoX, yBase, texto);
    }

    public void desenharFrasesCentralizadas(TextGraphics tg, int xBase, int yBase, int larguraTotal, String textoLongo){
        List<String> linhas = quebrarTexto(textoLongo, larguraTotal);
        for(int i = 0; i < linhas.size(); i++){
            desenharTextoCentralizado(tg, xBase, yBase + i, larguraTotal, linhas.get(i));
        }
    }
    
    /**
     * Cria uma mensagem descritiva baseada em um efeito aplicado a uma entidade.
     *
     * @param acao              identificador da ação.
     * @param receptor          entidade afetada.
     * @param intensidadeDaAcao intensidade do efeito.
     * @param efeitoCausado     efeito aplicado.
     * @return mensagem formatada descrevendo o efeito.
     * @throws RuntimeException caso o efeito seja inválido.
     */
    public String criarMensagemDeAcao(char acao, Entidade receptor, int intensidadeDaAcao, Efeito efeitoCausado) {
        if (efeitoCausado.getNome().equals("Veneno")) {
            return String.format("%s recebeu %d de dano de veneno\n", receptor.getNome(), intensidadeDaAcao);
        } else if (efeitoCausado.getNome().equals("Regeneração")) {
            return String.format("%s recebeu %d de cura regenerativa\n", receptor.getNome(), intensidadeDaAcao);
        } else {
            throw new RuntimeException(String.format("Efeito '%s' inválido", efeitoCausado.getTipoDeEfeito()));
        }
    }

    /**
     * Exibe o histórico de ações realizadas durante o jogo.
     *
     * @param historico lista de mensagens representando as ações.
     */
    public void historico(ArrayList<String> historico) {
        for (String acao : historico) {
            System.out.printf("%s\n", acao);
        }
    }

    /**
     * Exibe as opções de inimigos disponíveis para seleção.
     *
     * @param inimigos lista de inimigos.
     */
    public void escolhasDeInimigos(ArrayList<Inimigo> inimigos) {
        int i = 0;
        for (Inimigo inimigo : inimigos) {
            System.out.printf("%d - %s", i + 1, inimigo.getNome());
            i++;
            if (!inimigo.estaVivo()) {
                System.out.printf("(Morto)");
            }
            System.out.printf("\n");
        }
        System.out.printf("%d - Cancelar\n", i + 1);
    }

    /**
     * Exibe mensagem de erro para escolha fora do intervalo válido.
     */
    public void escolhaForaDeAlcance() {
        System.out.printf("ATENÇÃO: escolha uma opção dentre os números listados.\n");
    }

    /**
     * Exibe mensagem de erro para energia insuficiente.
     */
    public void energiaInsuficiente() {
        System.out.printf(
                "ENERGIA INSUFICIENTE: a carta selecionada possui custo de energia superior ao nível de energia atual.\n");
    }

    /**
     * Informa que o inimigo selecionado já está morto.
     */
    public void inimigoEstaMorto() {
        System.out.printf(
                "INIMIGO JÁ ESTÁ MORTO: o inimigo selecionado já foi derrotado, essa ação não terá efeito\n");
    }

    /**
     * Exibe mensagem de vitória do jogador.
     */
    public void playerGanhou() {
        System.out.println("\nParabéns! Você GANHOU\n");
    }

    /**
     * Exibe mensagem de derrota do jogador.
     */
    public void playerPerdeu() {
        System.out.println("\nQue pena! Você perdeu\n");
    }

    /**
     * Exibe mensagem de erro para entrada não numérica.
     */
    public void estradaNaoNumerica() {
        System.out.println("\n ATENÇÂO: A escolha deve ser um número dentre os listados");
    }



    /**
     * Limpa a tela do console.
     *
     * Utiliza códigos ANSI para limpar a saída do terminal.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
