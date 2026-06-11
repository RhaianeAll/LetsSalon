//oiii

package com.letssalon.letssalon;

import com.letssalon.util.Conexao;
import com.letssalon.view.TelaSelecao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // 1. Testa a conexão com o banco de dados PostgreSQL antes de abrir o sistema
        System.out.println("Tentando conectar ao banco de dados PostgreSQL...");
        Connection conexaoTeste = Conexao.getConnection();
        
        if (conexaoTeste != null) {
            System.out.println("Conexão estabelecida com sucesso!");
            
            try {
                // Fecha a conexão de teste para liberar o banco de dados
                conexaoTeste.close(); 
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão de teste: " + e.getMessage());
            }

            // 2. Ponto de entrada do sistema - Abre a tela de seleção de perfil
            SwingUtilities.invokeLater(() -> {
                new TelaSelecao().setVisible(true);
            });
            
        } else {
            // Se a conexão falhar, avisa o usuário visualmente e encerra o sistema
            System.err.println("Falha crítica: Não foi possível conectar ao banco de dados PostgreSQL.");
            JOptionPane.showMessageDialog(null, 
                "Não foi possível conectar ao banco de dados PostgreSQL (lets_salon).\n" +
                "Certifique-se de que o serviço do PostgreSQL está ativo e rodando no seu computador.", 
                "Erro de Conexão", 
                JOptionPane.ERROR_MESSAGE);
            
            System.exit(1); 
        }
    }
}