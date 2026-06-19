package salon.util;

import javax.swing.text.MaskFormatter;

public class ValidadorUtil {

    // Verifica se os campos de texto estão vazios
    public static boolean camposPreenchidos(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Validação básica de CPF (verifica tamanho e caracteres repetidos)
    public static boolean isCpfValido(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove máscara
        
        if (cpf.length() != 11) return false;
        
        // Verifica se todos os números são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        // (Opcional: Inserir aqui o cálculo matemático completo dos 2 dígitos verificadores do CPF)
        return true;
    }

    // Retorna uma máscara pronta para usar em JFormattedTextField
    public static MaskFormatter getMascara(String mascara) {
        try {
            MaskFormatter formatter = new MaskFormatter(mascara);
            formatter.setPlaceholderCharacter('_');
            return formatter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}