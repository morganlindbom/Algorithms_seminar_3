// HashGUI.java

package task2.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;

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

    private TablePanel tablePanel;
    private ControlPanel controlPanel;

    private JLabel calculationLabel;
    private JLabel explanationLabel;

    public HashGUI() {
    /**
     * Constructor that initializes the GUI window and layout.
     *
     * The window uses a BorderLayout to organize the interface:
     *
     * NORTH  -> numbers and hash function
     * CENTER -> visual hash table
     * SOUTH  -> explanation text
     * EAST   -> control panel (Run / Pause / Clean / Delay)
     */

        setTitle("Hash Table Visualizer - Task 2");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createTopPanel();
        createCenterPanel();
        createControlPanel();
        createExplanationPanel();
        connectVisualizer();

        setVisible(true);
    }

    private void connectVisualizer() {
    /**
     * Connects the HashVisualizer to the control buttons.
     */

        HashVisualizer visualizer = new HashVisualizer(this, tablePanel, controlPanel);

        controlPanel.getRunButton().addActionListener(e -> {
            tablePanel.clearTable();
            visualizer.stop();
            HashVisualizer newVis = new HashVisualizer(this, tablePanel, controlPanel);
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
            controlPanel.getPauseButton().setText("Pause");
            updateCalculation("");
        });
    }

    private void createTopPanel() {
    /**
     * Creates the top information panel.
     *
     * This panel shows:
     * - the numbers used in the task
     * - the hash function definition
     * - the current hash calculation
     */

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3,1));

        JLabel numbersLabel = new JLabel(
            "Numbers: 4371   1323   6173   4199   4344   9679   1989"
        );
        numbersLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel hashLabel = new JLabel("Hash Function:  h(x) = x mod 10");
        hashLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        calculationLabel = new JLabel("Calculation: ");
        calculationLabel.setFont(new Font("Arial", Font.BOLD, 14));

        topPanel.add(numbersLabel);
        topPanel.add(hashLabel);
        topPanel.add(calculationLabel);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
    /**
     * Creates the center panel containing the visual hash table.
     */

        tablePanel = new TablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }

    private void createControlPanel() {
    /**
     * Creates the control panel containing buttons
     * and delay configuration.
     */

        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.EAST);
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