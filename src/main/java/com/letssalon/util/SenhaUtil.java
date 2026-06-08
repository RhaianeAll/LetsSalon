package com.letssalon.util;

import javax.swing.*;
import java.awt.*;

public class SenhaUtil {

    /**
     * Adiciona um botão de olho ao lado do campo de senha
     * para alternar entre mostrar e ocultar a senha.
     *
     * @param txtSenha  campo de senha a ser controlado
     * @param painel    painel onde o campo está inserido
     * @param gbc       constraints do GridBagLayout
     * @param coluna    coluna onde o botão será adicionado
     */
    public static JButton criarBotaoOlho(JPasswordField txtSenha) {
        // Botão com ícone de olho para mostrar/ocultar senha
        JButton btnOlho = new JButton("👁");
        btnOlho.setPreferredSize(new Dimension(40, 28));
        btnOlho.setBackground(Color.WHITE);
        btnOlho.setBorderPainted(false);
        btnOlho.setFocusPainted(false);
        btnOlho.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOlho.setToolTipText("Mostrar/ocultar senha");

        // Alterna entre mostrar e ocultar a senha ao clicar
        btnOlho.addActionListener(e -> {
            if (txtSenha.getEchoChar() == 0) {
                // Oculta a senha
                txtSenha.setEchoChar('*');
                btnOlho.setText("👁");
            } else {
                // Mostra a senha
                txtSenha.setEchoChar((char) 0);
                btnOlho.setText("🔒");
            }
        });

        return btnOlho;
    }

    /**
     * Cria um painel com o campo de senha e o botão de olho juntos.
     * Facilita a inserção em qualquer tela.
     */
    public static JPanel criarPainelSenha(JPasswordField txtSenha) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);
        painel.add(txtSenha, BorderLayout.CENTER);
        painel.add(criarBotaoOlho(txtSenha), BorderLayout.EAST);
        return painel;
    }
}