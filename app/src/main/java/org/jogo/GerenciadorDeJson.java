package org.jogo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


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
                                    
        try (FileWriter writer = new FileWriter("save.json")){
            gson.toJson(dados, writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public DadosDoSave carregarSave(){
        Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(criarAdaptadorDeCartas())
                    .create();

        try(FileReader reader = new FileReader("save.json")){
            DadosDoSave dadosCarregados = gson.fromJson(reader, DadosDoSave.class);

            return dadosCarregados;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BancoDeDadosDoJogo carregarDadosDoJogo(){
        Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(criarAdaptadorDeCartas())
                        .create();
        try(FileReader reader = new FileReader("dadosDojogo.json")){
            BancoDeDadosDoJogo banco = gson.fromJson(reader, BancoDeDadosDoJogo.class);
            return banco;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
