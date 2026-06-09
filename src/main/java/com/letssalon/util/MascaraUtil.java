package com.letssalon.util;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class MascaraUtil {

    // Máscara CPF
    public static JFormattedTextField criarCampoCPF() {

        try {

            MaskFormatter mascara = new MaskFormatter("###.###.###-##");
            mascara.setPlaceholderCharacter('_');

            return new JFormattedTextField(mascara);

        } catch (ParseException e) {

            return new JFormattedTextField();
        }
    }

    // Máscara telefone
    public static JFormattedTextField criarCampoTelefone() {

        try {

            MaskFormatter mascara = new MaskFormatter("(##) #####-####");
            mascara.setPlaceholderCharacter('_');

            return new JFormattedTextField(mascara);

        } catch (ParseException e) {

            return new JFormattedTextField();
        }
    }
}
