package br.com.investimento.model;

import br.com.investimento.database.Conexao;
import java.sql.*;

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
}
