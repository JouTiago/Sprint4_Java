package br.com.fiap.validacao.validadores;

import java.util.regex.Pattern;

public class ValidadorPlaca implements Validador {
    private static final Pattern PLACA_PATTERN = Pattern.compile("^[A-Z]{3}\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$");

    @Override
    public boolean validar(String placa) {
        return placa != null && PLACA_PATTERN.matcher(placa).matches();
    }
}
