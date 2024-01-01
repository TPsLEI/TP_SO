import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraphPage extends BaseFrame {

    public GraphPage() {
        super("Gráfico");
        setSize(960, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JFreeChart barChart = ChartFactory.createBarChart(
                "Número de Mensagens por Dia",
                "Dia",
                "Número de Mensagens",
                createDataset(),
                PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel chartPanel = new ChartPanel(barChart);
        mainPanel.add(chartPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private CategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Date, Integer> dayMessageCountMap = readCSVFile("./files/dados.csv");

        for (Map.Entry<Date, Integer> entry : dayMessageCountMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Messages", formatDate(entry.getKey()));
        }

        return dataset;
    }

    private Map<Date, Integer> readCSVFile(String filePath) {
        Map<Date, Integer> dayMessageCountMap = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String dateString = parts[0].trim();
                    Date date = parseDate(dateString);

                    dayMessageCountMap.put(date, dayMessageCountMap.getOrDefault(date, 0) + 1);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return dayMessageCountMap;
    }

    private String formatDate(Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        return outputFormat.format(date);
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}