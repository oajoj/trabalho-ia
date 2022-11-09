package edu.ifpa;

import edu.ifpa.models.Barco;
import edu.ifpa.models.Pessoa;
import edu.ifpa.models.enums.Posicao;
import edu.ifpa.models.enums.TipoPessoa;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {
    private List<Pessoa> ladoDireito = new ArrayList<>();
    private List<Pessoa> ladoEsquerdo = new ArrayList<>();
    private Barco barco = new Barco();
    private AtomicInteger count = new AtomicInteger(0);

    public Runner(){
        for (TipoPessoa tipo : TipoPessoa.values()) {
            var pessoa = Pessoa.builder()
                    .peso(tipo.getPeso())
                    .tipo(tipo)
                    .build();
            ladoEsquerdo.add(pessoa);

        }
    }

    public void run() {
        String LE = "";
        String LD = "";
        while(CollectionUtils.isNotEmpty(ladoEsquerdo) && ladoDireito.size() < 4){
            count.addAndGet(1);
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
        System.out.println("Lado Esquerdo: ["+ LE +"]\nLado direito: ["+ LD +"]");
        System.out.println("Movimentos: "+count);
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
        BC = getString(new ArrayList<>(barco.getPessoas()));

        System.out.println("Lado Esquerdo: ["+ LE +"]\nBarco: ["+BC+"]\nLado direito: ["+ LD +"]");
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
        count.addAndGet(barco.getPessoas().size());
    }

    private void sortearPessoasNoBarco(Integer quantidadeJogadores, List<Pessoa> pessoas) {
        var flag = true;
        var pessoa = pessoas.get(
                new Random().nextInt(pessoas.size())
        );

        if(quantidadeJogadores > 1 || pessoa.getPeso() < 100){
            Pessoa pessoa2;
            while(flag){
                count.addAndGet(1);
                pessoa2 = pessoas.get(
                        new Random().nextInt(pessoas.size())
                );
                if(pessoa2.equals(pessoa) || pessoa2.getPeso() + pessoa.getPeso() > 100){
                    break;
                }
                pessoas.remove(pessoa2);
                barco.getPessoas().add(pessoa2);
            }
        }
        pessoas.remove(pessoa);
        barco.getPessoas().add(pessoa);
    }

    private Integer sortearQuantidadeJogadores() {
        return new Random().nextInt(2) + 1;
    }
}
