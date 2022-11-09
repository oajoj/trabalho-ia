package edu.ifpa;

import edu.ifpa.models.Barco;
import edu.ifpa.models.Pessoa;
import edu.ifpa.models.enums.Posicao;
import edu.ifpa.models.enums.TipoPessoa;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {
    private List<Pessoa> ladoDireito = new ArrayList<>();
    private List<Pessoa> ladoEsquerdo = new ArrayList<>();
    private Barco barco = new Barco();

    public Runner(){
        var mae = Pessoa.builder()
                .peso(100)
                .tipo(TipoPessoa.MAE)
                .build();

        var pai = Pessoa.builder()
                .peso(100)
                .tipo(TipoPessoa.PAI)
                .build();

        var filho = Pessoa.builder()
                .peso(50)
                .tipo(TipoPessoa.FILHO)
                .build();

        var filha = Pessoa.builder()
                .peso(50)
                .tipo(TipoPessoa.FILHA)
                .build();

        ladoEsquerdo.addAll(List.of(mae, pai, filho, filha));
    }

    public void run() {
        String LE = "";
        String LD = "";
        while(CollectionUtils.isNotEmpty(ladoEsquerdo) && ladoDireito.size() < 4){
            var quantidadeJogadores = sortearQuantidadeJogadores();
            sortearPessoasNoBarco(quantidadeJogadores, ladoEsquerdo);
            moverEDesembarcar(Posicao.DIREITO);
            if(ladoDireito.size() == 4){
                break;
            }
            sortearPessoasNoBarco(1, ladoDireito);
            moverEDesembarcar(Posicao.ESQUERDO);
        }

        LE = getString(ladoEsquerdo);
        LD = getString(ladoDireito);
        System.out.println("LE["+ LE +"] LD["+ LD +"]");
    }

    private String getString(List<Pessoa> pessoas) {
        String string = "";
        for(Pessoa pessoa : pessoas){
            string += " " + pessoa.getTipo() + " ";
        }
        return string;
    }

    private void moverEDesembarcar(Posicao posicao) {
        String LE = "";
        String LD = "";
        String BC = "";
        LE = getString(ladoEsquerdo);
        LD = getString(ladoDireito);
        BC = getString(barco.getPessoas().stream().collect(Collectors.toList()));

        System.out.println("LE["+ LE +"] BC["+BC+"] LD["+ LD +"]");
        switch (posicao){
            case DIREITO:
                barco.setPosicao(Posicao.DIREITO);
                ladoDireito.addAll(barco.getPessoas());
                barco.getPessoas().clear();
                break;
            case ESQUERDO:
                barco.setPosicao(Posicao.ESQUERDO);
                ladoEsquerdo.addAll(barco.getPessoas());
                barco.getPessoas().clear();
                break;
        }
    }

    private void sortearPessoasNoBarco(Integer quantidadeJogadores, List<Pessoa> pessoas) {
        System.out.println("sorteando");
        var flag = true;
        var pessoa = pessoas.get(
                new Random().nextInt(pessoas.size())
        );

        if(quantidadeJogadores > 1 && pessoa.getPeso() < 100){
            Pessoa pessoa2;
            do {
                pessoa2 = pessoas.get(
                        new Random().nextInt(pessoas.size())
                );
                flag = pessoa2.equals(pessoa) || pessoa2.getPeso() + pessoa.getPeso() > 100;
            }while(flag);
            pessoas.remove(pessoa2);
            barco.getPessoas().add(pessoa2);
        }
        pessoas.remove(pessoa);
        barco.getPessoas().add(pessoa);
    }

    private Integer sortearQuantidadeJogadores() {
        return new Random().nextInt(2) + 1;
    }
}
