// GraphPanel.java
package task1.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class GraphPanel extends JPanel {

    private String title;
    private String xLabel;
    private String yLabel;
    private List<DataSeries> seriesList = new ArrayList<>();

    private static final int MARGIN_LEFT = 80;
    private static final int MARGIN_RIGHT = 30;
    private static final int MARGIN_TOP = 40;
    private static final int MARGIN_BOTTOM = 50;

    private static final Color[] COLORS = {
            new Color(220, 60, 60),
            new Color(60, 130, 220),
            new Color(40, 170, 80),
            new Color(200, 140, 40)
    };

    public GraphPanel(String title, String xLabel, String yLabel){
        /** constructor

        Creates a panel for drawing an XY line chart with axes and legend.
        */
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
    }

    public void clearData(){
        /** clear all data series */
        seriesList.clear();
        repaint();
    }

    public void addSeries(String name, double[] xData, double[] yData){
        /** add a data series

        Adds a named dataset to the chart.
        */
        seriesList.add(new DataSeries(name, xData, yData));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        /** render the chart

        Draws axes, grid lines, data points, lines, and legend.
        */
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int plotW = w - MARGIN_LEFT - MARGIN_RIGHT;
        int plotH = h - MARGIN_TOP - MARGIN_BOTTOM;

        // Title
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.setColor(new Color(50, 50, 50));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(title, (w - fm.stringWidth(title)) / 2, 25);

        if(seriesList.isEmpty() || plotW < 20 || plotH < 20) return;

        // Find data ranges
        double xMin = Double.MAX_VALUE, xMax = -Double.MAX_VALUE;
        double yMin = 0, yMax = -Double.MAX_VALUE;

        for(DataSeries s : seriesList){
            for(double v : s.xData){
                xMin = Math.min(xMin, v);
                xMax = Math.max(xMax, v);
            }
            for(double v : s.yData){
                yMax = Math.max(yMax, v);
            }
        }

        if(xMax == xMin) xMax = xMin + 1;
        if(yMax == yMin) yMax = yMin + 1;
        yMax *= 1.1; // padding

        // Draw axes
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(MARGIN_LEFT, MARGIN_TOP, MARGIN_LEFT, h - MARGIN_BOTTOM);
        g2.drawLine(MARGIN_LEFT, h - MARGIN_BOTTOM, w - MARGIN_RIGHT, h - MARGIN_BOTTOM);

        // Grid lines and Y labels
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2.setStroke(new BasicStroke(0.5f));
        int yTicks = 5;
        for(int i = 0; i <= yTicks; i++){
            double val = yMin + (yMax - yMin) * i / yTicks;
            int py = h - MARGIN_BOTTOM - (int)(plotH * i / (double)yTicks);
            g2.setColor(new Color(220, 220, 220));
            g2.drawLine(MARGIN_LEFT + 1, py, w - MARGIN_RIGHT, py);
            g2.setColor(Color.DARK_GRAY);
            String label = formatNumber(val);
            fm = g2.getFontMetrics();
            g2.drawString(label, MARGIN_LEFT - fm.stringWidth(label) - 5, py + 4);
        }

        // X labels
        for(DataSeries s : seriesList){
            for(int i = 0; i < s.xData.length; i++){
                int px = MARGIN_LEFT + (int)(plotW * (s.xData[i] - xMin) / (xMax - xMin));
                int py = h - MARGIN_BOTTOM;
                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(px, MARGIN_TOP, px, py);
                g2.setColor(Color.DARK_GRAY);
                String label = formatNumber(s.xData[i]);
                fm = g2.getFontMetrics();
                g2.drawString(label, px - fm.stringWidth(label) / 2, py + 15);
            }
            break; // only draw x labels from first series
        }

        // Axis labels
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.setColor(Color.DARK_GRAY);
        fm = g2.getFontMetrics();
        g2.drawString(xLabel, MARGIN_LEFT + plotW / 2 - fm.stringWidth(xLabel) / 2,
                h - 5);

        // Y axis label (rotated)
        Graphics2D g2r = (Graphics2D) g2.create();
        g2r.rotate(-Math.PI / 2);
        g2r.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2r.setColor(Color.DARK_GRAY);
        g2r.drawString(yLabel, -(MARGIN_TOP + plotH / 2 + fm.stringWidth(yLabel) / 2), 15);
        g2r.dispose();

        // Draw data series
        for(int si = 0; si < seriesList.size(); si++){
            DataSeries s = seriesList.get(si);
            Color color = COLORS[si % COLORS.length];
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.5f));

            int[] px = new int[s.xData.length];
            int[] py = new int[s.yData.length];

            for(int i = 0; i < s.xData.length; i++){
                px[i] = MARGIN_LEFT + (int)(plotW * (s.xData[i] - xMin) / (xMax - xMin));
                py[i] = h - MARGIN_BOTTOM - (int)(plotH * (s.yData[i] - yMin) / (yMax - yMin));
            }

            // Lines
            for(int i = 0; i < px.length - 1; i++){
                g2.drawLine(px[i], py[i], px[i + 1], py[i + 1]);
            }

            // Points
            for(int i = 0; i < px.length; i++){
                g2.fillOval(px[i] - 4, py[i] - 4, 8, 8);
            }
        }

        // Legend
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        int legendX = MARGIN_LEFT + 15;
        int legendY = MARGIN_TOP + 15;
        for(int si = 0; si < seriesList.size(); si++){
            Color color = COLORS[si % COLORS.length];
            g2.setColor(color);
            g2.fillRect(legendX, legendY + si * 18, 12, 12);
            g2.setColor(Color.BLACK);
            g2.drawString(seriesList.get(si).name, legendX + 16, legendY + si * 18 + 11);
        }
    }

    private String formatNumber(double val){
        /** format a number for axis labels

        Uses appropriate formatting based on magnitude.
        */
        if(val >= 1_000_000) return String.format("%.1fM", val / 1_000_000);
        if(val >= 1_000) return String.format("%.0fK", val / 1_000);
        if(val == (long) val) return String.valueOf((long) val);
        return String.format("%.1f", val);
    }

    private static class DataSeries {
        String name;
        double[] xData;
        double[] yData;

        DataSeries(String name, double[] xData, double[] yData){
            this.name = name;
            this.xData = xData;
            this.yData = yData;
        }
    }
}
