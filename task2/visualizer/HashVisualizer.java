// HashVisualizer.java

package task2.visualizer;

import task2.gui.HashGUI;
import task2.gui.TablePanel;
import task2.gui.ControlPanel;

import task2.data.InputNumbers;

import task2.hash.HashTableChaining;
import task2.hash.HashTableLinearProbing;
import task2.hash.HashTableQuadraticProbing;
import task2.hash.HashFunction;

import javax.swing.SwingUtilities;

public class HashVisualizer implements Runnable {

    private HashGUI gui;
    private TablePanel tablePanel;
    private ControlPanel controlPanel;

    private boolean running = false;
    private boolean paused = false;

    public HashVisualizer(HashGUI gui, TablePanel tablePanel, ControlPanel controlPanel) {
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
        this.tablePanel = tablePanel;
        this.controlPanel = controlPanel;

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

        for (int number : numbers) {

            if (!running) break;

            waitIfPaused();

            int index = hashFunction.hash(number);

            updateCalculation(number + " mod 10 = " + index);

            highlightProcessing(index);

            sleep(delay);

            int finalIndex = index;

            if (algorithm.equals("CHAINING")) {

                finalIndex = chaining.insert(number);

            }

            if (algorithm.equals("LINEAR")) {

                finalIndex = linear.insert(number);

            }

            if (algorithm.equals("QUADRATIC")) {

                finalIndex = quadratic.insert(number);

            }

            if (algorithm.equals("CHAINING")) {
                appendValue(finalIndex, number);
            } else {
                insertValue(finalIndex, number);
            }

            highlightInserted(finalIndex);

            sleep(delay);

        }

        running = false;

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