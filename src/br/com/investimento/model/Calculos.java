package br.com.investimento.model;

import java.util.Queue;

public class Calculos {
	 // Há um pequeno erro na hora de exibir o valor, corrigirei assim que o banco de dados estiver implementado e os valores investidos sejam subtraídos de um saldo que até então não existe.
    public double processarTransacao(Queue<LoteCompra> filaCompras, double quantidade, double preco) {
        double lucroVenda = 0;

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

            lucroVenda = receitaVenda - custoTotal;

            if (qtdParaVender > 0) {
                System.out.println("ATENÇÃO: Venda maior que saldo disponível!");
            }
        }

        return lucroVenda;
    }
}
