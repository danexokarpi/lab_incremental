package org.jogo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class GerenciadorDeJsonTest {

    private final File saveAtual = new File("saveAtual.json");
    private final File saveInicial = new File("saveInicial.json");

    private final File backupInicial = new File("saveInicial_backup_teste.json");
    private final File backupAtual = new File("saveAtual_backup_teste.json");

    @BeforeEach
    public void setUp() throws IOException {
        if (saveAtual.exists()) {
            Files.copy(saveAtual.toPath(), backupAtual.toPath(), StandardCopyOption.REPLACE_EXISTING);
            saveAtual.delete();
        }
        
        if (saveInicial.exists()) {
            Files.copy(saveInicial.toPath(), backupInicial.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (saveAtual.exists()) {
            saveAtual.delete();
        }
        if (saveInicial.exists()) {
            saveInicial.delete();
        }
        
        if (backupAtual.exists()) {
            Files.move(backupAtual.toPath(), saveAtual.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        if (backupInicial.exists()) {
            Files.move(backupInicial.toPath(), saveInicial.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // ###############################################################################
    // TESTES
    // ###############################################################################

    @Test
    public void testSalvarECarregar_GeraERecuperaSaveAtual() {
        GerenciadorDeJson gerenciador = new GerenciadorDeJson();
    
        DadosDoSave dadosParaSalvar = new DadosDoSave();
        
        gerenciador.salvar(dadosParaSalvar);
        
        assertTrue(saveAtual.exists(), "O método salvar deveria ter criado o arquivo saveAtual.json.");
        
        DadosDoSave dadosCarregados = gerenciador.carregarSave();
        assertNotNull(dadosCarregados, "Deveria conseguir carregar o objeto salvo no saveAtual.json.");
    }

    @Test
    public void testCarregarSave_FallbackParaSaveInicialQuandoAtualNaoExiste() throws IOException {
        GerenciadorDeJson gerenciador = new GerenciadorDeJson();

        if (saveAtual.exists()) saveAtual.delete();
        
        try (FileWriter writer = new FileWriter(saveInicial)) {
            writer.write("{ }"); 
        }

        DadosDoSave dadosCarregados = gerenciador.carregarSave();
        
        assertNotNull(dadosCarregados, "Deveria ter puxado os dados do saveInicial.json pois o atual não existe.");
    }

    @Test
    public void testCarregarSave_RetornaNullSeNenhumArquivoExistir() {
        GerenciadorDeJson gerenciador = new GerenciadorDeJson();
    
        if (saveAtual.exists()) saveAtual.delete();
        if (saveInicial.exists()) saveInicial.delete();
        
        DadosDoSave dadosCarregados = gerenciador.carregarSave();
        
        assertNull(dadosCarregados, "Se não houver nenhum arquivo de save, deveria retornar null.");
    }
}