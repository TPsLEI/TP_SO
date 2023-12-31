import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
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

        Map<String, Integer> dayMessageCountMap = readCSVFile("./files/dados.csv");

        for (Map.Entry<String, Integer> entry : dayMessageCountMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Messages", entry.getKey());
        }

        return dataset;
    }

    private Map<String, Integer> readCSVFile(String filePath) {
        Map<String, Integer> dayMessageCountMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String date = parts[0].trim();
                    String day = extractDay(date);

                    dayMessageCountMap.put(day, dayMessageCountMap.getOrDefault(day, 0) + 1);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        Map<String, Integer> sortedMap = new TreeMap<>(dayMessageCountMap);
        return sortedMap;
    }

    private String extractDay(String dateTime) {
        String[] dateTimeParts = dateTime.split(" ");
        if (dateTimeParts.length >= 2) {
            String dateString = dateTimeParts[0].trim();
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                Date date = inputFormat.parse(dateString);
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
