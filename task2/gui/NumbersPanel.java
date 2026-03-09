// NumbersPanel.java

package task2.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import task2.data.InputNumbers;

public class NumbersPanel extends JPanel {

    private JLabel[] numberLabels;

    public NumbersPanel() {

        int[] numbers = InputNumbers.getNumbers();

        setLayout(new GridLayout(numbers.length, 1));
        setBorder(BorderFactory.createTitledBorder("Input Numbers"));
        setPreferredSize(new Dimension(120, 0));

        numberLabels = new JLabel[numbers.length];

        Font font = new Font("Arial", Font.BOLD, 14);

        for (int i = 0; i < numbers.length; i++) {

            numberLabels[i] = new JLabel(String.valueOf(numbers[i]));
            numberLabels[i].setFont(font);
            numberLabels[i].setOpaque(true);
            numberLabels[i].setBackground(Color.WHITE);
            numberLabels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            numberLabels[i].setHorizontalAlignment(JLabel.CENTER);
            numberLabels[i].setPreferredSize(new Dimension(100, 40));

            add(numberLabels[i]);
        }
    }

    public void highlightProcessing(int index) {
        numberLabels[index].setBackground(Color.YELLOW);
    }

    public void highlightInserted(int index) {
        numberLabels[index].setBackground(Color.GREEN);
    }

    public void clearHighlights() {
        for (JLabel label : numberLabels) {
            label.setBackground(Color.WHITE);
        }
    }

}
