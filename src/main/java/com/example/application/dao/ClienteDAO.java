package com.example.application.dao;

import com.example.application.data.connection.GetConnectionDatabase;
import com.example.application.data.entity.ClienteDTO;
import com.example.application.data.entity.ProdutoDTO;
import com.example.application.views.main.component.customField.Phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO { // DATA ACCESS OBJECT - DAO
    private static Connection connection = getConnection();
    private static Connection getConnection() {
        try {
            connection = GetConnectionDatabase.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return connection;
    }

    public static void adicionarCliente(ClienteDTO clienteDTO) throws SQLException {
        String sql = """
                    INSERT INTO cliente (nome, dataNascimento, cpf, telefone, email, cep, bairro, logradouro, numero,
                     complemento, cidade, estado)
                    VALUES (?,?,?,?,?,?,?,?,?,?,?,?);
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, clienteDTO.getNome());
            preparedStatement.setDate(2, Date.valueOf(clienteDTO.getDataNascimento()));
            preparedStatement.setString(3, clienteDTO.getCpf());
            preparedStatement.setString(4, clienteDTO.getTelefone().toString());
            preparedStatement.setString(5, clienteDTO.getEmail());
            preparedStatement.setString(6, clienteDTO.getCep());
            preparedStatement.setString(7, clienteDTO.getBairro());
            preparedStatement.setString(8, clienteDTO.getLogradouro());
            preparedStatement.setInt(9, clienteDTO.getNumero());
            preparedStatement.setString(10, clienteDTO.getComplemento() != null ? clienteDTO.getComplemento() : "");
            preparedStatement.setString(11, clienteDTO.getCidade());
            preparedStatement.setString(12, clienteDTO.getEstado());
            preparedStatement.executeUpdate();

        } catch (SQLException ex){
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement !=null){
                preparedStatement.close();
            }
        }
    }

    public static List<ClienteDTO> getClientes() throws SQLException {
        List<ClienteDTO> clientes = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = """
                SELECT * FROM cliente;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        try {
            while (resultSet.next()) {
                ClienteDTO clienteDTO = new ClienteDTO(); //  DATA TRANSFER OBJECT - DTO
                clienteDTO.setId(resultSet.getInt("id"));
                clienteDTO.setNome(resultSet.getString("nome"));
                clienteDTO.setDataNascimento(resultSet.getDate("dataNascimento").toLocalDate());
                clienteDTO.setCpf(resultSet.getString("cpf"));
                clienteDTO.setTelefone(criarInstanciaPhone(resultSet.getString("telefone")));
                clienteDTO.setEmail(resultSet.getString("email"));
                clienteDTO.setCep(resultSet.getString("cep"));
                clienteDTO.setBairro(resultSet.getString("bairro"));
                clienteDTO.setLogradouro(resultSet.getString("logradouro"));
                clienteDTO.setNumero(resultSet.getInt("numero"));
                clienteDTO.setComplemento(resultSet.getString("complemento"));
                clienteDTO.setCidade(resultSet.getString("cidade"));
                clienteDTO.setEstado(resultSet.getString("estado"));

                clientes.add(clienteDTO);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
        return clientes;
    }

    public static ClienteDTO buscaClienteId(Integer id, ClienteDTO clienteDTO) throws SQLException {
        ResultSet resultSet = null;
        Integer resultados = 0;
        String sql = """
                SELECT * FROM cliente WHERE id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();

        try {
            while (resultSet.next()) {
                resultados++;
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }

        if (resultados == 1){
            return clienteDTO;
        } else {
            throw new RuntimeException("Erro ao buscar cliente");
        }
    }

    public static void atualizaCliente(ClienteDTO clienteDTO) throws SQLException {
        String sql = """
                    UPDATE cliente SET nome = ?, dataNascimento = ?, cpf = ?, telefone = ?, email = ?, cep = ?, bairro = ?,
                    logradouro = ?, numero = ?, complemento = ?, cidade = ?, estado = ? WHERE id = ?;
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, clienteDTO.getNome());
            preparedStatement.setDate(2, Date.valueOf(clienteDTO.getDataNascimento()));
            preparedStatement.setString(3, clienteDTO.getCpf());
            preparedStatement.setString(4, clienteDTO.getTelefone().toString());
            preparedStatement.setString(5, clienteDTO.getEmail());
            preparedStatement.setString(6, clienteDTO.getCep());
            preparedStatement.setString(7, clienteDTO.getBairro());
            preparedStatement.setString(8, clienteDTO.getLogradouro());
            preparedStatement.setInt(9, clienteDTO.getNumero());
            preparedStatement.setString(10, clienteDTO.getComplemento() != null ? clienteDTO.getComplemento() : "");
            preparedStatement.setString(11, clienteDTO.getCidade());
            preparedStatement.setString(12, clienteDTO.getEstado());
            preparedStatement.setInt(13, clienteDTO.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex){
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement !=null){
                preparedStatement.close();
            }
        }
    }

    public static void excluirCliente(Integer id) throws SQLException {
        String sql = """
                    DELETE FROM cliente WHERE id = ?;
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex){
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement !=null){
                preparedStatement.close();
            }
        }
    }

    private static Phone criarInstanciaPhone(String phone) {
        String code = "";
        String telefone = "";

        String valorLimpo = phone.replaceAll("[()\\s-]+", "");

        code = valorLimpo.substring(0, 2);
        telefone = valorLimpo.substring(2);

        return new Phone(code, telefone);
    }

}
