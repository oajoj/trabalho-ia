package edu.ifpa.models.enums;

import lombok.Getter;

@Getter
public enum TipoPessoa {
    MAE(100),
    PAI(100),
    FILHO(50),
    FILHA(50);
    private final Integer peso;

    TipoPessoa(int peso) {
        this.peso = peso;
    }
}
