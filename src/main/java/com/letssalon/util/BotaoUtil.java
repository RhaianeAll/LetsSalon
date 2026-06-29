/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letssalon.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author casaril.jessica
 */
public class BotaoUtil {

    public static JButton criarBotao(String texto, Color corFundo) {

        JButton btn = new JButton(texto);

        btn.setBackground(corFundo);

        // usa suas cores já existentes
        if (corFundo.equals(CoresUtil.CINZA_VOLTAR)) {
            btn.setForeground(CoresUtil.CINZA_VOLTAR_TEXTO);

        } else if (corFundo.equals(CoresUtil.VERDE_CONFIRMAR)) {
            btn.setForeground(CoresUtil.VERDE_CONFIRMAR_TEXTO);

        } else {
            btn.setForeground(CoresUtil.TEXTO_CLARO);
        }

        btn.setFont(new Font("SansSerif", Font.BOLD, 12));

        // Compatibilidade Mac/Windows/Linux
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }
}