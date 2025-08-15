package br.com.investimento.model;

import java.util.*;
import br.com.investimento.Mensagens;

public class FibonacciCalculo {

    private Scanner scanner;
    private Mensagens mensagens;

    public FibonacciCalculo(Scanner scanner, Mensagens mensagens) {
        this.scanner = scanner;
        this.mensagens = mensagens;
    }

    public void executar() {
        double[] retracaoPadrao = {0.236, 0.382, 0.5, 0.618, 0.786};
        double[] expansaoPadrao = {1.272, 1.618, 2.0, 2.618, 4.236};

        System.out.println("=== " + mensagens.get("fibonacci.titulo") + " ===");

        System.out.print(mensagens.get("fibonacci.tipoMovimento"));
        int tipoMovimento = scanner.nextInt();

        System.out.print(mensagens.get("fibonacci.tipoFibonacci"));
        int tipoFibonacci = scanner.nextInt();

        double[] valoresPadrao = (tipoFibonacci == 1) ? retracaoPadrao : expansaoPadrao;

        System.out.print(mensagens.get("fibonacci.adicionar"));
        char adicionar = scanner.next().toLowerCase().charAt(0);

        List<Double> listaValores = new ArrayList<>();
        for (double v : valoresPadrao) listaValores.add(v);

        if (adicionar == 's') {
            System.out.print(mensagens.get("fibonacci.qtdAdicionar"));
            int qtd = scanner.nextInt();
            for (int i = 0; i < qtd; i++) {
                System.out.print(mensagens.get("fibonacci.digiteValor") + " " + (i + 1) + ": ");
                listaValores.add(scanner.nextDouble());
            }
        }

        listaValores.sort(Double::compareTo);

        System.out.println("\n" + mensagens.get("fibonacci.valoresFinais") + ":");
        for (double valor : listaValores) {
            System.out.println(valor);
        }

        System.out.print(mensagens.get("fibonacci.precoInicial"));
        double precoInicial = scanner.nextDouble();
        System.out.print(mensagens.get("fibonacci.precoFinal"));
        double precoFinal = scanner.nextDouble();

        double diferenca;
        double precoCalculado;

        System.out.println("\n" + mensagens.get("fibonacci.niveisCalculados") + ":");

        for (double nivel : listaValores) {
            if (tipoMovimento == 1 && tipoFibonacci == 1) {
                diferenca = precoFinal - precoInicial;
                precoCalculado = precoFinal - (diferenca * nivel);
            } else if (tipoMovimento == 2 && tipoFibonacci == 1) {
                diferenca = precoInicial - precoFinal;
                precoCalculado = precoFinal + (diferenca * nivel);
            } else if (tipoMovimento == 1 && tipoFibonacci == 2) {
                diferenca = precoFinal - precoInicial;
                precoCalculado = precoFinal + (diferenca * nivel);
            } else {
                diferenca = precoInicial - precoFinal;
                precoCalculado = precoFinal - (diferenca * nivel);
            }

            System.out.printf("Nível %.3f -> %.2f%n", nivel, precoCalculado);
        }
    }
}
