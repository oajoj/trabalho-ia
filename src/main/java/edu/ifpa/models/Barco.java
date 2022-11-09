package edu.ifpa.models;

import edu.ifpa.models.enums.Posicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Barco {
    private Integer peso;
    private Posicao posicao = Posicao.ESQUERDO;
    private Set<Pessoa> pessoas = new HashSet<>();
}

