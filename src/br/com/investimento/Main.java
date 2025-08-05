package br.com.investimento;

import br.com.investimento.model.Transacao;
import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o idioma (pt, en, es, fr): ");
        String idiomaEscolhido = scanner.nextLine();

        Mensagens mensagens = new Mensagens(idiomaEscolhido);
        List<Transacao> transacoes = new ArrayList<>();

        while (true) {
            System.out.println("\n==== " + mensagens.get("menu.titulo") + " ====");
            System.out.println("1. " + mensagens.get("menu.cadastrar"));
            System.out.println("2. " + mensagens.get("menu.listar"));
            System.out.println("3. " + mensagens.get("menu.total"));
            System.out.println("4. " + mensagens.get("menu.sair"));
            System.out.print(mensagens.get("menu.escolha"));

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

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
                double total = 0;
                for (Transacao t : transacoes) {
                    total += t.getPrecoTotal();
                }
                System.out.println(mensagens.get("msg.total") + " R$" + total);

            } else if (opcao == 4) {
                System.out.println(mensagens.get("msg.encerrar"));
                break;
            } else {
                System.out.println(mensagens.get("msg.opcaoInvalida"));
            }
        }

        scanner.close();
    }
}
