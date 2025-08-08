package br.com.investimento;

import br.com.investimento.model.Transacao;
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

        while (true) {
            System.out.println("\n==== " + mensagens.get("menu.titulo") + " ====");
            System.out.println("1. " + mensagens.get("menu.cadastrar"));
            System.out.println("2. " + mensagens.get("menu.listar"));
            System.out.println("3. " + mensagens.get("menu.total"));
            System.out.println("4. " + mensagens.get("menu.sair"));
            System.out.println("5. " + mensagens.get("menu.grafico"));
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
                System.out.println("1. Gráfico de Barras");
                System.out.println("2. Gráfico de Progresso");
                System.out.print("Escolha o tipo de gráfico: ");
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

            } else {
                System.out.println(mensagens.get("msg.opcaoInvalida"));
            }
        }

        scanner.close();
    }
}
