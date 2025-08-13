package br.com.investimento;

import br.com.investimento.model.*;
import br.com.investimento.ui.GraficoBarras;
import br.com.investimento.ui.GraficoProgresso;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o idioma (pt, en, es, fr): ");
        String idiomaEscolhido = scanner.nextLine().trim().toLowerCase();

        Mensagens mensagens = new Mensagens(idiomaEscolhido);
        List<Transacao> transacoes = new ArrayList<>();
        Queue<LoteCompra> filaCompras = new LinkedList<>();
        double lucroTotal = 0;

        while (true) {
            System.out.println("\n==== " + mensagens.get("menu.titulo") + " ====");
            System.out.println("1. " + mensagens.get("menu.cadastrar"));
            System.out.println("2. " + mensagens.get("menu.listar"));
            System.out.println("3. " + mensagens.get("menu.total"));
            System.out.println("4. " + mensagens.get("menu.sair"));
            System.out.println("5. " + mensagens.get("menu.grafico"));
            System.out.println("6. " + mensagens.get("menu.fibonacci")); // nova opção
            System.out.print(mensagens.get("menu.escolha"));

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.print(mensagens.get("input.nome"));
                String nome = scanner.nextLine();
                System.out.print(mensagens.get("input.sigla"));
                String sigla = scanner.nextLine();
                System.out.print(mensagens.get("input.quantidade"));
                double quantidade = scanner.nextDouble();
                System.out.print(mensagens.get("input.preco"));
                double preco = scanner.nextDouble();

                Transacao t = new Transacao(nome, sigla, quantidade, preco);
                transacoes.add(t);
                System.out.println(mensagens.get("msg.sucesso"));

                if (quantidade > 0) {
                    filaCompras.add(new LoteCompra(quantidade, preco));
                } else {
                    double qtdParaVender = -quantidade; 
                    double receitaVenda = qtdParaVender * preco;
                    double custoTotal = 0;

                    while (qtdParaVender > 0 && !filaCompras.isEmpty()) {
                        LoteCompra lote = filaCompras.peek();
                        if (lote.getQuantidade() <= qtdParaVender) {
                            custoTotal += lote.getQuantidade() * lote.getPrecoUnitario();
                            qtdParaVender -= lote.getQuantidade();
                            filaCompras.poll(); 
                        } else {
                            custoTotal += qtdParaVender * lote.getPrecoUnitario();
                            lote.setQuantidade(lote.getQuantidade() - qtdParaVender);
                            qtdParaVender = 0;
                        }
                    }

                    double lucroVenda = receitaVenda - custoTotal;
                    lucroTotal += lucroVenda;

                    if (qtdParaVender > 0) {
                        System.out.println("ATENÇÃO: Venda maior que saldo disponível!");
                    }
                }
            } else if (opcao == 2) {
                if (transacoes.isEmpty()) {
                    System.out.println(mensagens.get("msg.vazio"));
                } else {
                    for (Transacao t : transacoes) {
                        System.out.println(t);
                    }
                }
            } else if (opcao == 3) {
                double total = transacoes.stream()
                    .mapToDouble(Transacao::getPrecoTotal)
                    .sum();
                System.out.println(mensagens.get("msg.total") + " R$" + total);
            } else if (opcao == 4) {
                System.out.println(mensagens.get("msg.encerrar"));
                break;
            } else if (opcao == 5) {
                System.out.println("1. " + mensagens.get("menu.grafico.opcao1"));
                System.out.println("2. " + mensagens.get("menu.grafico.opcao2"));
                System.out.print(mensagens.get("menu.grafico.escolha"));
                int tipo = scanner.nextInt();

                if (tipo == 1) {
                    Map<String, Double> dadosGrafico = transacoes.stream()
                        .collect(Collectors.groupingBy(
                            t -> t.sigla,
                            Collectors.summingDouble(t -> t.getQuantidade() * t.getPrecoUnitario())
                        ));
                    new GraficoBarras(dadosGrafico, idiomaEscolhido).setVisible(true);
                } else if (tipo == 2) {
                    List<Double> progresso = new ArrayList<>();
                    double acumulado = 0;
                    for (Transacao t : transacoes) {
                        acumulado += t.getPrecoTotal();
                        progresso.add(acumulado);
                    }
                    new GraficoProgresso(progresso, idiomaEscolhido).setVisible(true);
                } else {
                    System.out.println("Tipo de gráfico inválido.");
                }
            } else if (opcao == 6) {
                FibonacciCalculo fibonacci = new FibonacciCalculo(scanner, mensagens);
                fibonacci.executar();
            } else {
                System.out.println(mensagens.get("msg.opcaoInvalida"));
            }
        }

        scanner.close();
    }
}
