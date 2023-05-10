package org.openjfx.lpi.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.openjfx.lpi.data.Lugar;
import org.openjfx.lpi.data.Pessoa;
import org.openjfx.lpi.data.Veiculo;

public class Query {
    public static void insertPessoa (Pessoa p) {
        try {
            Connection conexao = new SQLConnection().connect();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO pessoa (nome, genero, nascimento) values (?, ?, ?)");
            statement.setString(1, p.getNome());
            statement.setString(2, p.getGenero());
            statement.setDate(3, p.getNascimento());
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertLugar (Lugar p) {
        try {
            Connection conexao = new SQLConnection().connect();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO lugar (pais, estado, cidade) values (?, ?, ?)");
            statement.setString(1, p.getPais());
            statement.setString(2, p.getEstado());
            statement.setString(3, p.getCidade());
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertVeiculo (Veiculo p) {
        try {
            Connection conexao = new SQLConnection().connect();
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO veiculo (modelo, genero) values (?, ?)");
            statement.setString(1, p.getModelo());
            statement.setInt(2, p.getAno());
            statement.execute();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeSqlFile (String file_path) {
        try {
            Connection conexao = new SQLConnection().connect();
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            StringBuilder sb = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append(System.lineSeparator());
            }
            conexao.createStatement().execute(sb.toString());
            br.close();
            conexao.commit();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
