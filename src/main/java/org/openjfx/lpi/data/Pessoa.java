package org.openjfx.lpi.data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Pessoa {
    String nome;
    String genero;
    LocalDate nascimento;
    public Pessoa (
        String nome,
        String genero,
        LocalDate nascimento
    ) {
        this.nome = nome;
        this.genero = genero;
        this.nascimento = nascimento;
    }
    public String getNome() { return nome; }
    public String getGenero() { return genero; }
    public LocalDate getNascimento() { return nascimento; }
    public String getNascimentoString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(nascimento);
    }
    
    public void setNome(String nome) { this.nome = nome; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setNascimento(LocalDate nascimento) { this.nascimento = nascimento; }

}