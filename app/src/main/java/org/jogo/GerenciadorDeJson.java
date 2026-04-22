package org.jogo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;


/**
 * Gerencia a persistência de dados do jogo em arquivos JSON.
 * 
 * <p>Esta classe é responsável por salvar e carregar o estado do jogo
 * utilizando a biblioteca Gson para serialização/desserialização.
 * Suporta polimorfismo para a hierarquia de classes {@link Carta}
 * através de um adaptador de tipos.</p>
 * 
 * <p>O gerenciador trabalha com dois arquivos:
 * <ul>
 *   <li><strong>saveInicial.json</strong> - arquivo com os dados iniciais do jogo</li>
 *   <li><strong>saveAtual.json</strong> - arquivo com o progresso salvo do jogador</li>
 * </ul>
 * </p>
 */
public class GerenciadorDeJson {

    /**
     * Cria um adaptador de tipos para serialização polimórfica das cartas.
     * 
     * <p>O adaptador permite que subclasses de {@link Carta} sejam
     * corretamente serializadas e desserializadas, adicionando um campo
     * "tipo_carta" ao JSON para identificar o tipo concreto.</p>
     * 
     * @return fábrica de adaptadores configurada para as subclasses de Carta
     */
    private RuntimeTypeAdapterFactory<Carta> criarAdaptadorDeCartas(){
        return RuntimeTypeAdapterFactory.of(Carta.class, "tipo_carta")
                .registerSubtype(CartaDano.class, "Dano")
                .registerSubtype(CartaEscudo.class, "Escudo")
                .registerSubtype(CartaEfeito.class, "Efeito");
    }


    /**
     * Salva os dados do jogo no arquivo "saveAtual.json".
     * 
     * <p>Utiliza formatação pretty-printing para facilitar a leitura
     * manual do arquivo JSON gerado.</p>
     * 
     * @param dados objeto contendo todos os dados a serem persistidos
     */
    public void salvar(DadosDoSave dados){
        Gson gson = new GsonBuilder().setPrettyPrinting()
                                    .registerTypeAdapterFactory(criarAdaptadorDeCartas())
                                    .create();
                                    
        try (FileWriter writer = new FileWriter("saveAtual.json")){
            gson.toJson(dados, writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Carrega os dados do arquivo "saveAtual.json".
     * 
     * @return dados carregados do arquivo de save atual, ou null em caso de erro
     */
    private DadosDoSave carregarSaveAtual(){
        Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(criarAdaptadorDeCartas())
                    .create();

        try(FileReader reader = new FileReader("saveAtual.json")){
            DadosDoSave dadosCarregados = gson.fromJson(reader, DadosDoSave.class);

            return dadosCarregados;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Carrega os dados iniciais do arquivo "saveInicial.json".
     * 
     * <p>Este arquivo contém a configuração padrão do jogo,
     * utilizada quando não existe um save atual.</p>
     * 
     * @return dados carregados do arquivo de save inicial, ou null em caso de erro
     */
    private DadosDoSave carregarSaveInicial(){
        Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(criarAdaptadorDeCartas())
                    .create();

        try(FileReader reader = new FileReader("saveInicial.json")){
            DadosDoSave dadosCarregados = gson.fromJson(reader, DadosDoSave.class);

            return dadosCarregados;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Carrega os dados do jogo, decidindo automaticamente qual arquivo usar.
     * 
     * <p>Se o arquivo "saveAtual.json" existir, carrega dele.
     * Caso contrário, carrega do arquivo "saveInicial.json".</p>
     * 
     * @return dados carregados do arquivo apropriado, ou null se nenhum arquivo válido for encontrado
     */
    public DadosDoSave carregarSave(){
        File arquivoDeSaveAtual = new File("saveAtual.json");
        if (!arquivoDeSaveAtual.exists()){
        return carregarSaveInicial();
        } else {
            return carregarSaveAtual();
        }
    }

    public void apagarSaveAtual(){
        File arquivoDeSaveAtual = new File("saveAtual.json");
        if (arquivoDeSaveAtual.exists()){
            arquivoDeSaveAtual.delete();
        }
    }
}
