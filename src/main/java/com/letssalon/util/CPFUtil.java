package com.letssalon.util;

public class CPFUtil {

    public static boolean validarCPF(String cpf) {

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica tamanho
        if (cpf.length() != 11) {
            return false;
        }

        // Impede CPFs repetidos
        if (
            cpf.equals("00000000000") ||
            cpf.equals("11111111111") ||
            cpf.equals("22222222222") ||
            cpf.equals("33333333333") ||
            cpf.equals("44444444444") ||
            cpf.equals("55555555555") ||
            cpf.equals("66666666666") ||
            cpf.equals("77777777777") ||
            cpf.equals("88888888888") ||
            cpf.equals("99999999999")
        ) {
            return false;
        }

        try {

            // Primeiro dígito
            int soma = 0;

            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }

            int resto = 11 - (soma % 11);

            int digito1 =
                (resto >= 10) ? 0 : resto;

            // Segundo dígito
            soma = 0;

            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }

            resto = 11 - (soma % 11);

            int digito2 =
                (resto >= 10) ? 0 : resto;

            // Verifica
            return
                digito1 == (cpf.charAt(9) - '0') &&
                digito2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {

            return false;
        }
    }
}