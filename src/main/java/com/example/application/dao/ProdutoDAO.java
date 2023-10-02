package com.example.application.dao;

import com.example.application.data.connection.GetConnectionDatabase;
import com.example.application.data.entity.ProdutoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private static Connection connection = getConnection();
    private static Connection getConnection() {
        try {
            connection = GetConnectionDatabase.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return connection;
    }

    public static void adicionarProduto(ProdutoDTO produtoDTO) throws SQLException {
        String sql = """
                    INSERT INTO produto (nome, preco)
                    VALUES (?,?);
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, produtoDTO.getNome());
            preparedStatement.setDouble(2, produtoDTO.getPreco());
            preparedStatement.executeUpdate();

        } catch (SQLException ex){
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement !=null){
                preparedStatement.close();
            }
        }
    }

    public static void atualizarProduto(ProdutoDTO produtoDTO) throws SQLException {
        String sql = """
                    UPDATE produto SET nome = ?, preco = ?
                    WHERE id = ?;
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, produtoDTO.getNome());
            preparedStatement.setDouble(2, produtoDTO.getPreco());
            preparedStatement.setInt(3, produtoDTO.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex){
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement !=null){
                preparedStatement.close();
            }
        }
    }

    public static void deletarProduto(Integer id) throws SQLException {
        String sql = """
                    DELETE FROM produto WHERE id = ?;
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

    public static List<ProdutoDTO> listarProdutos() throws SQLException {
        List<ProdutoDTO> produtos = new ArrayList<>();
        ResultSet resultSet = null;
        String sql = """
                SELECT * FROM produto;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        try {
            while (resultSet.next()) {
                ProdutoDTO produtoDTO = new ProdutoDTO();
                produtoDTO.setId(resultSet.getInt("id"));
                produtoDTO.setNome(resultSet.getString("nome"));
                produtoDTO.setPreco(resultSet.getDouble("preco"));

                produtos.add(produtoDTO);
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
        return produtos;
    }

    public static ProdutoDTO buscarProdutoPorId(Integer id, ProdutoDTO produtoDTO) throws SQLException {
        ResultSet resultSet = null;
        Integer resultados = 0;
        String sql = """
                SELECT * FROM produto WHERE id = ?;
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
        return produtoDTO;
    }
}
