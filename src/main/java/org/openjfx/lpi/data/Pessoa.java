package org.openjfx.lpi.data;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class Pessoa {
    String nome;
    String genero;
    Date nascimento;
    public Pessoa (
        String nome,
        String genero,
        Date nascimento
    ) {
        this.nome = nome;
        this.genero = genero;
        this.nascimento = nascimento;
    }
    public String getNome() { return nome; }
    public String getGenero() { return genero; }
    public Date getNascimento() { return nascimento; }
    public String getNascimentoString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(nascimento);
    }
    
    public void setNome(String nome) { this.nome = nome; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setNascimento(Date nascimento) { this.nascimento = nascimento; }

}