package br.com.investimento.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Queue;

public class Calculos {

    public BigDecimal processarTransacao(Queue<LoteCompra> filaCompras, BigDecimal quantidade, BigDecimal preco) {
        BigDecimal lucroVenda = BigDecimal.ZERO;

        if (quantidade.compareTo(BigDecimal.ZERO) > 0) {
            filaCompras.add(new LoteCompra(quantidade, preco));
        } else {
        	
            BigDecimal qtdParaVender = quantidade.negate();
            BigDecimal receitaVenda = qtdParaVender.multiply(preco);
            BigDecimal custoTotal = BigDecimal.ZERO;

            while (qtdParaVender.compareTo(BigDecimal.ZERO) > 0 && !filaCompras.isEmpty()) {
                LoteCompra lote = filaCompras.peek();
                if (lote.getQuantidade().compareTo(qtdParaVender) <= 0) {
                    custoTotal = custoTotal.add(lote.getQuantidade().multiply(lote.getPrecoUnitario()));
                    qtdParaVender = qtdParaVender.subtract(lote.getQuantidade());
                    filaCompras.poll();
                } else {
                    custoTotal = custoTotal.add(qtdParaVender.multiply(lote.getPrecoUnitario()));
                    lote.setQuantidade(lote.getQuantidade().subtract(qtdParaVender));
                    qtdParaVender = BigDecimal.ZERO;
                }
            }

            lucroVenda = receitaVenda.subtract(custoTotal);

            if (qtdParaVender.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("ATENÇÃO: Venda maior que saldo disponível!");
            }
        }

        return lucroVenda.setScale(8, RoundingMode.HALF_UP);
    }
}
