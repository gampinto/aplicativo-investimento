package br.com.investimento.model;

import java.math.BigDecimal;

public class LoteCompra {
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;

    public LoteCompra(BigDecimal quantidade, BigDecimal precoUnitario) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
