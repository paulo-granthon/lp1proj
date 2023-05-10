package org.openjfx.lpi.db;

public class Query {
    public static void executeInsert (Pessoa p) {
        Connection conexao = new SQLConnection().connect();



        PreparedStatement statement = conexao.PreparedStatement("INSERT INTO pessoa (nome, genero, nascimento) values (?, ?, ?)");

        statement.

    }
}
