// ComplexityGraphPanel.java

package task3.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ComplexityGraphPanel extends JPanel {

    private List<Integer> prefixSteps;
    private List<Integer> bruteSteps;
    private int totalBruteForce;

    public ComplexityGraphPanel(){
        prefixSteps = new ArrayList<>();
        bruteSteps = new ArrayList<>();
        totalBruteForce = 0;
        setBackground(Color.WHITE);
    }

    public void reset(){
        prefixSteps.clear();
        bruteSteps.clear();
        totalBruteForce = 0;
        repaint();
    }

    public void setTotalBruteForce(int total){
        totalBruteForce = total;
    }

    public void addStep(int currentPrefixStep, int currentBruteStep){
        prefixSteps.add(currentPrefixStep);
        bruteSteps.add(currentBruteStep);
        repaint();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int leftMargin = 80;
        int rightMargin = 40;
        int topMargin = 50;
        int bottomMargin = 210;
        int graphW = w - leftMargin - rightMargin;
        int graphH = h - topMargin - bottomMargin;

        if(graphW < 20 || graphH < 20) return;

        // Background
        g2.setColor(new Color(248, 248, 248));
        g2.fillRect(leftMargin, topMargin, graphW, graphH);

        // Title
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        FontMetrics titleFm = g2.getFontMetrics();
        String title = "Time Complexity: Prefix Pruning vs Brute Force";
        g2.drawString(title, (w - titleFm.stringWidth(title)) / 2, topMargin - 15);

        // Axes
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(leftMargin, topMargin, leftMargin, h - bottomMargin);
        g2.drawLine(leftMargin, h - bottomMargin, w - rightMargin, h - bottomMargin);

        // Arrow tips
        int arrowSize = 8;
        g2.fillPolygon(
                new int[]{leftMargin, leftMargin - arrowSize / 2, leftMargin + arrowSize / 2},
                new int[]{topMargin - arrowSize, topMargin, topMargin}, 3);
        g2.fillPolygon(
                new int[]{w - rightMargin + arrowSize, w - rightMargin, w - rightMargin},
                new int[]{h - bottomMargin, h - bottomMargin - arrowSize / 2, h - bottomMargin + arrowSize / 2}, 3);

        // X-axis label
        g2.setFont(new Font("SansSerif", Font.BOLD, 15));
        FontMetrics axisFm = g2.getFontMetrics();
        String xLabel = "Solver iteration (progress)";
        g2.drawString(xLabel, (w - axisFm.stringWidth(xLabel)) / 2, h - bottomMargin + 45);

        // Complexity explanation block below the graph
        int cBoxX = leftMargin;
        int cBoxY = h - bottomMargin + 55;
        int cBoxW = graphW;
        int cBoxH = bottomMargin - 60;

        g2.setColor(new Color(245, 245, 255));
        g2.fillRoundRect(cBoxX, cBoxY, cBoxW, cBoxH, 12, 12);
        g2.setColor(new Color(160, 160, 200));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(cBoxX, cBoxY, cBoxW, cBoxH, 12, 12);

        int colW = cBoxW / 2;
        int textX1 = cBoxX + 20;
        int textX2 = cBoxX + colW + 10;
        int lineY = cBoxY + 22;

        // Left column: Prefix pruning
        g2.setColor(new Color(30, 150, 30));
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("Prefix pruning algorithm", textX1, lineY);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 15));
        g2.setColor(new Color(50, 50, 50));
        g2.drawString("O(R \u00B7 C \u00B7 D \u00B7 L)", textX1, lineY + 22);

        // Right column: Brute force
        g2.setColor(new Color(210, 40, 40));
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("Brute force algorithm", textX2, lineY);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 15));
        g2.setColor(new Color(50, 50, 50));
        g2.drawString("O(R \u00B7 C \u00B7 D \u00B7 L \u00B7 W)", textX2, lineY + 22);

        // Variable legend
        int varY = lineY + 50;
        g2.setColor(new Color(80, 80, 80));
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.drawString("Where:", textX1, varY);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        String[] vars = {
            "R = number of rows in the puzzle grid",
            "C = number of columns in the puzzle grid",
            "D = number of search directions (8)",
            "L = word length",
            "W = number of words in the dictionary"
        };
        int vi = 0;
        for(String v : vars){
            int col = vi < 3 ? 0 : 1;
            int row = vi < 3 ? vi : vi - 3;
            int vx = col == 0 ? textX1 + 10 : textX2;
            g2.drawString(v, vx, varY + 18 + row * 17);
            vi++;
        }

        // Y-axis label
        g2.setFont(new Font("SansSerif", Font.BOLD, 15));
        Graphics2D g2r = (Graphics2D) g2.create();
        g2r.setFont(new Font("SansSerif", Font.BOLD, 15));
        g2r.rotate(-Math.PI / 2);
        String yLabel = "Cumulative checks";
        FontMetrics yFm = g2r.getFontMetrics();
        g2r.drawString(yLabel, -(h + yFm.stringWidth(yLabel)) / 2, 20);
        g2r.dispose();

        if(prefixSteps.isEmpty()) {
            g2.setFont(new Font("SansSerif", Font.ITALIC, 20));
            g2.setColor(Color.GRAY);
            String msg = "Start solver to see comparison graph";
            FontMetrics mfm = g2.getFontMetrics();
            g2.drawString(msg, (w - mfm.stringWidth(msg)) / 2, h / 2);
            return;
        }

        int n = prefixSteps.size();
        int maxY = Math.max(
                bruteSteps.get(bruteSteps.size() - 1),
                Math.max(prefixSteps.get(prefixSteps.size() - 1), 1)
        );
        if(totalBruteForce > 0) maxY = Math.max(maxY, totalBruteForce);
        maxY = (int)(maxY * 1.15);

        // Grid lines and Y ticks
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        int yTicks = 6;
        for(int i = 0; i <= yTicks; i++){
            int yy = h - bottomMargin - (int)((double) i / yTicks * graphH);
            g2.setColor(new Color(210, 210, 210));
            g2.setStroke(new BasicStroke(1));
            if(i > 0) g2.drawLine(leftMargin, yy, w - rightMargin, yy);
            // Tick mark
            g2.setColor(Color.BLACK);
            g2.drawLine(leftMargin - 5, yy, leftMargin, yy);
            String yVal = String.valueOf(maxY * i / yTicks);
            FontMetrics tfm = g2.getFontMetrics();
            g2.drawString(yVal, leftMargin - 8 - tfm.stringWidth(yVal), yy + 5);
        }

        // X ticks
        int xTicks = Math.min(n, 10);
        for(int i = 0; i <= xTicks; i++){
            int idx = (int)((double) i / xTicks * (n - 1));
            int xx = leftMargin + (int)((double) idx / Math.max(n - 1, 1) * graphW);
            g2.setColor(new Color(210, 210, 210));
            g2.setStroke(new BasicStroke(1));
            if(i > 0) g2.drawLine(xx, topMargin, xx, h - bottomMargin);
            g2.setColor(Color.BLACK);
            g2.drawLine(xx, h - bottomMargin, xx, h - bottomMargin + 5);
            String xVal = String.valueOf(idx + 1);
            FontMetrics tfm = g2.getFontMetrics();
            g2.drawString(xVal, xx - tfm.stringWidth(xVal) / 2, h - bottomMargin + 20);
        }

        // Draw brute force line (red, thick)
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(new Color(210, 40, 40));
        drawLine(g2, bruteSteps, n, maxY, leftMargin, graphW, graphH, h - bottomMargin);

        // Draw prefix pruning line (green, thick)
        g2.setColor(new Color(30, 150, 30));
        drawLine(g2, prefixSteps, n, maxY, leftMargin, graphW, graphH, h - bottomMargin);

        // Total brute force reference line (dashed)
        if(totalBruteForce > 0){
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[]{10.0f, 6.0f}, 0.0f));
            g2.setColor(new Color(210, 40, 40, 140));
            int refY = h - bottomMargin - (int)((double) totalBruteForce / maxY * graphH);
            g2.drawLine(leftMargin, refY, w - rightMargin, refY);
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.setColor(new Color(180, 30, 30));
            g2.drawString("Brute force total: " + totalBruteForce + " checks", leftMargin + 10, refY - 8);
        }

        // Legend box — positioned just below brute force reference line, left-aligned
        int legW = 320;
        int legH = 100;
        int legX = leftMargin + 10;
        int refLineY = (totalBruteForce > 0)
                ? h - bottomMargin - (int)((double) totalBruteForce / maxY * graphH)
                : topMargin + 15;
        int legY = refLineY + 12;
        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillRoundRect(legX, legY, legW, legH, 10, 10);
        g2.setColor(new Color(180, 180, 180));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(legX, legY, legW, legH, 10, 10);

        int legPad = 15;
        int lineLen = 35;

        // Green line entry
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(new Color(30, 150, 30));
        g2.drawLine(legX + legPad, legY + 25, legX + legPad + lineLen, legY + 25);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.setColor(Color.BLACK);
        int prefixTotal = prefixSteps.isEmpty() ? 0 : prefixSteps.get(prefixSteps.size()-1);
        g2.drawString("Prefix pruning: " + prefixTotal + " checks",
                legX + legPad + lineLen + 10, legY + 30);

        // Red line entry
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(new Color(210, 40, 40));
        g2.drawLine(legX + legPad, legY + 52, legX + legPad + lineLen, legY + 52);
        g2.setColor(Color.BLACK);
        int bruteTotal = bruteSteps.isEmpty() ? 0 : bruteSteps.get(bruteSteps.size()-1);
        g2.drawString("Brute force: " + bruteTotal + " checks",
                legX + legPad + lineLen + 10, legY + 57);

        // Search reduction percentage
        if(bruteTotal > 0){
            double reduction = (1.0 - (double) prefixTotal / bruteTotal) * 100;
            g2.setColor(new Color(0, 100, 0));
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            String redLabel = String.format("Search reduction: %.1f%% fewer checks", reduction);
            g2.drawString(redLabel, legX + legPad, legY + 82);
        }
    }

    private void drawLine(Graphics2D g2, List<Integer> data, int n, int maxY,
                           int leftMargin, int graphW, int graphH, int bottom){
        if(n < 2) return;
        int prevX = leftMargin;
        int prevY = bottom - (int)((double) data.get(0) / maxY * graphH);
        for(int i = 1; i < n; i++){
            int xx = leftMargin + (int)((double) i / (n - 1) * graphW);
            int yy = bottom - (int)((double) data.get(i) / maxY * graphH);
            g2.drawLine(prevX, prevY, xx, yy);
            prevX = xx;
            prevY = yy;
        }
    }
}
