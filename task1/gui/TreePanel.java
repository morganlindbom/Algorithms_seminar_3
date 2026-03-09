// TreePanel.java
package task1.gui;

import java.awt.*;
import javax.swing.*;
import task1.heap.BinaryHeap;

public class TreePanel extends JPanel {

    private BinaryHeap heap;
    private String title;
    private static final int NODE_RADIUS = 15;
    private static final int VERTICAL_GAP = 55;

    public TreePanel(String title){
        /** constructor

        Creates a panel for drawing a binary heap as a tree diagram.
        */
        this.title = title;
        this.heap = null;
        setPreferredSize(new Dimension(500, 350));
        setBackground(Color.WHITE);
    }

    public void setTitle(String title){
        /** update the title displayed above the tree */
        this.title = title;
        repaint();
    }

    public void setHeap(BinaryHeap heap){
        /** update the heap to draw

        Sets the heap data and repaints the panel.
        */
        this.heap = heap;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        /** draw the tree

        Renders the binary heap as a visual tree with nodes and edges.
        */
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw title
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.setColor(new Color(50, 50, 50));
        g2.drawString(title, 10, 20);

        if(heap == null || heap.size() == 0){
            g2.setFont(new Font("SansSerif", Font.ITALIC, 12));
            g2.drawString("(empty heap)", 10, 50);
            return;
        }

        // Draw array representation at bottom
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.setColor(new Color(80, 80, 80));
        StringBuilder arrayStr = new StringBuilder("Array: [");
        for(int i = 1; i <= heap.size(); i++){
            if(i > 1) arrayStr.append(", ");
            arrayStr.append(heap.get(i));
        }
        arrayStr.append("]");
        g2.drawString(arrayStr.toString(), 10, getHeight() - 10);

        // Draw tree
        int treeWidth = getWidth() - 40;
        drawNode(g2, 1, 20, treeWidth, 45);
    }

    private void drawNode(Graphics2D g2, int index, int left, int width, int y){
        /** draw a single node and its children recursively

        Draws the node circle with value, connecting lines to children,
        and recurses for left and right subtrees.
        */
        if(index > heap.size()) return;

        int x = left + width / 2;

        // Draw edges to children first (behind nodes)
        int childY = y + VERTICAL_GAP;
        int halfWidth = width / 2;

        if(index * 2 <= heap.size()){
            int leftChildX = left + halfWidth / 2;
            g2.setColor(new Color(150, 150, 150));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x, y + NODE_RADIUS, leftChildX, childY - NODE_RADIUS);
            drawNode(g2, index * 2, left, halfWidth, childY);
        }

        if(index * 2 + 1 <= heap.size()){
            int rightChildX = left + halfWidth + halfWidth / 2;
            g2.setColor(new Color(150, 150, 150));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x, y + NODE_RADIUS, rightChildX, childY - NODE_RADIUS);
            drawNode(g2, index * 2 + 1, left + halfWidth, halfWidth, childY);
        }

        // Draw node circle
        g2.setColor(new Color(70, 130, 210));
        g2.fillOval(x - NODE_RADIUS, y - NODE_RADIUS,
                NODE_RADIUS * 2, NODE_RADIUS * 2);
        g2.setColor(new Color(40, 80, 160));
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(x - NODE_RADIUS, y - NODE_RADIUS,
                NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Draw value
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        String text = String.valueOf(heap.get(index));
        FontMetrics fm = g2.getFontMetrics();
        int textX = x - fm.stringWidth(text) / 2;
        int textY = y + fm.getAscent() / 2 - 1;
        g2.drawString(text, textX, textY);
    }
}
