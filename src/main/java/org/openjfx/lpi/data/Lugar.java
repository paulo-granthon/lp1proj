package org.openjfx.lpi.data;

public class Lugar {
    String pais;
    String estado;
    String cidade;

    public Lugar (
        String pais,
        String estado,
        String cidade
    ) {
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
    }

    public String getPais() { return pais; }
    public String getEstado() { return estado; }
    public String getCidade() { return cidade; }

    public void setPais(String pais) { this.pais = pais; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCidade(String cidade) { this.cidade = cidade; }

}
