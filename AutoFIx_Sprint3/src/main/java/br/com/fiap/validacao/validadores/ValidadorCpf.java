package br.com.fiap.validacao.validadores;

import java.util.regex.Pattern;

public class ValidadorCpf implements Validador {
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");


    // Métodos
    @Override
    public boolean validar(String cpf) {
        if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999"))
            return false;
        return CPF_PATTERN.matcher(cpf).matches() && verificarDigitos(cpf);
    }

    private boolean verificarDigitos(String cpf) {
        int sm = 0, peso = 10;
        for (int i = 0; i < 9; i++) {
            int num = cpf.charAt(i) - '0';
            sm += num * peso--;
        }
        int r = 11 - (sm % 11);
        char dig10 = (r == 10 || r == 11) ? '0' : (char) (r + '0');
        if (dig10 != cpf.charAt(9)) return false;

        sm = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            int num = cpf.charAt(i) - '0';
            sm += num * peso--;
        }
        r = 11 - (sm % 11);
        char dig11 = (r == 10 || r == 11) ? '0' : (char) (r + '0');
        return dig11 == cpf.charAt(10);
    }
}
