package br.com.investimento.model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import br.com.investimento.Mensagens;

public class Cadastrar {

	  public static BigDecimal executarCadastro(
	            Scanner scanner,
	            Mensagens mensagens,             
	            List<Transacao> transacoes,
	            Queue<LoteCompra> filaCompras,       
	            Calculos calculos,
	            BigDecimal lucroTotal) {

        System.out.print(mensagens.get("input.nome"));
        String nome = scanner.nextLine();
        System.out.print(mensagens.get("input.sigla"));
        String sigla = scanner.nextLine();
        System.out.print(mensagens.get("input.quantidade"));
        BigDecimal quantidade = scanner.nextBigDecimal();
        System.out.print(mensagens.get("input.preco"));
        BigDecimal preco = scanner.nextBigDecimal();
        scanner.nextLine();

        TipoTransacao tipo = quantidade.compareTo(BigDecimal.ZERO) > 0 ? TipoTransacao.COMPRA : TipoTransacao.VENDA;
        Transacao t = new Transacao(nome, sigla, quantidade.abs(), preco, tipo);
        transacoes.add(t);
        TransacaoDAO dao = new TransacaoDAO();
        try {
            dao.adicionarTransacao(t);
        } catch (SQLException e) {
            System.out.println("Erro ao gravar no banco: " + e.getMessage());
        }

        System.out.println(mensagens.get("msg.sucesso"));

        return lucroTotal.add(calculos.processarTransacao(filaCompras, quantidade, preco));
    }
}
