package com.letssalon.util;

public class GerarSenha {

    public static void main(String[] args) {

        String senha = "123456";

        String senhaCriptografada =
                SenhaUtil.criptografarSenha(senha);

        System.out.println("Senha original: " + senha);
        System.out.println("Senha criptografada:");
        System.out.println(senhaCriptografada);

    }

}