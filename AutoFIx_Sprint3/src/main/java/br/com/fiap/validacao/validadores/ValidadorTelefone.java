package br.com.fiap.validacao.validadores;

import java.util.regex.Pattern;

public class ValidadorTelefone implements Validador {
    private static final Pattern telefone_pattern = Pattern.compile(
            "^((\\+\\d{1,3}\\s?)?\\(?\\d{2}\\)?\\s?)?9?\\d{4}-?\\d{4}$"
    );

    @Override
    public boolean validar(String telefone) {
        return telefone != null && telefone_pattern.matcher(telefone).matches();
    }
}

