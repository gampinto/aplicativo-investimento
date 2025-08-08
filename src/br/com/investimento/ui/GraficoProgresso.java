package br.com.investimento.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GraficoProgresso extends JFrame {

    public GraficoProgresso(List<Double> valoresTotais, String idioma) {
        Locale locale = switch (idioma.toLowerCase()) {
            case "en" -> Locale.ENGLISH;
            case "es" -> new Locale("es", "ES");
            case "fr" -> Locale.FRENCH;
            default -> new Locale("pt", "BR");
        };
        ResourceBundle mensagens = ResourceBundle.getBundle("mensagens", locale);

        setTitle(mensagens.getString("graficoProgresso.titulo"));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < valoresTotais.size(); i++) {
            dataset.addValue(valoresTotais.get(i), mensagens.getString("graficoProgresso.legenda"), String.valueOf(i + 1));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                mensagens.getString("graficoProgresso.titulo"),
                mensagens.getString("graficoProgresso.eixoX"),
                mensagens.getString("graficoProgresso.eixoY"),
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

}
