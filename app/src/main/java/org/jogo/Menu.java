package org.jogo;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe responsável pela interação com o jogador via console.
 *
 * O Menu centraliza toda a lógica de exibição de informações do jogo,
 * leitura de entradas do usuário e geração de mensagens relacionadas às ações
 * realizadas durante a partida.
 */
public class Menu {
    private Screen screen;

    private static final int xAviso = 5;
    private static final int yAviso = 34;
    private static final int xEscolhas = 5;
    private static final int yEscolhas = 35;

    /**
     * Inicializa a tela do terminal com o tamanho especificado (150x45).
     * Configura e inicia a screen do Lanterna.
     */
    public void incializarTela() {
        try {
            DefaultTerminalFactory fabrica = new DefaultTerminalFactory();
            fabrica.setInitialTerminalSize(new TerminalSize(150, 45));
            this.screen = fabrica.createScreen();
            this.screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Retorna a largura atual da tela do terminal.
     * 
     * @return número de colunas da tela
     */
    private int getLargura() {
        return screen.getTerminalSize().getColumns();
    }

    /**
     * Retorna a altura atual da tela do terminal.
     * 
     * @return número de linhas da tela
     */
    private int getAltura() {
        return screen.getTerminalSize().getRows();
    }

    /**
     * Desenha um texto centralizado horizontalmente na tela.
     * 
     * @param textG   objeto TextGraphics para desenhar
     * @param centroX coordenada X central desejada
     * @param y       coordenada Y para posicionar o texto
     * @param texto   texto a ser desenhado
     */
    private void desenharCentralizado(TextGraphics textG, int centroX, int y, String texto) {
        int largura = getLargura();

        int x = centroX - texto.length() / 2;

        if (x < 0)
            x = 0;
        if (x + texto.length() >= largura)
            x = largura - texto.length();

        textG.putString(x, y, texto);
    }

    /**
     * Desenha o título do jogo centralizado na tela.
     * O título é carregado do arquivo "titulo.txt".
     */
    public void desenharTitulo() {
        try (Scanner scannerTitulo = new Scanner(new File("titulo.txt"))) {
            TextGraphics textG = screen.newTextGraphics();
            textG.setForegroundColor(TextColor.ANSI.GREEN);
            int yCentro = getAltura() / 2;
            int xCentro = getLargura() / 2;
            while (scannerTitulo.hasNextLine()) {
                desenharCentralizado(textG, xCentro, yCentro, scannerTitulo.nextLine());
            }
            scannerTitulo.close();
            textG.setForegroundColor(TextColor.ANSI.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Desenha o mapa do jogo centralizado na tela.
     * O mapa é carregado do arquivo "desenhoDoMapa.txt".
     */
    public void desenharMapa() {
        try (Scanner scannerMapa = new Scanner(new File("desenhoDoMapa.txt"))) {

            TextGraphics textG = screen.newTextGraphics();
            textG.setForegroundColor(TextColor.ANSI.GREEN);

            List<String> linhas = new ArrayList<>();
            while (scannerMapa.hasNextLine()) {
                linhas.add(scannerMapa.nextLine());
            }
            int larguraMaxima = 0;
            for (String linha : linhas) {
                if (linha.length() > larguraMaxima) {
                    larguraMaxima = linha.length();
                }
            }

            int alturaMapa = linhas.size();

            int xCentro = getLargura() / 2;
            int yCentro = getAltura() / 2;

            int xInicio = xCentro - larguraMaxima / 2;
            int yInicio = yCentro - alturaMapa / 2;

            if (xInicio < 0)
                xInicio = 0;
            if (yInicio < 0)
                yInicio = 0;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);

                linha = String.format("%-" + larguraMaxima + "s", linha);

                textG.putString(xInicio, yInicio + i, linha);
            }

            textG.setForegroundColor(TextColor.ANSI.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Desenha o personagem do jogador em uma posição específica do mapa.
     * 
     * @param posicao índice da posição (0-10) que determina as coordenadas do
     *                personagem
     */
    public void desenharPersonagemNoMapa(int posicao) {
        Integer[][] coordenadas = { { 60, 20 }, // 0
                { 65, 17 }, // 1
                { 65, 23 }, // 2
                { 71, 17 }, // 3
                { 71, 20 }, // 4
                { 71, 26 }, // 5
                { 77, 20 }, // 6
                { 77, 26 }, // 7
                { 83, 20 }, // 8
                { 83, 26 }, // 9
                { 89, 20 } // 10
        };
        ArrayList<Integer[]> listaDeCoordenadas = new ArrayList(Arrays.asList(coordenadas));
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        Integer[] posicaoCerta = listaDeCoordenadas.get(posicao);
        desenharCentralizado(textG, posicaoCerta[0], posicaoCerta[1], "Å");
        textG.setForegroundColor(TextColor.ANSI.DEFAULT);

    }

    /**
     * Desenha uma entidade (herói ou inimigo) com suas informações na tela.
     * Exibe nome, vida, escudo, efeitos ativos, representação ASCII e próxima ação
     * (para inimigos).
     * 
     * @param centroX   coordenada X central para o desenho
     * @param yBase     coordenada Y base para o desenho
     * @param entidade  entidade a ser desenhada
     * @param tabuleiro referência à batalha atual (necessário para ações de
     *                  inimigos)
     */
    private void printarEntidade(int centroX, int yBase, Entidade entidade, Batalha tabuleiro) {
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);
        String[] linhasAscci = entidade.getAscii().split("\n");

        String nome = entidade.getNome();
        String status = "Vida: " + entidade.getVida() + "(escudo " + entidade.getEscudo() + ")";

        desenharCentralizado(textG, centroX, yBase, nome);

        desenharCentralizado(textG, centroX, yBase + 1, status);

        if (!entidade.getEfeitos().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            ArrayList<Efeito> efeitos = entidade.getEfeitos();
            for (int i = 0; i < efeitos.size(); i++) {
                Efeito efeito = efeitos.get(i);
                sb.append(efeito.getNome()).append(" ").append(efeito.getAcumulos());

                if (i < efeitos.size() - 1) {
                    sb.append(" | ");
                }
            }
            String linhaEfeitos = sb.toString();
            desenharCentralizado(textG, centroX, yBase + 2, linhaEfeitos);
        }

        if (entidade instanceof Inimigo inimigo) {
            if (inimigo.estaVivo()) {
                String proxAcao = "Irá " + inimigo.getProxAcao(tabuleiro);
                desenharCentralizado(textG, centroX, yBase + 3, proxAcao);
            } else {
                desenharCentralizado(textG, centroX, yBase + 3, "Está Morto");
            }
        }

        for (int i = 0; i < linhasAscci.length; i++) {
            desenharCentralizado(textG, centroX, yBase + 4 + i, linhasAscci[i]);
        }

    }

    /**
     * Desenha o status de todas as entidades da batalha (herói e inimigos).
     * Organiza as entidades lado a lado na tela.
     * 
     * @param tabuleiro batalha contendo as entidades a serem exibidas
     */
    public void desenharStatus(Batalha tabuleiro) {
        int largura = getLargura();
        int y = 1;

        int totalEntidades = tabuleiro.getInimigos().size() + 1;
        int larguraPacote = largura / totalEntidades;

        int centroHeroi = larguraPacote / 2;
        printarEntidade(centroHeroi, y, tabuleiro.getHeroi(), tabuleiro);

        ArrayList<Inimigo> inimigos = tabuleiro.getInimigos();

        for (int i = 0; i < inimigos.size(); i++) {
            int centro = larguraPacote * (i + 1) + larguraPacote / 2;
            printarEntidade(centro, y, inimigos.get(i), tabuleiro);
        }
    }

    /**
     * Desenha o histórico de ações do combate em duas colunas.
     * As mensagens mais recentes aparecem primeiro.
     * 
     * @param historico lista de mensagens de ações ocorridas
     */
    public void desenharLogs(ArrayList<String> historico) {
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

        for (int i = 0; i < quantidadeProcessamento; i++) {
            int indiceReverso = historico.size() - 1 - i;

            String linha = historico.get(indiceReverso);

            int colunaAtual = i / maxLinhasPorColuna;
            int linhaAtual = i % maxLinhasPorColuna;

            int x = (larguraColuna * colunaAtual) + 5;
            int y = yInicio + linhaAtual + 1;

            if (linha.trim().isEmpty() || linha.contains("===")) {
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

    /**
     * Exibe um aviso específico na posição pré-definida da tela.
     * 
     * @param tipoAviso tipo do aviso ("energiaInsuficiente" ou "inimigoEstaMorto")
     */
    public void desenharAviso(String tipoAviso, int x, int y) {
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        switch (tipoAviso) {
            case "energiaInsuficiente":
                desenharCentralizado(textG, x, y,
                        "ENERGIA INSUFICIENTE: a carta selecionada possui custo de energia superior ao nível de energia atual.");
                break;
            case "inimigoEstaMorto":
                desenharCentralizado(textG, x, y,
                        "INIMIGO JÁ ESTÁ MORTO: o inimigo selecionado já foi derrotado, essa ação não terá efeito");
                break;
            case "ouroInsuficiente":
                List<String> trechosEncaixados = quebrarTexto(
                        "FALTA OURO: Desculpa viajante, eu não posso te dar ouro, talvez você devesse voltar quando estiver hmm..... mais rico",
                        50);
                for (String trecho : trechosEncaixados) {
                    textG.putString(x, y, trecho);
                    y++;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Desenha a seleção de cartas da mão do jogador em formato de grade.
     * Exibe nome, custo/efeito, descrição e destaca a carta selecionada pelo
     * cursor.
     * 
     * @param maoDoJogador  mão atual do jogador
     * @param cursor        posição atual do cursor na seleção
     * @param energia       energia atual do jogador
     * @param energiaMaxima energia máxima do jogador
     */
    public void desenharSelecaoCartas(MaoDoJogador maoDoJogador, int cursor, int energia, int energiaMaxima,
            String tipodDeAviso) {
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        int largura = getLargura();
        int altura = getAltura();

        int totalCartas = maoDoJogador.getTamanho();
        int totalOpcoes = totalCartas + 1;

        int numeroColunas = 2; // Possivel mudar para modularizar depois;
        int larguraPacote = largura / numeroColunas;
        int alturaLinha = 5;

        int numeroLinhasNecessario = (int) Math.ceil((double) totalOpcoes / numeroColunas);
        int yBase = altura - (numeroLinhasNecessario * alturaLinha) - 1;

        String texto = energia + "/" + energiaMaxima + " energia";
        desenharAviso(tipodDeAviso, largura / 2, yBase - 3);
        desenharCentralizado(textG, largura / 2, yBase - 2, "Escolha sua ação");
        desenharCentralizado(textG, largura / 2, yBase - 1, texto);

        for (int i = 0; i < maoDoJogador.getTamanho(); i++) {
            Carta carta = maoDoJogador.getCarta(i);
            String nome = carta.getNome();
            String efeitoCustoAoE = carta.getEfeitoCustoAoE();
            String descricao = carta.getDescricao();

            int colunaAtual = i % numeroColunas;
            int linhaAtual = i / numeroColunas;

            int centro = (larguraPacote * colunaAtual) + larguraPacote / 2;
            int yAtual = yBase + (linhaAtual * alturaLinha);

            if (cursor == i) {
                textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                desenharCentralizado(textG, centro, yAtual - 1, "↓");
            } else {
                textG.setForegroundColor(TextColor.ANSI.GREEN);
            }
            desenharCentralizado(textG, centro, yAtual, nome);
            desenharCentralizado(textG, centro, yAtual + 1, efeitoCustoAoE);

            List<String> linhas = quebrarTexto(descricao, larguraPacote - 10);

            for (int j = 0; j < linhas.size(); j++) {
                desenharCentralizado(textG, centro, yAtual + 2 + j, linhas.get(j));
            }
        }
        int colunaEncerramento = totalCartas % numeroColunas;
        int linhaEncerrametno = totalCartas / numeroColunas;

        int centroEncerramento = larguraPacote * colunaEncerramento + larguraPacote / 2;
        int yEncerramento = yBase + (linhaEncerrametno * alturaLinha);

        if (cursor == maoDoJogador.getTamanho()) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroEncerramento, yEncerramento - 1, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroEncerramento, yEncerramento, "Encerrar");
    }

    public void desenharBauFechado() {
        try (Scanner scannerMapa = new Scanner(new File("baufechado.txt"))) {

            TextGraphics textG = screen.newTextGraphics();
            textG.setForegroundColor(TextColor.ANSI.GREEN);

            List<String> linhas = new ArrayList<>();
            while (scannerMapa.hasNextLine()) {
                linhas.add(scannerMapa.nextLine());
            }
            int larguraMaxima = 0;
            for (String linha : linhas) {
                if (linha.length() > larguraMaxima) {
                    larguraMaxima = linha.length();
                }
            }

            int alturaMapa = linhas.size();

            int xCentro = getLargura() / 2;
            int yCentro = getAltura() / 2;

            int xInicio = xCentro - larguraMaxima / 2;
            int yInicio = yCentro - alturaMapa / 2;

            if (xInicio < 0)
                xInicio = 0;
            if (yInicio < 0)
                yInicio = 0;
            int i;
            for (i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);

                linha = String.format("%-" + larguraMaxima + "s", linha);

                textG.putString(xInicio, yInicio + i, linha);
            }
            desenharCentralizado(textG, xCentro, yInicio + i + 2, "Um Baú!!!!! Precione qualquer tecla para abri-lo");
            textG.setForegroundColor(TextColor.ANSI.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desenharBauAberto(int quantidadeOuro) {
        try (Scanner scannerMapa = new Scanner(new File("bauaberto.txt"))) {

            TextGraphics textG = screen.newTextGraphics();
            textG.setForegroundColor(TextColor.ANSI.GREEN);

            List<String> linhas = new ArrayList<>();
            while (scannerMapa.hasNextLine()) {
                linhas.add(scannerMapa.nextLine());
            }
            int larguraMaxima = 0;
            for (String linha : linhas) {
                if (linha.length() > larguraMaxima) {
                    larguraMaxima = linha.length();
                }
            }

            int alturaMapa = linhas.size();

            int xCentro = getLargura() / 2;
            int yCentro = getAltura() / 2;

            int xInicio = xCentro - larguraMaxima / 2;
            int yInicio = yCentro - alturaMapa / 2;

            if (xInicio < 0)
                xInicio = 0;
            if (yInicio < 0)
                yInicio = 0;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                linha = String.format("%-" + larguraMaxima + "s", linha);
                textG.putString(xInicio, yInicio + i, linha);
            }

            String mensagemOuro = "Você achou " + quantidadeOuro + " de ouro!";
            int xMensagem = xCentro - (mensagemOuro.length() / 2);
            int yMensagem = yInicio + alturaMapa + 2;

            if (xMensagem < 0)
                xMensagem = 0;

            textG.putString(xMensagem, yMensagem, mensagemOuro);

            textG.setForegroundColor(TextColor.ANSI.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desenharLojista(String tipoDeAviso, int ouro) {
        try (Scanner scannerLojista = new Scanner(new File("lojista.txt"))) {
            TextGraphics textG = screen.newTextGraphics();

            List<String> linhas = new ArrayList<>();
            while (scannerLojista.hasNextLine()) {
                linhas.add(scannerLojista.nextLine());
            }

            int larguraMaxima = 0;
            for (String linha : linhas) {
                if (linha.length() > larguraMaxima) {
                    larguraMaxima = linha.length();
                }
            }

            int x = 4;
            int y = 4;

            textG.setForegroundColor(TextColor.ANSI.GREEN);
            int centroAscci = x + larguraMaxima / 2;
            desenharCentralizado(textG, centroAscci, y - 2, "Criatura Encapuzada");

            for (String linha : linhas) {
                textG.putString(x, y, linha);
                y++;
            }

            List<String> trechosEncaixados = quebrarTexto(
                    "Olá viajante, sinta-se a vontade para comprar qualquer coisa que precisar!", larguraMaxima);
            for (String trecho : trechosEncaixados) {
                textG.putString(x, y, trecho);
                y++;
            }

            desenharAviso(tipoDeAviso, x, y + 2);

            desenharCentralizado(textG, centroAscci, y + 10, "Você tem: " + ouro + " ouros");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void desenharEstoqueLoja(ArrayList<Carta> estoque, int cursor) {
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        int largura = getLargura();

        int xInicioDireita = largura / 2;
        int larguraDisponivel = largura / 2;

        int numeroColunas = 2;
        int larguraPacote = larguraDisponivel / numeroColunas;
        int alturaLinha = 10;

        int larguraSeguraTexto = larguraPacote - 4;

        int totalOpcoes = estoque.size() + 2;
        int linhasPorColuna = (int) Math.ceil((double) totalOpcoes / numeroColunas);
        if (linhasPorColuna == 0)
            linhasPorColuna = 1;

        int yBase = 4;

        desenharCentralizado(textG, xInicioDireita + (larguraDisponivel / 2), yBase - 3, "--- ESTOQUE DO LOJISTA ---");

        for (int i = 0; i < estoque.size(); i++) {
            Carta carta = estoque.get(i);
            String nome = carta.getNome();
            String efeitoCustoAoE = carta.getEfeitoCustoAoE();
            String descricao = carta.getDescricao();
            String preco = String.valueOf(carta.getPreco());

            int colunaAtual = i / linhasPorColuna;
            int linhaAtual = i % linhasPorColuna;

            int centro = xInicioDireita + (larguraPacote * colunaAtual) + (larguraPacote / 2);
            int yAtual = yBase + (linhaAtual * alturaLinha);

            if (cursor == i) {
                textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                desenharCentralizado(textG, centro, yAtual - 2, "↓");
            } else {
                textG.setForegroundColor(TextColor.ANSI.GREEN);
            }

            desenharCentralizado(textG, centro, yAtual - 1, "Preço: " + preco + " Ouros");

            int linhaOffset = 0;

            List<String> linhasNome = quebrarTexto(nome, larguraSeguraTexto);
            for (String ln : linhasNome) {
                desenharCentralizado(textG, centro, yAtual + linhaOffset, ln);
                linhaOffset++;
            }

            List<String> linhasEfeito = quebrarTexto(efeitoCustoAoE, larguraSeguraTexto);
            for (String le : linhasEfeito) {
                desenharCentralizado(textG, centro, yAtual + linhaOffset, le);
                linhaOffset++;
            }

            List<String> linhasDesc = quebrarTexto(descricao, larguraSeguraTexto);
            for (String ld : linhasDesc) {
                desenharCentralizado(textG, centro, yAtual + linhaOffset, ld);
                linhaOffset++;
            }
        }
        int indexRemover = estoque.size();
        int colRemover = indexRemover / linhasPorColuna;
        int linRemover = indexRemover % linhasPorColuna;
        int centroRemover = xInicioDireita + (larguraPacote * colRemover) + (larguraPacote / 2);
        int yRemover = yBase + (linRemover * alturaLinha);

        if (cursor == indexRemover) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroRemover, yRemover - 2, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroRemover, yRemover - 1, "Preço: 10 Ouros");
        desenharCentralizado(textG, centroRemover, yRemover, "Remover uma carta");

        int indexSair = estoque.size() + 1;
        int colSair = indexSair / linhasPorColuna;
        int linSair = indexSair % linhasPorColuna;
        int centroSair = xInicioDireita + (larguraPacote * colSair) + (larguraPacote / 2);
        int ySair = yBase + (linSair * alturaLinha);

        if (cursor == indexSair) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroSair, ySair - 1, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroSair, ySair, "Sair da Loja");
    }

    public void desenharLoja(ArrayList<Carta> estoque, int cursor, String tipoDeAviso, int ouro) {
        desenharLojista(tipoDeAviso, ouro);
        desenharEstoqueLoja(estoque, cursor);
    }

    public void desenharRemocaoCartas(ArrayList<Carta> inventario, int cursor) {
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        int largura = getLargura();
        int numeroColunas = 5;
        int larguraPacote = largura / numeroColunas;
        int alturaLinha = 10;
        int larguraSeguraTexto = larguraPacote - 4;

        int yBase = 5;

        desenharCentralizado(textG, largura / 2, yBase - 3, "--- ESCOLHA UMA CARTA PARA REMOVER ---");

        for (int i = 0; i < inventario.size(); i++) {
            Carta carta = inventario.get(i);
            String nome = carta.getNome();
            String efeitoCustoAoE = carta.getEfeitoCustoAoE();
            String descricao = carta.getDescricao();

            int colunaAtual = i % numeroColunas;
            int linhaAtual = i / numeroColunas;

            int centro = (larguraPacote * colunaAtual) + (larguraPacote / 2);
            int yAtual = yBase + (linhaAtual * alturaLinha);

            if (cursor == i) {
                textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                desenharCentralizado(textG, centro, yAtual - 2, "↓");
            } else {
                textG.setForegroundColor(TextColor.ANSI.GREEN);
            }

            int linhaOffset = -1;

            List<String> linhasNome = quebrarTexto(nome, larguraSeguraTexto);
            for (String ln : linhasNome) {
                desenharCentralizado(textG, centro, yAtual + linhaOffset, ln);
                linhaOffset++;
            }

            List<String> linhasEfeito = quebrarTexto(efeitoCustoAoE, larguraSeguraTexto);
            for (String le : linhasEfeito) {
                desenharCentralizado(textG, centro, yAtual + linhaOffset, le);
                linhaOffset++;
            }

            List<String> linhasDesc = quebrarTexto(descricao, larguraSeguraTexto);
            for (String ld : linhasDesc) {
                desenharCentralizado(textG, centro, yAtual + linhaOffset, ld);
                linhaOffset++;
            }
        }

        int indexCancelar = inventario.size();
        int colunaCancelar = indexCancelar % numeroColunas;
        int linhaCancelar = indexCancelar / numeroColunas;

        int centroCancelar = (larguraPacote * colunaCancelar) + (larguraPacote / 2);
        int yCancelar = yBase + (linhaCancelar * alturaLinha);

        if (cursor == indexCancelar) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroCancelar, yCancelar - 2, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroCancelar, yCancelar - 1, "Cancelar");
    }

    public void desenharFogueira(int cursor, Carta cartaRecebida) {
        TextGraphics textG = screen.newTextGraphics();
        String nome = cartaRecebida.getNome();
        String efeitoCustoArea = cartaRecebida.getEfeitoCustoAoE();
        String descricao = cartaRecebida.getDescricao();
        List<String> descricaoQuebrada = quebrarTexto(descricao, 30);
        int centro = getLargura() / 2;
        int yInicioArte = 0;
        int yFinalDaArte = yInicioArte;

        try (Scanner scannerFogueira = new Scanner(new File("fogueira.txt"))) {
            textG.setForegroundColor(TextColor.ANSI.GREEN);

            List<String> linhas = new ArrayList<>();
            while (scannerFogueira.hasNextLine()) {
                linhas.add(scannerFogueira.nextLine());
            }

            int larguraMaxima = 0;
            for (String linha : linhas) {
                if (linha.length() > larguraMaxima) {
                    larguraMaxima = linha.length();
                }
            }

            int xInicio = centro - larguraMaxima / 2;
            if (xInicio < 0)
                xInicio = 0;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                linha = String.format("%-" + larguraMaxima + "s", linha);
                textG.putString(xInicio, yInicioArte + i, linha);
            }

            yFinalDaArte = yInicioArte + linhas.size();

        } catch (Exception e) {
            e.printStackTrace();
            yFinalDaArte = yInicioArte + 5;
        }

        int yTextos = yFinalDaArte + 1;
        String textoInicial = "O sol já se pôs e você encontra uma velha barraca abandonada. Ao lado dela, há uma pilha de entulhos.";
        String perguntarAcao = "O que gostaria de fazer?";

        textG.setForegroundColor(TextColor.ANSI.GREEN);
        List<String> linhas = quebrarTexto(textoInicial, 80);
        for (String linha : linhas) {
            desenharCentralizado(textG, centro, yTextos, linha);
            yTextos++;
        }
        desenharCentralizado(textG, centro, yTextos + 2, perguntarAcao);

        int yOpcoes = yTextos + 4;
        int centroEsquerda = getLargura() / 4;
        int centroDireita = (getLargura() / 4) * 3;

        if (cursor == 0) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroEsquerda, yOpcoes - 1, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroEsquerda, yOpcoes, "Dormir na Barraca ao lado da fogueira (Recupera toda sua vida)");

        if (cursor == 1) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroDireita, yOpcoes - 1, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroDireita, yOpcoes, "Passar a noite criando um novo equipamento com os estulhos");
        desenharCentralizado(textG, centroDireita, ++yOpcoes, "(Ganhará a seguinte carta)");
        yOpcoes++;
        desenharCentralizado(textG, centroDireita, ++yOpcoes, nome);

        for(int i = 0; i < descricaoQuebrada.size(); i++){
            desenharCentralizado(textG, centroDireita, yOpcoes + i + 1, descricaoQuebrada.get(i));
        }
        textG.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    public void desenharRegeneracaoNaFogueira(int x, int y) {
        /*
         * Apesar do vento seco e do sentimento de solidão, você consegue dormir à
         * noite.
         *
         * Sua vida foi recuperada ao máximo
         */

    }

    public void desenharCartaNaFogueira(Carta cartaRecebida, int x, int y) {
        /*
         * Depois de uma longa noite de trabalho, você consegue bolar uma baita
         * engenhoca com todo aquele entulho.
         *
         * Uma unidade de "cartaRecebida" foi adicionada ao seu inventário.
         */
    }

    public void desenharEventoAmbulancia(int cursor) {
        TextGraphics textG = screen.newTextGraphics();
        int centro = getLargura() / 2;
        int yInicioArte = 3;
        int yFinalDaArte = yInicioArte;

        try (Scanner scannerAmbulancia = new Scanner(new File("ambulancia.txt"))) {
            textG.setForegroundColor(TextColor.ANSI.GREEN);

            List<String> linhas = new ArrayList<>();
            while (scannerAmbulancia.hasNextLine()) {
                linhas.add(scannerAmbulancia.nextLine());
            }

            int larguraMaxima = 0;
            for (String linha : linhas) {
                if (linha.length() > larguraMaxima) {
                    larguraMaxima = linha.length();
                }
            }

            int xInicio = centro - larguraMaxima / 2;
            if (xInicio < 0)
                xInicio = 0;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                linha = String.format("%-" + larguraMaxima + "s", linha);
                textG.putString(xInicio, yInicioArte + i, linha);
            }

            yFinalDaArte = yInicioArte + linhas.size();

        } catch (Exception e) {
            e.printStackTrace();
            yFinalDaArte = yInicioArte + 5;
        }

        int yTextos = yFinalDaArte + 2;
        String textoInicial = "Na sua caminhada você encontra uma ambulância abandonada, dentro dela você acha stimpacks de alta potência e inaladores de Jet experimental";
        String perguntarAcao = "O que gostaria de fazer?";

        textG.setForegroundColor(TextColor.ANSI.GREEN);
        List<String> linhas = quebrarTexto(textoInicial, 80);
        for (String linha : linhas) {
            desenharCentralizado(textG, centro, yTextos, linha);
            yTextos++;
        }
        desenharCentralizado(textG, centro, yTextos + 2, perguntarAcao);

        int yOpcoes = yTextos + 8;
        int centroEsquerda = getLargura() / 4;
        int centroDireita = (getLargura() / 4) * 3;

        if (cursor == 0) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroEsquerda, yOpcoes - 1, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroEsquerda, yOpcoes, "Injetar Stimpack de Alta Potência (+15 Vida Máxima)");

        if (cursor == 1) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroDireita, yOpcoes - 1, "↓");
        } else {
            textG.setForegroundColor(TextColor.ANSI.GREEN);
        }
        desenharCentralizado(textG, centroDireita, yOpcoes, "Inalar Jet Experimental (+1 Energia Máxima)");

        textG.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    public void desenharEventoAranhaEscondida(int cursor) {
        /*
         * enunciado:
         * "Você seguia tranquilamente sua caminhada, quando, de repente, seus reflexos agem! Uma imensa aranha mutante pula de debaixo da areia em sua direção."
         * pergunta: "Como gostaria de se defender?"
         *
         * cursor == 0: "Utilizar seus próprios braços para lutar (-1/3 de Vida)"
         * cursor == 1:
         * "Utilizar um equipamento rápido para golpear (-1 Equipamento Aleatório)"
         */
    }

    /**
     * Encerra a tela do Lanterna.
     */
    public void desligarTela() {
        try {
            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aplica as alterações de desenho na tela (refresh).
     */
    public void aplicarDesenho() {
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Limpa todo o conteúdo desenhado na tela.
     */
    public void limparDesenho() {
        screen.clear();
    }

    /**
     * Aguarda e recebe uma entrada do teclado do usuário.
     * 
     * @return KeyStroke representando a tecla pressionada, ou null em caso de erro
     */
    public KeyStroke receberInputTeclado() {
        try {
            return screen.readInput();
        } catch (IOException e) {
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

    /**
     * Divide um texto em múltiplas linhas respeitando um limite de largura.
     * 
     * @param texto         texto a ser quebrado
     * @param larguraMaxima largura máxima de cada linha
     * @return lista de linhas resultantes
     */
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
    public void desenharSelecaoInimigos(ArrayList<Inimigo> inimigos, int cursor, String TipoDeAviso) {
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);

        int largura = getLargura();
        int altura = getAltura();

        int yBase = altura - 10;

        int totalInimigos = inimigos.size();
        int totalOpcoes = totalInimigos + 1;
        int larguraPacote = largura / totalOpcoes;
        desenharAviso(TipoDeAviso, largura / 2, yBase - 3);
        desenharCentralizado(textG, largura / 2, yBase - 2, "Escolha seu Alvo:");

        for (int i = 0; i < inimigos.size(); i++) {
            Inimigo inimigo = inimigos.get(i);
            String nome = inimigo.getNome();

            int centro = larguraPacote * i + larguraPacote / 2;

            if (cursor == i) {
                textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                desenharCentralizado(textG, centro, yBase, "↓");

            } else {
                textG.setForegroundColor(TextColor.ANSI.GREEN);
            }
            desenharCentralizado(textG, centro, yBase, nome);

            if (!inimigo.estaVivo()) {
                desenharCentralizado(textG, centro, yBase + 1, "(Está Morto)");
            }
        }

        int centroEncerramento = larguraPacote * totalInimigos + larguraPacote / 2;

        if (cursor == inimigos.size()) {
            textG.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            desenharCentralizado(textG, centroEncerramento, yBase, "↓");
        } else {
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

    public void esperarFeedback() {
        boolean esperandoInput = true;
        while (esperandoInput) {
            KeyStroke key = receberInputTeclado();
            if (key != null) {
                esperandoInput = false;
            }
        }

    }

    /**
     * Exibe uma mensagem final no final da batalha.
     * 
     * @param mensagem mensagem a ser exibida
     */
    public void desenharMensagemFinalBatalha(String mensagem) {
        TextGraphics textG = screen.newTextGraphics();

        int x = xEscolhas;
        int y = yEscolhas;

        textG.setForegroundColor(TextColor.ANSI.YELLOW);
        textG.putString(x, y, mensagem);
    }

    /**
     * Exibe uma mensagem final centralizada na tela (vitória ou derrota).
     * 
     * @param mensagem mensagem a ser exibida
     */
    public void desenharMensagemFinal(String mensagem) {
        TextGraphics textG = screen.newTextGraphics();
        textG.setForegroundColor(TextColor.ANSI.GREEN);
        int yCentro = getAltura() / 2;
        int xCentro = getLargura() / 2;
        desenharCentralizado(textG, xCentro, yCentro, mensagem);
    }

    /**
     * Exibe mensagem de erro para entrada não numérica.
     */
    public void estradaNaoNumerica() {
        System.out.println("\n ATENÇÂO: A escolha deve ser um número dentre os listados");
    }
}
