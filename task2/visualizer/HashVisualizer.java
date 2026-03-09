// HashVisualizer.java

package task2.visualizer;

import javax.swing.SwingUtilities;
import task2.data.InputNumbers;
import task2.gui.ControlPanel;
import task2.gui.HashGUI;
import task2.gui.NumbersPanel;
import task2.gui.ProcessPanel;
import task2.gui.TablePanel;
import task2.hash.HashFunction;
import task2.hash.HashTableChaining;
import task2.hash.HashTableLinearProbing;
import task2.hash.HashTableQuadraticProbing;

public class HashVisualizer implements Runnable {

    private HashGUI gui;
    private NumbersPanel numbersPanel;
    private TablePanel tablePanel;
    private ControlPanel controlPanel;
    private ProcessPanel processPanel;

    private boolean running = false;
    private boolean paused = false;

    public HashVisualizer(HashGUI gui, NumbersPanel numbersPanel, TablePanel tablePanel, ControlPanel controlPanel, ProcessPanel processPanel) {
    /**
     * Creates the visualizer controller.
     *
     * This class connects the GUI with the hashing algorithms.
     * It controls:
     *
     * animation
     * delay
     * highlighting
     * algorithm execution
     *
     * The visualizer runs in its own thread so the GUI
     * remains responsive while the animation runs.
     */

        this.gui = gui;
        this.numbersPanel = numbersPanel;
        this.tablePanel = tablePanel;
        this.controlPanel = controlPanel;
        this.processPanel = processPanel;

    }

    public void start() {
    /**
     * Starts the animation thread.
     */

        if (!running) {

            running = true;
            paused = false;

            new Thread(this).start();

        }

    }

    public void pause() {
    /**
     * Pauses the animation.
     */

        paused = true;

    }

    public void resume() {
    /**
     * Resumes the animation.
     */

        paused = false;

    }

    public void stop() {
    /**
     * Stops the animation completely.
     */

        running = false;

    }

    @Override
    public void run() {
    /**
     * Main animation loop.
     *
     * This method:
     *
     * 1 reads the numbers
     * 2 selects the algorithm
     * 3 processes numbers step-by-step
     * 4 updates GUI
     */

        int[] numbers = InputNumbers.getNumbers();

        int delay = controlPanel.getDelay();

        String algorithm = controlPanel.getSelectedAlgorithm();

        HashFunction hashFunction = new HashFunction(10);

        HashTableChaining chaining = new HashTableChaining(10);
        HashTableLinearProbing linear = new HashTableLinearProbing(10);
        HashTableQuadraticProbing quadratic = new HashTableQuadraticProbing(10);

        String algorithmName = switch (algorithm) {
            case "LINEAR" -> "Linear Probing";
            case "QUADRATIC" -> "Quadratic Probing";
            default -> "Separate Chaining";
        };

        updateProcess("Algorithm: " + algorithmName + "\n\nStarting insertion...\n\n");

        for (int i = 0; i < numbers.length; i++) {

            int number = numbers[i];

            if (!running) break;

            waitIfPaused();

            highlightNumberProcessing(i);

            int index = hashFunction.hash(number);

            StringBuilder process = new StringBuilder();
            process.append("Step ").append(i + 1).append(":\n");
            process.append("Processing number: ").append(number).append("\n");
            process.append("Hash calculation: ").append(number).append(" mod 10 = ").append(index).append("\n");

            updateCalculation(number + " mod 10 = " + index);

            highlightProcessing(index);

            sleep(delay);

            int finalIndex = index;

            if (algorithm.equals("CHAINING")) {

                finalIndex = chaining.insert(number);
                process.append("Inserted ").append(number).append(" at index ").append(finalIndex).append("\n\n");

            }

            if (algorithm.equals("LINEAR")) {

                int probeIndex = index;
                while (linear.getTable()[probeIndex] != null) {
                    process.append("Collision detected at index ").append(probeIndex).append("\n");
                    probeIndex = (probeIndex + 1) % 10;
                    process.append("Linear probing -> checking index ").append(probeIndex).append("\n");
                }
                finalIndex = linear.insert(number);
                process.append("Inserted ").append(number).append(" at index ").append(finalIndex).append("\n\n");

            }

            if (algorithm.equals("QUADRATIC")) {

                int probeIndex = index;
                int step = 1;
                while (quadratic.getTable()[probeIndex] != null) {
                    process.append("Collision detected at index ").append(probeIndex).append("\n");
                    probeIndex = (index + (step * step)) % 10;
                    process.append("Quadratic probing -> checking index ").append(probeIndex).append("\n");
                    step++;
                }
                finalIndex = quadratic.insert(number);
                process.append("Inserted ").append(number).append(" at index ").append(finalIndex).append("\n\n");

            }

            appendProcess(process.toString());

            if (algorithm.equals("CHAINING")) {
                appendValue(finalIndex, number);
            } else {
                insertValue(finalIndex, number);
            }

            highlightNumberInserted(i);
            highlightInserted(finalIndex);

            sleep(delay);

        }

        running = false;

    }

    private void highlightNumberProcessing(int index) {
    /**
     * Highlights the current number in NumbersPanel as processing (yellow).
     */

        SwingUtilities.invokeLater(() -> {

            numbersPanel.clearHighlights();
            numbersPanel.highlightProcessing(index);

        });

    }

    private void highlightNumberInserted(int index) {
    /**
     * Highlights the current number in NumbersPanel as inserted (green).
     */

        SwingUtilities.invokeLater(() -> {

            numbersPanel.highlightInserted(index);

        });

    }

    private void highlightProcessing(int index) {
    /**
     * Highlights the current cell as processing (yellow).
     */

        SwingUtilities.invokeLater(() -> {

            tablePanel.clearHighlights();
            tablePanel.highlightProcessing(index);

        });

    }

    private void highlightInserted(int index) {
    /**
     * Highlights the final inserted cell (green).
     */

        SwingUtilities.invokeLater(() -> {

            tablePanel.highlightInserted(index);

        });

    }

    private void insertValue(int index, int value) {
    /**
     * Inserts the value into the visual table.
     */

        SwingUtilities.invokeLater(() -> {

            tablePanel.setValue(index, String.valueOf(value));

        });

    }

    private void appendValue(int index, int value) {
    /**
     * Appends a value in the visual table for chaining.
     */

        SwingUtilities.invokeLater(() -> {

            tablePanel.appendValue(index, String.valueOf(value));

        });

    }

    private void updateCalculation(String text) {
    /**
     * Updates the calculation label in the GUI.
     */

        SwingUtilities.invokeLater(() -> {

            gui.updateCalculation(text);

        });

    }

    private void appendProcess(String text) {
    /**
     * Appends text to the process explanation panel.
     */

        SwingUtilities.invokeLater(() -> {

            processPanel.appendProcess(text);

        });

    }

    private void updateProcess(String text) {
    /**
     * Updates the process explanation panel.
     */

        SwingUtilities.invokeLater(() -> {

            processPanel.updateProcess(text);

        });

    }

    private void sleep(int delay) {
    /**
     * Delays the animation.
     */

        try {

            Thread.sleep(delay);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

    private void waitIfPaused() {
    /**
     * Waits while the animation is paused.
     */

        while (paused) {

            sleep(100);

        }

    }

}