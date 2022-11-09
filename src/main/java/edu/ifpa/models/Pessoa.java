package edu.ifpa.models;

import edu.ifpa.models.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pessoa {
    private Integer peso;
    private TipoPessoa tipo;
}
