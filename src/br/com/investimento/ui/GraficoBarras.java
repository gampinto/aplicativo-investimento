package br.com.investimento.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class GraficoBarras extends JFrame {

    public GraficoBarras(Map<String, Double> dados, String idioma) {
        Locale locale = switch (idioma.toLowerCase()) {
            case "en" -> Locale.ENGLISH;
            case "es" -> new Locale("es", "ES");
            case "fr" -> Locale.FRENCH;
            default -> new Locale("pt", "BR");
        };

        ResourceBundle mensagens = ResourceBundle.getBundle("mensagens", locale);

        setTitle(mensagens.getString("grafico.titulo"));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : dados.entrySet()) {
            dataset.addValue(entry.getValue(), mensagens.getString("grafico.legenda"), entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                mensagens.getString("grafico.titulo"),
                mensagens.getString("grafico.eixoX"),
                mensagens.getString("grafico.eixoY"),
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }


}
