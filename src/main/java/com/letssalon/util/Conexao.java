package com.letssalon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class Conexao {

    private static String URL;
    private static String USUARIO;
    private static String SENHA;

    static {
        try {
            Properties props = new Properties();

            InputStream input = Conexao.class.getClassLoader().getResourceAsStream("db.properties");

            if (input != null) {

                props.load(input);

                URL = props.getProperty("db.url");
                USUARIO = props.getProperty("db.usuario");
                SENHA = props.getProperty("db.senha");

            } else {
                System.out.println("Arquivo db.properties não encontrado.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo de configuração: " + e.getMessage());
        }
    }

    public static Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (ClassNotFoundException e) {
            System.out.println("Driver do PostgreSQL não encontrado: " + e.getMessage());
            return null;

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao PostgreSQL: " + e.getMessage());
            return null;
        }
    }

    public static Connection getConnection() {
        return conectar();
    }
}