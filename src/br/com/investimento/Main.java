	package br.com.investimento;

import br.com.investimento.model.*;
import br.com.investimento.ui.GraficoBarras;
import br.com.investimento.ui.GraficoProgresso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
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
        Calculos calculos = new Calculos();
        BigDecimal lucroTotal = BigDecimal.ZERO;

        while (true) {
            System.out.println("\n==== " + mensagens.get("menu.titulo") + " ====");
            System.out.println("1. " + mensagens.get("menu.cadastrar"));
            System.out.println("2. " + mensagens.get("menu.listar"));
            System.out.println("3. " + mensagens.get("menu.total"));
            System.out.println("4. " + mensagens.get("menu.sair"));
            System.out.println("5. " + mensagens.get("menu.grafico"));
            System.out.println("6. " + mensagens.get("menu.fibonacci"));
            System.out.println("7. " + mensagens.get("menu.bep"));
            System.out.print(mensagens.get("menu.escolha"));

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: {
                    Cadastrar cadastrar = new Cadastrar();
                    lucroTotal = cadastrar.executarCadastro(
                            scanner, mensagens, transacoes, filaCompras, calculos, lucroTotal
                    );
                    break;
                }

                case 2: {
                    TransacaoDAO daoListar = new TransacaoDAO();
                    try {
                        List<Transacao> transacoesDoBanco = daoListar.listarTransacoes();
                        if (transacoesDoBanco.isEmpty()) {
                            System.out.println(mensagens.get("msg.vazio"));
                        } else {
                            for (Transacao tr : transacoesDoBanco) {
                                System.out.println(tr);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                    break;
                }

                case 3: {
                    TransacaoDAO daoTotal = new TransacaoDAO();
                    try {
                        BigDecimal totalInvestido = daoTotal.calcularTotalInvestido();
                        System.out.println(mensagens.get("msg.total") + " R$"
                                + totalInvestido.setScale(8, RoundingMode.HALF_UP).toPlainString());
                        System.out.println("Lucro acumulado: R$"
                                + lucroTotal.setScale(8, RoundingMode.HALF_UP).toPlainString());
                    } catch (SQLException e) {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                    break;
                }

                case 4: {
                    System.out.println(mensagens.get("msg.encerrar"));
                    scanner.close();
                    return;
                }

                case 5: {
                    System.out.println("1. " + mensagens.get("menu.grafico.opcao1"));
                    System.out.println("2. " + mensagens.get("menu.grafico.opcao2"));
                    System.out.print(mensagens.get("menu.grafico.escolha"));
                    int tipoGrafico = scanner.nextInt();
                    scanner.nextLine();

                    TransacaoDAO daoGraficos = new TransacaoDAO();
                    try {
                        List<Transacao> transacoesDoBanco = daoGraficos.listarTransacoes();

                        if (transacoesDoBanco.isEmpty()) {
                            System.out.println(mensagens.get("msg.vazio"));
                        } else if (tipoGrafico == 1) {
                            Map<String, BigDecimal> dadosGrafico = transacoesDoBanco.stream()
                                    .collect(Collectors.groupingBy(
                                            Transacao::getSigla,
                                            Collectors.mapping(
                                                    Transacao::getPrecoTotal,
                                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                            )
                                    ));
                            new GraficoBarras(dadosGrafico, idiomaEscolhido).setVisible(true);

                        } else if (tipoGrafico == 2) {
                            List<BigDecimal> progresso = new ArrayList<>();
                            BigDecimal acumulado = BigDecimal.ZERO;

                            progresso.add(acumulado);

                            for (Transacao tr : transacoesDoBanco) {
                                acumulado = acumulado.add(tr.getPrecoTotal());
                                progresso.add(acumulado);
                            }

                            new GraficoProgresso(progresso, idiomaEscolhido).setVisible(true);
                            }
 else {
                            System.out.println("Tipo de gráfico inválido.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                    }
                    break;
                }

                case 6: {
                    FibonacciCalculo fibonacci = new FibonacciCalculo(scanner, mensagens);
                    fibonacci.executar();
                    break;
                }

                case 7: {
                    BreakEvenCalculo bep = new BreakEvenCalculo(scanner, mensagens);
                    bep.executar();
                    break;
                }

                default: {
                    System.out.println(mensagens.get("msg.opcaoInvalida"));
                    break;
                }
            }
        }
    }
}
