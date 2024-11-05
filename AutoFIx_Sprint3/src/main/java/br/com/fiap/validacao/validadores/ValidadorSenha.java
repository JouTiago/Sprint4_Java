package br.com.fiap.validacao.validadores;

import java.util.regex.Pattern;

public class ValidadorSenha implements Validador {
    // Senha deve conter no mínimo uma letra minúscula, maiúscula, dígito, um caractere especial e 8 caracteres
    private static final Pattern SENHA_PATTERN = Pattern.compile
            ("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");


    // Métodos

    @Override
    public boolean validar(String senha) {
        return senha != null && SENHA_PATTERN.matcher(senha).matches();
    }
}
