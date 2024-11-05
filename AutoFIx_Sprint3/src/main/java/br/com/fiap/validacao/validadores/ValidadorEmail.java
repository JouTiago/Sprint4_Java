package br.com.fiap.validacao.validadores;

import java.util.regex.Pattern;

public class ValidadorEmail implements Validador {
    private static final Pattern EMAIL_PATTERN = Pattern.compile
            ("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$");


    // Métodos

    @Override
    public boolean validar(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

}
