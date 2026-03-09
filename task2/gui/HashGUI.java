// HashGUI.java

package task2.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import task2.visualizer.HashVisualizer;

/**
 * Main graphical interface for the Hash Table Visualizer.
 *
 * This class builds the main application window and arranges
 * all visual components used in Task 2.
 *
 * The GUI contains:
 * - Numbers panel (input numbers)
 * - Hash function panel
 * - Hash calculation display
 * - Table panel (visual hash table)
 * - Control panel (Run / Pause / Clean / Delay)
 * - Explanation panel describing algorithm behaviour
 *
 * The GUI itself does not execute hashing algorithms. Instead
 * it provides the visual framework where the algorithms will
 * be animated step-by-step.
 */
public class HashGUI extends JFrame {

    private NumbersPanel numbersPanel;
    private TablePanel tablePanel;
    private ControlPanel controlPanel;
    private ProcessPanel processPanel;

    private JLabel calculationLabel;
    private JLabel explanationLabel;

    public HashGUI() {
    /**
     * Constructor that initializes the GUI window and layout.
     *
     * The window uses a BorderLayout to organize the interface:
     *
     * NORTH  -> hash function and calculation
     * CENTER -> numbers panel (WEST) + table (CENTER) + controls (EAST)
     * SOUTH  -> explanation text
     */

        setTitle("Hash Table Visualizer - Task 2");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createTopPanel();
        createMainPanel();
        createExplanationPanel();
        connectVisualizer();

        setVisible(true);
    }

    private void connectVisualizer() {
    /**
     * Connects the HashVisualizer to the control buttons.
     */

        controlPanel.getRunButton().addActionListener(e -> {
            tablePanel.clearTable();
            numbersPanel.clearHighlights();
            processPanel.clear();
            Object oldVis = controlPanel.getRunButton().getClientProperty("visualizer");
            if (oldVis instanceof HashVisualizer v) {
                v.stop();
            }
            HashVisualizer newVis = new HashVisualizer(this, numbersPanel, tablePanel, controlPanel, processPanel);
            controlPanel.getRunButton().putClientProperty("visualizer", newVis);
            newVis.start();
        });

        controlPanel.getPauseButton().addActionListener(e -> {
            Object vis = controlPanel.getRunButton().getClientProperty("visualizer");
            if (vis instanceof HashVisualizer v) {
                if (controlPanel.getPauseButton().getText().equals("Pause")) {
                    v.pause();
                    controlPanel.getPauseButton().setText("Resume");
                } else {
                    v.resume();
                    controlPanel.getPauseButton().setText("Pause");
                }
            }
        });

        controlPanel.getCleanButton().addActionListener(e -> {
            Object vis = controlPanel.getRunButton().getClientProperty("visualizer");
            if (vis instanceof HashVisualizer v) {
                v.stop();
            }
            tablePanel.clearTable();
            numbersPanel.clearHighlights();
            processPanel.clear();
            controlPanel.getPauseButton().setText("Pause");
            updateCalculation("");
        });
    }

    private void createTopPanel() {
    /**
     * Creates the top information panel.
     *
     * This panel shows:
     * - the hash function definition
     * - the current hash calculation
     */

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        JLabel hashLabel = new JLabel("Hash Function:  h(x) = x mod 10");
        hashLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        calculationLabel = new JLabel("Calculation: ");
        calculationLabel.setFont(new Font("Arial", Font.BOLD, 14));

        topPanel.add(hashLabel);
        topPanel.add(calculationLabel);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createMainPanel() {
    /**
     * Creates the main center panel containing:
     * WEST   -> NumbersPanel (input numbers)
     * CENTER -> TablePanel (hash table)
     * EAST   -> ControlPanel (buttons and delay)
     */

        numbersPanel = new NumbersPanel();
        tablePanel = new TablePanel();
        controlPanel = new ControlPanel();
        processPanel = new ProcessPanel();

        JPanel numbersWrapper = new JPanel(new BorderLayout());
        numbersWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        numbersWrapper.add(numbersPanel);

        JPanel tableWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tableWrapper.add(tablePanel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(processPanel, BorderLayout.CENTER);
        rightPanel.add(controlPanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(numbersWrapper, BorderLayout.WEST);
        centerPanel.add(tableWrapper, BorderLayout.CENTER);
        centerPanel.add(rightPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void createExplanationPanel() {
    /**
     * Creates the explanation panel displayed at the bottom
     * of the window.
     *
     * This panel explains what the visualization is showing.
     */

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Explanation"));

        explanationLabel = new JLabel(
            "Yellow = number currently processed. Green = number successfully inserted."
        );

        bottomPanel.add(explanationLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateCalculation(String text) {
    /**
     * Updates the hash calculation display.
     *
     * Example:
     * 6173 mod 10 = 3
     */
        calculationLabel.setText("Calculation: " + text);
    }

    public void updateExplanation(String text) {
    /**
     * Updates the explanation text shown at the bottom.
     */
        explanationLabel.setText(text);
    }

}