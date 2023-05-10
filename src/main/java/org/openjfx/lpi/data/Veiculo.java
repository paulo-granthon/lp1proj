package org.openjfx.lpi.data;

public class Veiculo {
    String modelo;
    int ano;
    
    public Veiculo (
        String modelo,
        int ano
    ) {
        this.modelo = modelo;
        this.ano = ano;
    }

    public String getModelo() { return modelo; }
    public int getAno() { return ano; }

    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAno(int ano) { this.ano = ano; }
}
