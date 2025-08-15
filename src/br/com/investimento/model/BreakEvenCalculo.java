package br.com.investimento.model;

import br.com.investimento.Mensagens;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class BreakEvenCalculo {

    private final Scanner scanner;
    private final Mensagens msg;
    private static final MathContext MC = new MathContext(34, RoundingMode.HALF_UP);
    private static final BigDecimal CEM = new BigDecimal("100");

    public BreakEvenCalculo(Scanner scanner, Mensagens mensagens) {
        this.scanner = scanner;
        this.msg = mensagens;
    }

    public void executar() {
        System.out.println("\n=== " + msg.get("bep.titulo") + " ===");

        System.out.print(msg.get("bep.perguntaIgual"));
        String igual = scanner.nextLine().trim().toLowerCase();

        BigDecimal taxaCompra;
        BigDecimal taxaVenda;

        if (igual.startsWith("s") || igual.startsWith("y") || igual.startsWith("o")) {
            taxaCompra = lerPercentual(msg.get("bep.taxaUnica"));
            taxaVenda  = taxaCompra;
        } else {
            taxaCompra = lerPercentual(msg.get("bep.taxaCompra"));
            taxaVenda  = lerPercentual(msg.get("bep.taxaVenda"));
        }

        BigDecimal precoCompra = lerDecimal(msg.get("bep.precoCompra"));


        BigDecimal fBuy  = taxaCompra.divide(CEM, MC);
        BigDecimal fSell = taxaVenda.divide(CEM, MC);

        BigDecimal numerador   = precoCompra.multiply(BigDecimal.ONE.add(fBuy, MC), MC);
        BigDecimal denominador = BigDecimal.ONE.subtract(fSell, MC);

        if (denominador.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(msg.get("bep.erroTaxaVenda100"));
            return;
        }

        BigDecimal precoSaida = numerador.divide(denominador, MC);

        BigDecimal variacaoPct = precoSaida
                .subtract(precoCompra, MC)
                .divide(precoCompra, MC)
                .multiply(CEM, MC);

        System.out.println(msg.get("bep.resultado") + " " + formatMoney(precoSaida));
        System.out.println(msg.get("bep.variacao") + " " + formatPercent(variacaoPct));
        System.out.println(msg.get("bep.obs"));
    }

    private BigDecimal lerPercentual(String prompt) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim().replace(',', '.');
            try {
                BigDecimal v = new BigDecimal(in, MC);
                if (v.compareTo(BigDecimal.ZERO) < 0 || v.compareTo(CEM) >= 0) {
                    System.out.println(msg.get("bep.percInvalido"));
                    continue;
                }
                return v;
            } catch (Exception e) {
                System.out.println(msg.get("bep.numeroInvalido"));
            }
        }
    }

    private BigDecimal lerDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim().replace(',', '.');
            try {
                BigDecimal v = new BigDecimal(in, MC);
                if (v.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println(msg.get("bep.valorPositivo"));
                    continue;
                }
                return v;
            } catch (Exception e) {
                System.out.println(msg.get("bep.numeroInvalido"));
            }
        }
    }

    private String formatMoney(BigDecimal v) {
        return v.setScale(8, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

    private String formatPercent(BigDecimal v) {
        DecimalFormat df = new DecimalFormat("0.######");
        return df.format(v) + "%";
    }
}
