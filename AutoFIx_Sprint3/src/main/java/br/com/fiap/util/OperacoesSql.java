package br.com.fiap.util;

import br.com.fiap.config.DatabaseConfig;
import br.com.fiap.model.cadastro.Cliente;
import br.com.fiap.model.cadastro.Oficina;
import br.com.fiap.model.cadastro.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperacoesSql {

    //Gerar o SQL de insert com base nas colunas recebidas
    public static String gerarInsertTabela(String nomeTabela, String[] colunas) {
        StringBuilder colunasTabela = new StringBuilder();
        StringBuilder valoresTabela = new StringBuilder();

        for (int i = 0; i < colunas.length; i++) {
            colunasTabela.append(colunas[i]);
            valoresTabela.append("?");

            if (i < colunas.length - 1) {
                colunasTabela.append(", ");
                valoresTabela.append(", ");
            }
        }
        return "INSERT INTO "+nomeTabela+" ("+colunasTabela+") VALUES ("+valoresTabela+")";
    }


    //Realizar a inserção no banco de dados
    public static boolean inserirNoBanco(String tabela, Object[] valores, String sqlInsert) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {

            for (int i = 0; i < valores.length; i++) {
                preparedStatement.setObject(i + 1, valores[i]);
            }

            int colunasAfetadas = preparedStatement.executeUpdate();
            return colunasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir na tabela " + tabela + ": " + e.getMessage());
            return false;
        }
    }


    //Verificar se login é válido no banco de dados
    public static boolean verificaLogin(String email, String senha) {
        String sql = "SELECT * FROM T_LOGIN_CLIENTE WHERE login_cliente = ? AND senha_cliente = ?";
        boolean loginValido = false;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loginValido = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar login: " + e.getMessage());
        }

        return loginValido;
    }


    //Fazer uma consulta no banco e retornar os resultados
    public static List<Object[]> executarConsulta(String sql, Object[] parametros) {
        List<Object[]> resultados = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                preparedStatement.setObject(i + 1, parametros[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int numeroColunas = metaData.getColumnCount();

                while (resultSet.next()) {
                    Object[] linha = new Object[numeroColunas];
                    for (int i = 0; i < numeroColunas; i++) {
                        linha[i] = resultSet.getObject(i + 1);
                    }
                    resultados.add(linha);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao fazer consulta: " + e.getMessage());
        }

        return resultados;
    }


    // Dar um update no banco de dados
    public static boolean executarUpdate(String tabela, String[] colunas, Object[] valores, String condicao, Object[] parametrosCondicao) {
        StringBuilder sql = new StringBuilder("UPDATE " + tabela + " SET ");
        for (int i = 0; i < colunas.length; i++) {
            sql.append(colunas[i]).append(" = ?");
            if (i < colunas.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE ").append(condicao);

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < valores.length; i++) {
                preparedStatement.setObject(i + 1, valores[i]);
            }

            for (int i = 0; i < parametrosCondicao.length; i++) {
                preparedStatement.setObject(colunas.length + i + 1, parametrosCondicao[i]);
            }

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao executar update na tabela " + tabela + ": " + e.getMessage());
            return false;
        }
    }


    //Buscar um cliente pelo CPF
    public static Cliente buscarClientePorCpf(String cpf) {
        String sql = "SELECT * FROM T_CLIENTE WHERE c_cpf = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cpf);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("c_nome");
                    String telefone = resultSet.getString("c_telefone");
                    String endereco = resultSet.getString("c_endereco");
                    String cidade = resultSet.getString("c_cidade");
                    String estado = resultSet.getString("c_estado");
                    String cep = resultSet.getString("c_cep");
                    return new Cliente(cpf, nome, telefone, endereco, cidade, estado, cep);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente pelo CPF: " + e.getMessage());
        }
        return null;
    }


    //Ver se o cliente já existe pelo CPF
    public static boolean clienteExiste(String tabela, String cpf) {
        String sql = "SELECT COUNT(*) AS total FROM " + tabela + " WHERE c_cpf = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cpf);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência do cliente: " + e.getMessage());
        }
        return false;
    }


    //Ver se o login inserido pelo cliente já está cadastrado
    public static boolean loginExiste(String tabela, String email) {
        String sql = "SELECT COUNT(*) AS total FROM " + tabela + " WHERE login_cliente = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência do login: " + e.getMessage());
        }
        return false;
    }


    //Deletar uma entrada do banco
    public static boolean executarDelete(String tabela, String condicao, Object[] parametrosCondicao) {
        String sqlDelete = "DELETE FROM " + tabela + " WHERE " + condicao;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {

            for (int i = 0; i < parametrosCondicao.length; i++) {
                preparedStatement.setObject(i + 1, parametrosCondicao[i]);
            }

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover entrada" + tabela + ": " + e.getMessage());
            return false;
        }
    }

    //Verificar se a oficina já está cadastrada pelo CNPJ
    public static boolean oficinaExiste(String tabela, String cnpj) {
        String sql = "SELECT COUNT(*) AS total FROM " + tabela + " WHERE o_cnpj = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cnpj);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar existência da oficina: " + e.getMessage());
        }
        return false;
    }

    //Buscar uma oficina pelo CNPJ
    public static Oficina buscarOficinaPorCnpj(String cnpj) {
        String sql = "SELECT * FROM T_OFICINA WHERE o_cnpj = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cnpj);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("o_nome");
                    String telefone = resultSet.getString("o_telefone");
                    String endereco = resultSet.getString("o_endereco");
                    String cidade = resultSet.getString("o_cidade");
                    String estado = resultSet.getString("o_estado");
                    String cep = resultSet.getString("o_cep");
                    return new Oficina(cnpj, nome, telefone, endereco, cidade, estado, cep);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar oficina pelo CNPJ: " + e.getMessage());
        }
        return null;
    }


    // Buscar um veículo pela placa
    public static Veiculo buscarVeiculoPorPlaca(String placa) {
        String sql = "SELECT * FROM T_VEICULO WHERE v_placa = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, placa);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String marca = resultSet.getString("v_marca");
                    String modelo = resultSet.getString("v_modelo");
                    int ano = resultSet.getInt("v_ano");
                    String combustivel = resultSet.getString("v_combustivel");
                    String transmissao = resultSet.getString("v_transmissao");
                    int quilometragem = resultSet.getInt("v_km");
                    String modificacoes = resultSet.getString("v_modificacoes");
                    String cpfCliente = resultSet.getString("c_cpf");
                    Cliente cliente = buscarClientePorCpf(cpfCliente);

                    return new Veiculo(placa, marca, modelo, ano, combustivel, transmissao, quilometragem, modificacoes, cliente);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar veículo pela placa: " + e.getMessage());
        }

        return null;
    }
}
