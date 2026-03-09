// TablePanel.java

package task2.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Visual representation of the hash table.
 *
 * This panel displays the hash table with indices 0–9.
 * Each row shows:
 *
 * index | value
 *
 * The panel also supports visual highlighting:
 * - Yellow: number currently being processed
 * - Green: number successfully inserted
 *
 * The table can be reset using the clearTable() method.
 */
public class TablePanel extends JPanel {

    private JLabel[] indexLabels;
    private JLabel[] valueLabels;

    public TablePanel() {
    /**
     * Constructor that builds the visual hash table.
     *
     * The table uses a GridLayout with two columns:
     * index | value
     */

        setLayout(new GridLayout(10, 2, 6, 6));
        setBorder(BorderFactory.createTitledBorder("Hash Table"));

        indexLabels = new JLabel[10];
        valueLabels = new JLabel[10];

        Font font = new Font("Arial", Font.BOLD, 14);

        for (int i = 0; i < 10; i++) {

            indexLabels[i] = new JLabel(String.valueOf(i));
            indexLabels[i].setFont(font);
            indexLabels[i].setPreferredSize(new Dimension(30, 40));
            indexLabels[i].setHorizontalAlignment(JLabel.CENTER);

            valueLabels[i] = new JLabel("");
            valueLabels[i].setOpaque(true);
            valueLabels[i].setBackground(Color.WHITE);
            valueLabels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            valueLabels[i].setFont(font);
            valueLabels[i].setPreferredSize(new Dimension(120, 40));
            valueLabels[i].setHorizontalAlignment(JLabel.LEFT);

            add(indexLabels[i]);
            add(valueLabels[i]);
        }
    }

    public void setValue(int index, String value) {
    /**
     * Sets a value at a specific index in the table.
     *
     * This method is used when a number is inserted
     * into the hash table.
     */
        valueLabels[index].setText(value);
    }

    public void appendValue(int index, String value) {
    /**
     * Appends a value at a specific index in the table.
     *
     * Used for separate chaining where multiple values
     * can exist at the same index.
     */
        String current = valueLabels[index].getText();
        if (current.isEmpty()) {
            valueLabels[index].setText(value);
        } else {
            valueLabels[index].setText(current + ", " + value);
        }
    }

    public void highlightProcessing(int index) {
    /**
     * Highlights a cell as currently being processed.
     *
     * The cell background becomes yellow.
     */
        valueLabels[index].setBackground(Color.YELLOW);
    }

    public void highlightInserted(int index) {
    /**
     * Highlights a cell as successfully inserted.
     *
     * The cell background becomes green.
     */
        valueLabels[index].setBackground(Color.GREEN);
    }

    public void clearHighlights() {
    /**
     * Removes highlight colors from all cells.
     */
        for (int i = 0; i < 10; i++) {
            valueLabels[i].setBackground(Color.WHITE);
        }
    }

    public void clearTable() {
    /**
     * Clears the entire table.
     *
     * This removes all values and resets colors.
     */
        for (int i = 0; i < 10; i++) {
            valueLabels[i].setText("");
            valueLabels[i].setBackground(Color.WHITE);
        }
    }

}