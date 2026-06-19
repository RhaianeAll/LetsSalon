package salon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {
    
    // Altere com os dados do seu pgAdmin local
    private static final String URL = "jdbc:postgresql://localhost:5432/lets_salon";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin"; // Coloque a senha do seu banco

    /**
     * Estabelece e retorna a conexão com o banco de dados PostgreSQL.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Em caso de erro, já mostramos um popup visual elegante avisando o problema
            JOptionPane.showMessageDialog(null, 
                "Erro ao conectar com o banco de dados Let's Salon:\n" + e.getMessage(), 
                "Erro de Conexão", 
                JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Erro de conexão", e);
        }
    }
    
    /**
     * Método para fechar a conexão de forma segura
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    
    
}