package com.letssalon.util;

public class GerarSenha {

    public static void main(String[] args) {

        if (args.length == 0) {

            System.out.println("----------------------------------------");
            System.out.println("Gerador de Senhas BCrypt");
            System.out.println("----------------------------------------");
            System.out.println("Uso:");
            System.out.println("java GerarSenha <senha>");
            System.out.println();
            System.out.println("Exemplo:");
            System.out.println("java GerarSenha 123456");

            return;
        }

        String senha = args[0];

        String senhaCriptografada =
                SenhaUtil.criptografarSenha(senha);

        System.out.println("----------------------------------------");
        System.out.println("Senha original:");
        System.out.println(senha);
        System.out.println();
        System.out.println("Hash BCrypt:");
        System.out.println(senhaCriptografada);
        System.out.println("----------------------------------------");
    }
}