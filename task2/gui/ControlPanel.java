// ControlPanel.java

package task2.gui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Control panel for the Hash Table Visualizer.
 *
 * This panel contains:
 * - Algorithm selection
 * - Run / Pause / Clean buttons
 * - Delay configuration
 *
 * The panel itself does not execute algorithms.
 * Instead it provides user controls that the main
 * GUI will later connect to the hashing logic.
 */
public class ControlPanel extends JPanel {

    private JButton runButton;
    private JButton pauseButton;
    private JButton cleanButton;

    private JTextField delayField;

    private JRadioButton chainingButton;
    private JRadioButton linearButton;
    private JRadioButton quadraticButton;

    public ControlPanel() {
    /**
     * Constructor that builds the control interface.
     *
     * Layout:
     * Algorithm selection
     * Run button
     * Pause button
     * Clean button
     * Delay configuration
     */

        setLayout(new GridLayout(9, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Controls"));

        createAlgorithmSelection();
        createControlButtons();
        createDelayInput();
    }

    private void createAlgorithmSelection() {
    /**
     * Creates the radio buttons used to select
     * the collision resolution strategy.
     */

        JLabel label = new JLabel("Algorithm");

        chainingButton = new JRadioButton("Separate Chaining");
        linearButton = new JRadioButton("Linear Probing");
        quadraticButton = new JRadioButton("Quadratic Probing");

        ButtonGroup group = new ButtonGroup();
        group.add(chainingButton);
        group.add(linearButton);
        group.add(quadraticButton);

        chainingButton.setSelected(true);

        add(label);
        add(chainingButton);
        add(linearButton);
        add(quadraticButton);
    }

    private void createControlButtons() {
    /**
     * Creates the Run, Pause, and Clean buttons.
     */

        runButton = new JButton("Run");
        pauseButton = new JButton("Pause");
        cleanButton = new JButton("Clean");

        add(runButton);
        add(pauseButton);
        add(cleanButton);
    }

    private void createDelayInput() {
    /**
     * Creates the delay configuration input.
     *
     * The user can specify delay time in milliseconds.
     * This value will control the animation speed.
     */

        JLabel delayLabel = new JLabel("Delay (ms)");

        delayField = new JTextField("1000");

        add(delayLabel);
        add(delayField);
    }

    public int getDelay() {
    /**
     * Returns the delay value entered by the user.
     *
     * If the value cannot be parsed, the default
     * delay of 1000 ms is returned.
     */
        try {
            return Integer.parseInt(delayField.getText());
        } catch (Exception e) {
            return 1000;
        }
    }

    public JButton getRunButton() {
    /**
     * Provides access to the Run button so that
     * the main GUI can attach event handlers.
     */
        return runButton;
    }

    public JButton getPauseButton() {
    /**
     * Provides access to the Pause button.
     */
        return pauseButton;
    }

    public JButton getCleanButton() {
    /**
     * Provides access to the Clean button.
     */
        return cleanButton;
    }

    public String getSelectedAlgorithm() {
    /**
     * Returns the currently selected collision
     * resolution algorithm.
     */

        if (linearButton.isSelected()) {
            return "LINEAR";
        }

        if (quadraticButton.isSelected()) {
            return "QUADRATIC";
        }

        return "CHAINING";
    }

}