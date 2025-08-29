package br.com.investimento.model;

import br.com.investimento.database.Conexao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    public void adicionarTransacao(Transacao t) throws SQLException {
        String sql = "INSERT INTO transacoes (nome, sigla, quantidade, preco_unitario, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNome());
            ps.setString(2, t.getSigla());
            ps.setBigDecimal(3, t.getQuantidade());
            ps.setBigDecimal(4, t.getPrecoUnitario());
            ps.setString(5, t.getTipo().name());
            ps.executeUpdate();
        }
    }

    public List<Transacao> listarTransacoes() throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT nome, sigla, quantidade, preco_unitario, tipo FROM transacoes";

        try (Connection con = Conexao.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String sigla = rs.getString("sigla");
                java.math.BigDecimal quantidade = rs.getBigDecimal("quantidade");
                java.math.BigDecimal preco = rs.getBigDecimal("preco_unitario");
                TipoTransacao tipo = TipoTransacao.valueOf(rs.getString("tipo"));

                Transacao t = new Transacao(nome, sigla, quantidade, preco, tipo);
                transacoes.add(t);
            }
        }

        return transacoes;
    }
    public BigDecimal calcularTotalInvestido() throws SQLException {
        String sql = "SELECT SUM(quantidade * preco_unitario) FROM transacoes";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : BigDecimal.ZERO;
            } else {
                return BigDecimal.ZERO;
            }
        }
    }

public List<BigDecimal> listarProgresso() throws SQLException {
    List<BigDecimal> progresso = new ArrayList<>();
    BigDecimal acumulado = BigDecimal.ZERO;

    progresso.add(acumulado);

    String sql = "SELECT quantidade * preco_unitario AS total FROM transacoes ORDER BY ROWID"; 
    try (Connection con = Conexao.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            BigDecimal valor = rs.getBigDecimal("total");
            if (valor != null) {
                acumulado = acumulado.add(valor);
                progresso.add(acumulado);
            }
        }
    }

    return progresso;
}
}