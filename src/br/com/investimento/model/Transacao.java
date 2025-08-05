package br.com.investimento.model;

public class Transacao {
    private String nome;
    private String sigla;
    private double quantidade;
    private double precoUnitario;

    public Transacao(String nome, String sigla, double quantidade, double precoUnitario) {
        this.nome = nome;
        this.sigla = sigla;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public double getPrecoTotal() {
        return quantidade * precoUnitario;
    }

    @Override
    public String toString() {
        return sigla + " (" + nome + ") - " + quantidade + " x R$" + precoUnitario +
                " | Total: R$" + getPrecoTotal();
    }
}
