
// HeapGUI.java
package task1.gui;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.*;
import task1.heap.BinaryHeap;
import task1.task.*;

public class HeapGUI extends JFrame {

    private JTextArea output;
    private JTextField inputField;
    private int[] currentInput = {10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2};

    // Visual panels
    private TreePanel treePanel1;
    private TreePanel treePanel2;
    private TreePanel singleTreePanel;
    private JTextArea detailOutput;
    private JTextArea traversalOutput1;
    private JTextArea traversalOutput2;
    private GraphPanel graphPanel;
    private JPanel visualPanel;
    private CardLayout cardLayout;
    private String currentTask = "A";

    public HeapGUI(){
        /** GUI constructor

        Creates the graphical interface with visual tree diagrams,
        editable input array, and XY graphs for complexity.
        */

        setTitle("Seminar 3 – Binary Heap Tasks");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int)(screenSize.width * 0.9);
        int h = (int)(screenSize.height * 0.9);
        setSize(w, h);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // === TOP: Input array panel ===
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Input Array"));

        JPanel inputRow = new JPanel(new BorderLayout(5, 2));
        inputRow.add(new JLabel("  Array: "), BorderLayout.WEST);
        inputField = new JTextField(arrayToString(currentInput));
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 13));
        inputRow.add(inputField, BorderLayout.CENTER);

        JPanel arrayBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        JButton applyBtn = new JButton("Apply");
        applyBtn.addActionListener(ev -> applyInput());
        arrayBtns.add(applyBtn);

        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(ev -> addNumber());
        arrayBtns.add(addBtn);

        JButton removeBtn = new JButton("Remove Last");
        removeBtn.addActionListener(ev -> removeLast());
        arrayBtns.add(removeBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(ev -> {
            currentInput = new int[]{10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2};
            inputField.setText(arrayToString(currentInput));
        });
        arrayBtns.add(resetBtn);
        inputRow.add(arrayBtns, BorderLayout.EAST);

        topPanel.add(inputRow, BorderLayout.CENTER);

        // === Task buttons ===
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        JButton a = new JButton("Task 1a – Insert");
        JButton b = new JButton("Task 1b – BuildHeap");
        JButton c = new JButton("Task 1c – Traversals");
        JButton d = new JButton("Task 1d – Complexity");
        JButton e = new JButton("Task 1e – Priority Queue");
        JButton all = new JButton("Show All Text");

        a.addActionListener(ev -> runTaskA());
        b.addActionListener(ev -> runTaskB());
        c.addActionListener(ev -> runTaskC());
        d.addActionListener(ev -> runTaskD());
        e.addActionListener(ev -> runTaskE());
        all.addActionListener(ev -> runAll());

        buttons.add(a);
        buttons.add(b);
        buttons.add(c);
        buttons.add(d);
        buttons.add(e);
        buttons.add(all);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(buttons, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        // === CENTER: Visual panel (CardLayout for switching views) ===
        cardLayout = new CardLayout();
        visualPanel = new JPanel(cardLayout);

        // Detail view: single tree (left) + explanation text (right)
        singleTreePanel = new TreePanel("");
        detailOutput = new JTextArea();
        detailOutput.setFont(new Font("Monospaced", Font.PLAIN, 13));
        detailOutput.setEditable(false);
        detailOutput.setLineWrap(false);
        detailOutput.setMargin(new Insets(10, 14, 10, 14));
        JSplitPane detailSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                singleTreePanel, new JScrollPane(detailOutput));
        detailSplit.setResizeWeight(0.45);
        detailSplit.setDividerLocation(0.45);

        // Traversal view: two columns, each tree + its own text (for Task 1c)
        treePanel1 = new TreePanel("Tree 1 – Insert one-by-one");
        treePanel2 = new TreePanel("Tree 2 – BuildHeap");

        traversalOutput1 = new JTextArea();
        traversalOutput1.setFont(new Font("Monospaced", Font.PLAIN, 13));
        traversalOutput1.setEditable(false);
        traversalOutput1.setLineWrap(false);
        traversalOutput1.setMargin(new Insets(10, 14, 10, 14));

        traversalOutput2 = new JTextArea();
        traversalOutput2.setFont(new Font("Monospaced", Font.PLAIN, 13));
        traversalOutput2.setEditable(false);
        traversalOutput2.setLineWrap(false);
        traversalOutput2.setMargin(new Insets(10, 14, 10, 14));

        JSplitPane col1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                treePanel1, new JScrollPane(traversalOutput1));
        col1.setResizeWeight(0.5);
        col1.setDividerLocation(0.5);

        JSplitPane col2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                treePanel2, new JScrollPane(traversalOutput2));
        col2.setResizeWeight(0.5);
        col2.setDividerLocation(0.5);

        JSplitPane traversalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                col1, col2);
        traversalSplit.setResizeWeight(0.5);
        traversalSplit.setDividerLocation(0.5);

        // Graph view
        graphPanel = new GraphPanel("Time Complexity", "Input Size (N)", "Time (ns)");

        // Text view
        output = new JTextArea();
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setEditable(false);
        output.setMargin(new Insets(10, 14, 10, 14));
        JScrollPane textScroll = new JScrollPane(output);

        visualPanel.add(detailSplit, "DETAIL");
        visualPanel.add(traversalSplit, "TRAVERSAL");
        visualPanel.add(graphPanel, "GRAPH");
        visualPanel.add(textScroll, "TEXT");

        add(visualPanel, BorderLayout.CENTER);

        // Start by showing Task 1a
        runTaskA();

        setVisible(true);
    }

    // ---- Input handling ----

    private void parseInput(){
        /** parse the text field into an int array */
        try {
            String text = inputField.getText().trim();
            text = text.replaceAll("[\\[\\]]", "");
            String[] parts = text.split("[,\\s]+");
            currentInput = new int[parts.length];
            for(int i = 0; i < parts.length; i++){
                currentInput[i] = Integer.parseInt(parts[i].trim());
            }
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Use comma-separated integers.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyInput(){
        /** parse input and refresh current view */
        parseInput();
        refreshCurrentTask();
    }

    private void refreshCurrentTask(){
        /** re-run the current task to reflect input changes */
        switch(currentTask){
            case "A": runTaskA(); break;
            case "B": runTaskB(); break;
            case "C": runTaskC(); break;
            default: buildBothTrees(); break;
        }
    }

    private void addNumber(){
        /** add a number via input dialog */
        String val = JOptionPane.showInputDialog(this,
                "Enter a number to add:", "Add Number",
                JOptionPane.PLAIN_MESSAGE);
        if(val != null && !val.trim().isEmpty()){
            try {
                int num = Integer.parseInt(val.trim());
                int[] newArr = Arrays.copyOf(currentInput, currentInput.length + 1);
                newArr[currentInput.length] = num;
                currentInput = newArr;
                inputField.setText(arrayToString(currentInput));
                refreshCurrentTask();
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,
                        "Not a valid integer.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeLast(){
        /** remove last element from the input array */
        if(currentInput.length > 1){
            currentInput = Arrays.copyOf(currentInput, currentInput.length - 1);
            inputField.setText(arrayToString(currentInput));
            refreshCurrentTask();
        }
    }

    private String arrayToString(int[] arr){
        /** format array with commas */
        return Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    // ---- Build trees ----

    private void buildBothTrees(){
        /** build both heaps and update tree panels */
        BinaryHeap heap1 = new BinaryHeap(currentInput.length + 10);
        for(int v : currentInput){
            heap1.insert(v);
        }
        treePanel1.setHeap(heap1);

        BinaryHeap heap2 = new BinaryHeap(currentInput.length + 10);
        heap2.buildHeap(currentInput);
        treePanel2.setHeap(heap2);
    }

    // ---- Task runners ----

    private void runTaskA(){
        /** run Task 1a with current input – single tree + explanation */
        currentTask = "A";
        parseInput();

        BinaryHeap heap = new BinaryHeap(currentInput.length + 10);
        for(int v : currentInput){
            heap.insert(v);
        }

        singleTreePanel.setTitle("Tree 1 – Insert ett-i-taget");
        singleTreePanel.setHeap(heap);
        detailOutput.setText(TaskA.run(currentInput));
        detailOutput.setCaretPosition(0);
        cardLayout.show(visualPanel, "DETAIL");
    }

    private void runTaskB(){
        /** run Task 1b with current input – single tree + explanation */
        currentTask = "B";
        parseInput();

        BinaryHeap heap = new BinaryHeap(currentInput.length + 10);
        heap.buildHeap(currentInput);

        singleTreePanel.setTitle("Tree 2 – BuildHeap (bottom-up)");
        singleTreePanel.setHeap(heap);
        detailOutput.setText(TaskB.run(currentInput));
        detailOutput.setCaretPosition(0);
        cardLayout.show(visualPanel, "DETAIL");
    }

    private void runTaskC(){
        /** run Task 1c: all 4 traversals on both trees + explanation */
        currentTask = "C";
        parseInput();

        BinaryHeap tree1 = new BinaryHeap(currentInput.length + 10);
        for(int v : currentInput){
            tree1.insert(v);
        }
        BinaryHeap tree2 = new BinaryHeap(currentInput.length + 10);
        tree2.buildHeap(currentInput);

        treePanel1.setHeap(tree1);
        treePanel2.setHeap(tree2);

        // ── Left column: Tree 1 text ──
        StringBuilder sb1 = new StringBuilder();
        sb1.append("Tree 1 – Insert ett-i-taget\n");
        sb1.append("═══════════════════════════════════\n\n");
        sb1.append("Metod: Insert element ett i taget med\n");
        sb1.append("percolate UP från botten till rot.\n\n");
        sb1.append("Traverseringar:\n");
        sb1.append("───────────────────────────────────\n");
        sb1.append("Level-order: ").append(tree1.levelOrder()).append("\n");
        sb1.append("Pre-order:   ").append(tree1.preOrder()).append("\n");
        sb1.append("In-order:    ").append(tree1.inOrder()).append("\n");
        sb1.append("Post-order:  ").append(tree1.postOrder()).append("\n\n");

        sb1.append("Förklaring:\n");
        sb1.append("───────────────────────────────────\n");
        sb1.append("• Level-order: Noder nivå för nivå,\n");
        sb1.append("  vänster till höger = arrayordning.\n\n");
        sb1.append("• Pre-order: Rot → vänster → höger.\n");
        sb1.append("  Besöker roten först.\n\n");
        sb1.append("• In-order: Vänster → rot → höger.\n");
        sb1.append("  Ger EJ sorterad ordning i en heap\n");
        sb1.append("  (bara i BST).\n\n");
        sb1.append("• Post-order: Vänster → höger → rot.\n");
        sb1.append("  Besöker barnen före föräldern.\n");

        traversalOutput1.setText(sb1.toString());
        traversalOutput1.setCaretPosition(0);

        // ── Right column: Tree 2 text ──
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Tree 2 – BuildHeap (bottom-up)\n");
        sb2.append("═══════════════════════════════════\n\n");
        sb2.append("Metod: Kopiera hela arrayen och anropa\n");
        sb2.append("percolateDown från ⌊n/2⌋ ner till rot.\n\n");
        sb2.append("Traverseringar:\n");
        sb2.append("───────────────────────────────────\n");
        sb2.append("Level-order: ").append(tree2.levelOrder()).append("\n");
        sb2.append("Pre-order:   ").append(tree2.preOrder()).append("\n");
        sb2.append("In-order:    ").append(tree2.inOrder()).append("\n");
        sb2.append("Post-order:  ").append(tree2.postOrder()).append("\n\n");

        sb2.append("Jämförelse:\n");
        sb2.append("───────────────────────────────────\n");
        sb2.append("Båda träden är giltiga min-heapar\n");
        sb2.append("men har OLIKA intern struktur p.g.a.\n");
        sb2.append("olika byggmetoder.\n\n");
        sb2.append("Därför ger traverseringarna olika\n");
        sb2.append("resultat även om båda uppfyller\n");
        sb2.append("heap-egenskapen (förälder ≤ barn).\n\n");
        sb2.append("BuildHeap är O(n) jämfört med\n");
        sb2.append("O(n log n) för insert ett-i-taget.\n");

        traversalOutput2.setText(sb2.toString());
        traversalOutput2.setCaretPosition(0);

        cardLayout.show(visualPanel, "TRAVERSAL");
    }

    private void runTaskD(){
        /** run Task 1d: measure complexity with graph */
        currentTask = "D";
        parseInput();
        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};

        double[] xData = new double[sizes.length];
        double[] yInsert = new double[sizes.length];
        double[] yBuild = new double[sizes.length];

        StringBuilder sb = new StringBuilder();
        sb.append("Task 1d – Complexity Measurement\n\n");
        sb.append(String.format("%-10s %22s %22s\n", "N", "Insert one-by-one (ns)", "BuildHeap (ns)"));
        sb.append("─".repeat(56)).append("\n");

        for(int si = 0; si < sizes.length; si++){
            int n = sizes[si];
            Random rand = new Random(42);
            int[] input = new int[n];
            for(int i = 0; i < n; i++){
                input[i] = rand.nextInt(n * 10);
            }

            // Warm up
            BinaryHeap warmup = new BinaryHeap(n + 10);
            for(int v : input) warmup.insert(v);
            warmup = new BinaryHeap(n + 10);
            warmup.buildHeap(input);

            // Algorithm 1: Insert one-by-one
            long start1 = System.nanoTime();
            BinaryHeap heap1 = new BinaryHeap(n + 10);
            for(int v : input) heap1.insert(v);
            long time1 = System.nanoTime() - start1;

            // Algorithm 2: BuildHeap
            long start2 = System.nanoTime();
            BinaryHeap heap2 = new BinaryHeap(n + 10);
            heap2.buildHeap(input);
            long time2 = System.nanoTime() - start2;

            xData[si] = n;
            yInsert[si] = time1;
            yBuild[si] = time2;

            sb.append(String.format("%-10d %19d ns %19d ns\n", n, time1, time2));
        }

        sb.append("\nInsert one-by-one: O(n log n) worst case\n");
        sb.append("BuildHeap:         O(n) using bottom-up construction\n");

        graphPanel.clearData();
        graphPanel.addSeries("Insert one-by-one  O(n log n)", xData, yInsert);
        graphPanel.addSeries("BuildHeap  O(n)", xData, yBuild);

        output.setText(sb.toString());
        cardLayout.show(visualPanel, "GRAPH");
    }

    private void runTaskE(){
        /** run Task 1e: compare deleteMin vs insert costs with graph */
        currentTask = "E";
        parseInput();
        int[] sizes = {100, 500, 1000, 5000, 10000};
        int opsPerSize = 1000;

        double[] xData = new double[sizes.length];
        double[] yInsertAvg = new double[sizes.length];
        double[] yDeleteAvg = new double[sizes.length];

        StringBuilder sb = new StringBuilder();
        sb.append("Task 1e – Priority Queue: deleteMin vs insert\n\n");
        sb.append(String.format("%-8s %20s %20s\n", "N", "Avg Insert (ns)", "Avg DeleteMin (ns)"));
        sb.append("─".repeat(50)).append("\n");

        for(int si = 0; si < sizes.length; si++){
            int n = sizes[si];
            Random rand = new Random(42);
            int[] input = new int[n];
            for(int i = 0; i < n; i++){
                input[i] = rand.nextInt(n * 10);
            }

            // Build heap for insert test
            BinaryHeap heapIns = new BinaryHeap(n + opsPerSize + 10);
            heapIns.buildHeap(input);

            long start = System.nanoTime();
            for(int i = 0; i < opsPerSize; i++){
                heapIns.insert(rand.nextInt(n * 10));
            }
            long insertTime = System.nanoTime() - start;

            // Build heap for deleteMin test
            BinaryHeap heapDel = new BinaryHeap(n + 10);
            heapDel.buildHeap(input);

            start = System.nanoTime();
            int delOps = Math.min(opsPerSize, n - 1);
            for(int i = 0; i < delOps; i++){
                heapDel.deleteMin();
            }
            long deleteTime = System.nanoTime() - start;

            xData[si] = n;
            yInsertAvg[si] = (double) insertTime / opsPerSize;
            yDeleteAvg[si] = (double) deleteTime / delOps;

            sb.append(String.format("%-8d %17.0f ns %17.0f ns\n",
                    n, yInsertAvg[si], yDeleteAvg[si]));
        }

        sb.append("\nConclusion:\n");
        sb.append("Insert is O(log n) worst case but often O(1) average.\n");
        sb.append("DeleteMin is always O(log n) since the new root must\n");
        sb.append("percolate down through the entire height of the tree.\n");
        sb.append("Therefore deleteMin is typically more expensive than insert.\n");

        graphPanel.clearData();
        graphPanel.addSeries("Avg Insert", xData, yInsertAvg);
        graphPanel.addSeries("Avg DeleteMin", xData, yDeleteAvg);

        output.setText(sb.toString());
        cardLayout.show(visualPanel, "GRAPH");
    }

    private void runAll(){
        /** show all text output */
        currentTask = "ALL";
        parseInput();
        output.setText(
                TaskA.run() + "\n\n" +
                TaskB.run() + "\n\n" +
                TaskC.run() + "\n\n" +
                TaskD.run() + "\n\n" +
                TaskE.run());
        cardLayout.show(visualPanel, "TEXT");
    }
}
