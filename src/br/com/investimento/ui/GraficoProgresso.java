package br.com.investimento.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GraficoProgresso extends JFrame {

    public GraficoProgresso(List<BigDecimal> progresso, String idioma) {
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

        XYSeries series = new XYSeries(mensagens.getString("graficoProgresso.legenda"));

        series.add(0, 0);

        for (int i = 0; i < progresso.size(); i++) {
            series.add(i, progresso.get(i).doubleValue());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                mensagens.getString("graficoProgresso.titulo"),
                mensagens.getString("graficoProgresso.eixoX"),
                mensagens.getString("graficoProgresso.eixoY"),
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.getXYPlot().getRangeAxis().setLowerBound(0);

        NumberAxis xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setLowerBound(0);

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }
}
