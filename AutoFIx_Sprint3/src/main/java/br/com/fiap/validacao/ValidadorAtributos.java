package br.com.fiap.validacao;

import br.com.fiap.model.cadastro.Cliente;
import br.com.fiap.model.cadastro.Veiculo;
import br.com.fiap.model.login.LoginCliente;
import br.com.fiap.model.login.LoginOficina;
import br.com.fiap.model.cadastro.Oficina;
import br.com.fiap.validacao.validadores.*;


public class ValidadorAtributos {

    public void validar(Object objeto) throws Exception {

        if (objeto instanceof Cliente cliente) {

            ValidadorCpf validadorCpf = new ValidadorCpf();
            if (!validadorCpf.validar(cliente.getCpf())) {
                throw new Exception("CPF inválido: " + cliente.getCpf());
            }

            ValidadorTelefone validadorTelefone = new ValidadorTelefone();
            if (!validadorTelefone.validar(cliente.getTelefone())) {
                throw new Exception("Telefone inválido: " + cliente.getTelefone());
            }

        } else if (objeto instanceof LoginCliente loginCliente) {

            ValidadorEmail validadorEmail = new ValidadorEmail();
            if (!validadorEmail.validar(loginCliente.getEmail())) {
                throw new Exception("Email inválido: " + loginCliente.getEmail());
            }

            ValidadorSenha validadorSenha = new ValidadorSenha();
            if (!validadorSenha.validar(loginCliente.getSenha())) {
                throw new Exception("Senha inválida: " + loginCliente.getSenha());
            }

        } else if (objeto instanceof Oficina oficina) {

            ValidadorTelefone validadorTelefone = new ValidadorTelefone();
            if (!validadorTelefone.validar(oficina.getTelefone())) {
                throw new Exception("Telefone inválido: " + oficina.getTelefone());
            }

            ValidadorCnpj validadorCnpj = new ValidadorCnpj();
            if (!validadorCnpj.validar(oficina.getCnpj())) {
                throw new Exception("CNPJ inválido: " + oficina.getCnpj());
            }

        } else if (objeto instanceof LoginOficina LoginOficina) {

            ValidadorEmail validadorEmail = new ValidadorEmail();
            if (!validadorEmail.validar(LoginOficina.getEmail())) {
                throw new Exception("Email inválido: " + LoginOficina.getEmail());
            }

            ValidadorSenha validadorSenha = new ValidadorSenha();
            if (!validadorSenha.validar(LoginOficina.getSenha())) {
                throw new Exception("Senha inválida: " + LoginOficina.getSenha());
            }

        } else if (objeto instanceof Veiculo) {
            Veiculo veiculo = (Veiculo) objeto;

            ValidadorPlaca validadorPlaca = new ValidadorPlaca();
            if (!validadorPlaca.validar(veiculo.getPlaca())) {
                throw new Exception("Placa inválida: " + veiculo.getPlaca());
            }
        }
    }
    }

