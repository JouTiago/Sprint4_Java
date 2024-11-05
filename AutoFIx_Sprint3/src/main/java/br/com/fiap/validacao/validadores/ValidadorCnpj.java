package br.com.fiap.validacao.validadores;

import java.util.regex.Pattern;

public class ValidadorCnpj implements Validador{
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");


    // Métodos
    @Override
    public boolean validar(String cnpj) {
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999"))
            return false;
        return CNPJ_PATTERN.matcher(cnpj).matches() && verificarDigitos(cnpj);
    }

    private boolean verificarDigitos(String cnpj) {
        char dig13, dig14;
        int sm, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (int i = 11; i >= 0; i--) {
                num = cnpj.charAt(i) - '0';
                sm += num * peso++;
                if (peso == 10) peso = 2;
            }
            r = sm % 11;
            dig13 = (r < 2) ? '0' : (char) ((11 - r) + '0');

            sm = 0;
            peso = 2;
            for (int i = 12; i >= 0; i--) {
                num = cnpj.charAt(i) - '0';
                sm += num * peso++;
                if (peso == 10) peso = 2;
            }
            r = sm % 11;
            dig14 = (r < 2) ? '0' : (char) ((11 - r) + '0');

            return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));

        } catch (Exception e) {
            return false;
        }
    }
}
