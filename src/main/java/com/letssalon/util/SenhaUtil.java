package com.letssalon.util;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;

public class SenhaUtil {

    // BCrypt
    
    // Criptografa a senha
    public static String criptografarSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // Verifica se a senha digitada corresponde à senha do banco
    public static boolean verificarSenha(String senhaDigitada, String senhaBanco) {
        return BCrypt.checkpw(senhaDigitada, senhaBanco);
    }

    // Mostrar/Ocultar senha

    public static JButton criarBotaoOlho(JPasswordField txtSenha) {

        JButton btnOlho = new JButton("Mostrar");

        btnOlho.setPreferredSize(new Dimension(90, 28));
        btnOlho.setBackground(Color.WHITE);
        btnOlho.setFocusPainted(false);
        btnOlho.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnOlho.addActionListener(e -> {

            // Se senha estiver visível
            if (txtSenha.getEchoChar() == (char) 0) {

                // Oculta
                txtSenha.setEchoChar('•');
                btnOlho.setText("Mostrar");

            } else {

                // Mostra
                txtSenha.setEchoChar((char) 0);
                btnOlho.setText("Ocultar");
            }
        });

        return btnOlho;
    }

    // Cria painel com campo + botão
    public static JPanel criarPainelSenha(JPasswordField txtSenha) {

        JPanel painel = new JPanel(new BorderLayout());

        painel.setOpaque(false);

        // Define caractere padrão oculto
        txtSenha.setEchoChar('•');

        painel.add(txtSenha, BorderLayout.CENTER);
        painel.add(criarBotaoOlho(txtSenha), BorderLayout.EAST);

        return painel;
    }
}
