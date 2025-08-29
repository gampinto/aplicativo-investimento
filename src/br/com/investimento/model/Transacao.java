package br.com.investimento.model;

import java.math.BigDecimal;

public class Transacao {
    private String nome;
    public String sigla;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private TipoTransacao tipo;

    public Transacao(String nome, String sigla, BigDecimal quantidade, BigDecimal precoUnitario, TipoTransacao tipo) {
           this.nome = nome;
        this.sigla = sigla;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.tipo = tipo;
    }

    public Transacao(String nome2, String sigla2, double quantidade2, double preco) {

	}

	public BigDecimal getPrecoTotal() {
        return quantidade.multiply(precoUnitario);
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return sigla + " (" + nome + ") - " + quantidade + " x R$" + precoUnitario +
               " | Total: R$" + getPrecoTotal();
    }

	public void setQuantidade(BigDecimal subtract) {
		
	}
}
