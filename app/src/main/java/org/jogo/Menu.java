package org.jogo;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.w3c.dom.Text;

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
            fabrica.setInitialTerminalSize(new TerminalSize(150, 45));
            this.screen = fabrica.createScreen();
            this.screen.startScreen();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    private int getLargura() {
        return screen.getTerminalSize().getColumns();
    }

    private int getAltura() {
        return screen.getTerminalSize().getRows();
    }

    private void desenharCentralizado(TextGraphics textG, int centroX, int y, String texto){
        int largura = getLargura();

        int x = centroX - texto.length()/2;

        if(x < 0) x = 0;
        if(x + texto.length() >= largura) x = largura - texto.length();

        textG.putString(x, y, texto);
    }

    private void printarEntidade(int centroX, int yBase, Entidade entidade, Tabuleiro tabuleiro){
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);
        String[] linhasAscci = entidade.getAscci().split("\n");

        int larguraAscci = 0;
        for (String linha : linhasAscci){
            larguraAscci = Math.max(larguraAscci, linha.length());
        }
        
        String nome = entidade.getNome();
        String status = "Vida: " + entidade.getVida() + "(escudo " + entidade.getEscudo() + ")";
        
        desenharCentralizado(textG, centroX, yBase, nome);
        
        desenharCentralizado(textG, centroX, yBase + 1, status);

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
            desenharCentralizado(textG, centroX, yBase + 2, linhaEfeitos);
        }

        if(entidade instanceof Inimigo inimigo){
            if(inimigo.estaVivo()){
                String proxAcao = "Irá " + inimigo.getProxAcao(tabuleiro);
                desenharCentralizado(textG, centroX, yBase + 3, proxAcao);
            }else {
                desenharCentralizado(textG, centroX, yBase + 3, "Está Morto");
            }
        }

        for(int i = 0; i < linhasAscci.length; i++){
            desenharCentralizado(textG, centroX, yBase + 4 + i, linhasAscci[i]);
        }

    }
    public void desenharStatus(Tabuleiro tabuleiro){
        int largura = getLargura();
        int y = 1;

        int totalEntidades = tabuleiro.getInimigos().size() + 1;
        int larguraPacote = largura / totalEntidades;

        int centroHeroi = larguraPacote / 2;
        printarEntidade(centroHeroi, y, tabuleiro.getHeroi(), tabuleiro);

        
        ArrayList<Inimigo> inimigos = tabuleiro.getInimigos();

        for(int i = 0; i < inimigos.size(); i++){
            int centro = larguraPacote * (i+1) + larguraPacote / 2;
            printarEntidade(centro, y, inimigos.get(i), tabuleiro);
        }

        
    }
    public void desenharLogs(ArrayList<String> historico){
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        int altura = getAltura();
        int largura = getLargura();

        int yInicio = altura / 2 - 3;
        int maxLinhasPorColuna = 6;
        int numColunas = 2;
        int larguraColuna = largura / numColunas;

        int totalMensagens = maxLinhasPorColuna * numColunas;

        int quantidadeProcessamento = Math.min(historico.size(), totalMensagens);

        textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        desenharCentralizado(textG, largura / 2, yInicio, "--- REGISTRO DE COMBATE ---");
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        for(int i = 0; i < quantidadeProcessamento; i++){
            int indiceReverso = historico.size() - 1 - i;

            String linha = historico.get(indiceReverso);

            int colunaAtual = i / maxLinhasPorColuna;
            int linhaAtual = i % maxLinhasPorColuna;
            
            int x = (larguraColuna * colunaAtual) + 5;
            int y =  yInicio + linhaAtual + 1;

            if(linha.trim().isEmpty() || linha.contains("===")){
                textG.setForegroundColor(TextColor.ANSI.YELLOW);
                textG.putString(x, y, linha);
                textG.setForegroundColor(TextColor.ANSI.GREEN);
            } else {
                if (linha.length() > larguraColuna - 6) {
                    linha = linha.substring(0, larguraColuna - 6) + "...";
                }
                textG.putString(x, y, "> " + linha);
            }
        }
    }
    public void desenharAviso(String tipoAviso){
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        switch(tipoAviso){
            case "energiaInsuficiente" :
                textG.putString(xAviso, yAviso, "ENERGIA INSUFICIENTE: a carta selecionada possui custo de energia superior ao nível de energia atual.");
                break;
            case "inimigoEstaMorto" :
                textG.putString(xAviso, yAviso, "INIMIGO JÁ ESTÁ MORTO: o inimigo selecionado já foi derrotado, essa ação não terá efeito");
                break;
            default:
                break;
        }
    }

    public void desenharSelecaoCartas(MaoDoJogador maoDoJogador, int cursor, int energia, int energiaMaxima){
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        int largura = getLargura();
        int altura = getAltura();

        int totalCartas = maoDoJogador.getTamanho();
        int totalOpcoes = totalCartas + 1;

        int numeroColunas = 2; //Possivel mudar para modularizar depois;
        int larguraPacote = largura / numeroColunas;
        int alturaLinha = 5;
        
        int numeroLinhasNecessario = (int) Math.ceil((double) totalOpcoes / numeroColunas);
        int yBase = altura - (numeroLinhasNecessario * alturaLinha) - 1;
        
        String texto = energia + "/" + energiaMaxima + " energia";

        desenharCentralizado(textG, largura / 2, yBase - 2, "Escolha sua ação");
        desenharCentralizado(textG, largura / 2, yBase - 1, texto);

        for(int i = 0; i < maoDoJogador.getTamanho(); i++){
            Carta carta = maoDoJogador.getCarta(i);
            String nome = carta.getNome();
            String efeitoCustoAoE = carta.getEfeitoCustoAoE();
            String descricao = carta.getDescricao();

            int colunaAtual = i % numeroColunas;
            int linhaAtual = i / numeroColunas;

            int centro = (larguraPacote * colunaAtual) + larguraPacote/2;
            int yAtual = yBase + (linhaAtual * alturaLinha);

            if(cursor == i){
                textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                desenharCentralizado(textG, centro, yAtual - 1, "↓");
            }else{
                textG.setForegroundColor(TextColor.ANSI.GREEN);
            }
            desenharCentralizado(textG, centro, yAtual, nome);
            desenharCentralizado(textG, centro, yAtual + 1, efeitoCustoAoE);
            
            List<String> linhas = quebrarTexto(descricao, larguraPacote - 10);

            for(int j = 0; j < linhas.size(); j++){
                desenharCentralizado(textG, centro, yAtual + 2 + j, linhas.get(j));
            }
        }
        int colunaEncerramento = totalCartas % numeroColunas;
        int linhaEncerrametno = totalCartas / numeroColunas;


        int centroEncerramento = larguraPacote * colunaEncerramento + larguraPacote / 2;
        int yEncerramento = yBase + (linhaEncerrametno * alturaLinha);

        if(cursor == maoDoJogador.getTamanho()){
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroEncerramento, yEncerramento -1, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroEncerramento, yEncerramento, "Encerrar");
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

    public void desenharTextoCentralizado(TextGraphics textG, int xBase, int yBase, int larguraTotal, String texto){
        int posicaoX = xBase + (larguraTotal / 2) - (texto.length() / 2);
        textG.putString(posicaoX, yBase, texto);
    }

    public void desenharFrasesCentralizadas(TextGraphics textG, int xBase, int yBase, int larguraTotal, String textoLongo){
        List<String> linhas = quebrarTexto(textoLongo, larguraTotal);
        for(int i = 0; i < linhas.size(); i++){
            desenharTextoCentralizado(textG, xBase, yBase + i, larguraTotal, linhas.get(i));
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
    public void desenharSelecaoInimigos(ArrayList<Inimigo> inimigos, int cursor){
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        int largura = getLargura();
        int altura = getAltura();

        int yBase = altura - 10;

        int totalInimigos = inimigos.size();
        int totalOpcoes = totalInimigos + 1;
        int larguraPacote = largura / totalOpcoes;


        desenharCentralizado(textG, largura / 2, yBase - 2, "Escolha seu Alvo:");

        for(int i = 0; i < inimigos.size(); i++){
            Inimigo inimigo = inimigos.get(i);
            String nome = inimigo.getNome();

            int centro = larguraPacote * i + larguraPacote / 2;

            if(cursor == i){
                textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                desenharCentralizado(textG, centro, yBase, "↓");
                
            }else{
                textG.setForegroundColor(TextColor.ANSI.GREEN);
            }
            desenharCentralizado(textG, centro, yBase, nome);

            if(!inimigo.estaVivo()){
                desenharCentralizado(textG, centro, yBase + 1, "(Está Morto)");
            }
        }

        int centroEncerramento = larguraPacote * totalInimigos + larguraPacote / 2;

        if(cursor == inimigos.size()){
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroEncerramento, yBase, "↓");
        }else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }

        desenharCentralizado(textG, centroEncerramento, yBase, "Cancelar");
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

    public void desenharMensagemFinal(String mensagem){
        TextGraphics textG = screen.newTextGraphics();

        int x = xEscolhas;
        int y = yEscolhas;

        textG.setForegroundColor(TextColor.ANSI.YELLOW);
        textG.putString(x, y, mensagem);
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
