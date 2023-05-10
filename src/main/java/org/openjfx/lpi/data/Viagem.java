package org.openjfx.lpi.data;

import java.util.List;

public class Viagem {

    List<Pessoa> pessoas;
    Lugar lugar;
    Veiculo veiculo;
    
    public Viagem (
        List<Pessoa> pessoas,
        Lugar lugar,
        Veiculo veiculo
    ) {
        this.pessoas = pessoas;
        this.lugar = lugar;
        this.veiculo = veiculo;
    }

    
}
