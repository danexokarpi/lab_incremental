package org.jogo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;



public class GerenciadorDeJson {

    private RuntimeTypeAdapterFactory<Carta> criarAdaptadorDeCartas(){
        return RuntimeTypeAdapterFactory.of(Carta.class, "tipo_carta")
                .registerSubtype(CartaDano.class, "Dano")
                .registerSubtype(CartaEscudo.class, "Escudo")
                .registerSubtype(CartaEfeito.class, "Efeito");
    }


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

    public DadosDoSave carregarSave(){
        File arquivoDeSaveAtual = new File("saveAtual.json");
        if (!arquivoDeSaveAtual.exists()){
        return carregarSaveInicial();
        } else {
            return carregarSaveAtual();
        }
    }

}
