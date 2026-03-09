
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

    /** Fixerad array från uppgiften – ändras ALDRIG */
    private static final int[] FIXED_INPUT = {10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2};

    private JTextArea output;
    private JTextField inputField;
    private int[] currentInput = FIXED_INPUT.clone();

    // Visual panels
    private TreePanel treePanel1;
    private TreePanel treePanel2;
    private TreePanel singleTreePanel;
    private JTextArea detailOutput;
    private JTextArea traversalOutput1;
    private JTextArea traversalOutput2;
    private GraphPanel graphPanel;
    private JTextArea graphExplanation;
    private JPanel visualPanel;
    private CardLayout cardLayout;
    private String currentTask = "A";

    public HeapGUI(){
        /** GUI constructor

        Creates the graphical interface with visual tree diagrams,
        editable input array, and XY graphs for complexity.
        */

        setTitle("Seminar 3 – Tasks 1");
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
            currentInput = FIXED_INPUT.clone();
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

        // Graph view with explanation text below
        graphPanel = new GraphPanel("Time Complexity", "Input Size (N)", "Time (ns)");
        graphExplanation = new JTextArea();
        graphExplanation.setFont(new Font("SansSerif", Font.PLAIN, 13));
        graphExplanation.setEditable(false);
        graphExplanation.setLineWrap(true);
        graphExplanation.setWrapStyleWord(true);
        graphExplanation.setMargin(new Insets(10, 14, 10, 14));
        graphExplanation.setBackground(new Color(245, 245, 250));

        JSplitPane graphWithText = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                graphPanel, new JScrollPane(graphExplanation));
        graphWithText.setResizeWeight(0.6);
        graphWithText.setDividerLocation(0.6);

        // Text view
        output = new JTextArea();
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setEditable(false);
        output.setMargin(new Insets(10, 14, 10, 14));
        JScrollPane textScroll = new JScrollPane(output);

        visualPanel.add(detailSplit, "DETAIL");
        visualPanel.add(traversalSplit, "TRAVERSAL");
        visualPanel.add(graphWithText, "GRAPH");
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
        /** run Task 1a – tree from input, text from FIXED array */
        currentTask = "A";
        parseInput();

        BinaryHeap heap = new BinaryHeap(currentInput.length + 10);
        for(int v : currentInput){
            heap.insert(v);
        }

        singleTreePanel.setTitle("Tree 1 – Insert ett-i-taget");
        singleTreePanel.setHeap(heap);
        detailOutput.setText(TaskA.run(FIXED_INPUT));
        detailOutput.setCaretPosition(0);
        cardLayout.show(visualPanel, "DETAIL");
    }

    private void runTaskB(){
        /** run Task 1b – tree from input, text from FIXED array */
        currentTask = "B";
        parseInput();

        BinaryHeap heap = new BinaryHeap(currentInput.length + 10);
        heap.buildHeap(currentInput);

        singleTreePanel.setTitle("Tree 2 – BuildHeap (bottom-up)");
        singleTreePanel.setHeap(heap);
        detailOutput.setText(TaskB.run(FIXED_INPUT));
        detailOutput.setCaretPosition(0);
        cardLayout.show(visualPanel, "DETAIL");
    }

    private void runTaskC(){
        /** run Task 1c – trees from input, text from FIXED array */
        currentTask = "C";
        parseInput();

        // Tree diagrams use currentInput
        BinaryHeap tree1 = new BinaryHeap(currentInput.length + 10);
        for(int v : currentInput){
            tree1.insert(v);
        }
        BinaryHeap tree2 = new BinaryHeap(currentInput.length + 10);
        tree2.buildHeap(currentInput);

        treePanel1.setHeap(tree1);
        treePanel2.setHeap(tree2);

        // Explanatory text uses FIXED array
        BinaryHeap fixed1 = new BinaryHeap(FIXED_INPUT.length + 10);
        for(int v : FIXED_INPUT) fixed1.insert(v);
        BinaryHeap fixed2 = new BinaryHeap(FIXED_INPUT.length + 10);
        fixed2.buildHeap(FIXED_INPUT);

        // ── Left column: Tree 1 text ──
        StringBuilder sb1 = new StringBuilder();
        sb1.append("Tree 1 – Insert ett-i-taget\n");
        sb1.append("═══════════════════════════════════\n\n");
        sb1.append("Fixerad input-array:\n");
        sb1.append("[").append(arrayToString(FIXED_INPUT)).append("]\n\n");
        sb1.append("Metod: Insert element ett i taget med\n");
        sb1.append("percolate UP från botten till rot.\n\n");
        sb1.append("Traverseringar:\n");
        sb1.append("───────────────────────────────────\n");
        sb1.append("Level-order: ").append(fixed1.levelOrder()).append("\n");
        sb1.append("Pre-order:   ").append(fixed1.preOrder()).append("\n");
        sb1.append("In-order:    ").append(fixed1.inOrder()).append("\n");
        sb1.append("Post-order:  ").append(fixed1.postOrder()).append("\n\n");

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
        sb2.append("Fixerad input-array:\n");
        sb2.append("[").append(arrayToString(FIXED_INPUT)).append("]\n\n");
        sb2.append("Metod: Kopiera hela arrayen och anropa\n");
        sb2.append("percolateDown från ⌊n/2⌋ ner till rot.\n\n");
        sb2.append("Traverseringar:\n");
        sb2.append("───────────────────────────────────\n");
        sb2.append("Level-order: ").append(fixed2.levelOrder()).append("\n");
        sb2.append("Pre-order:   ").append(fixed2.preOrder()).append("\n");
        sb2.append("In-order:    ").append(fixed2.inOrder()).append("\n");
        sb2.append("Post-order:  ").append(fixed2.postOrder()).append("\n\n");

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

    private long median(long[] arr){
        /** return the median of a sorted copy */
        long[] sorted = arr.clone();
        Arrays.sort(sorted);
        return sorted[sorted.length / 2];
    }

    private void runTaskD(){
        /** run Task 1d: measure complexity with graph (in background thread) */
        currentTask = "D";

        // Show a "running" message immediately
        graphPanel.clearData();
        graphExplanation.setText("Mäter... vänta.");
        cardLayout.show(visualPanel, "GRAPH");

        new Thread(() -> {
            int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};
            int RUNS = 5;

            double[] xData = new double[sizes.length];
            double[] yInsert = new double[sizes.length];
            double[] yBuild = new double[sizes.length];

            StringBuilder sb = new StringBuilder();
            sb.append("Task 1d – Complexity Measurement (median of ").append(RUNS).append(" runs)\n\n");
            sb.append(String.format("%-10s %22s %22s\n", "N", "Insert one-by-one (ns)", "BuildHeap (ns)"));
            sb.append("─".repeat(56)).append("\n");

            for(int si = 0; si < sizes.length; si++){
                int n = sizes[si];
                Random rand = new Random(42);
                int[] input = new int[n];
                for(int i = 0; i < n; i++){
                    input[i] = rand.nextInt(n * 10);
                }

                // Warm up JIT
                for(int w = 0; w < 3; w++){
                    BinaryHeap warmup = new BinaryHeap(n + 10);
                    for(int v : input) warmup.insert(v);
                    warmup = new BinaryHeap(n + 10);
                    warmup.buildHeap(input);
                }

                long[] timesInsert = new long[RUNS];
                long[] timesBuild = new long[RUNS];

                for(int r = 0; r < RUNS; r++){
                    // Algorithm 1: Insert one-by-one
                    long start1 = System.nanoTime();
                    BinaryHeap heap1 = new BinaryHeap(n + 10);
                    for(int v : input) heap1.insert(v);
                    timesInsert[r] = System.nanoTime() - start1;

                    // Algorithm 2: BuildHeap
                    long start2 = System.nanoTime();
                    BinaryHeap heap2 = new BinaryHeap(n + 10);
                    heap2.buildHeap(input);
                    timesBuild[r] = System.nanoTime() - start2;
                }

                long time1 = median(timesInsert);
                long time2 = median(timesBuild);

                xData[si] = n;
                yInsert[si] = time1;
                yBuild[si] = time2;

                sb.append(String.format("%-10d %19d ns %19d ns\n", n, time1, time2));
            }

            sb.append("\nInsert one-by-one: O(n log n) worst case\n");
            sb.append("BuildHeap:         O(n) using bottom-up construction\n");

            String tableText = sb.toString();

            String explanation =
                "VAD GRAFEN VISAR\n" +
                "════════════════\n" +
                "Grafen jämför tidsåtgången (i nanosekunder) för två olika sätt att bygga en min-heap " +
                "med ökande antal element (N = 100 till 100 000).\n\n" +
                "• Röd linje – Insert ett-i-taget: Varje element sätts in med insert(), som placerar " +
                "elementet sist i arrayen och sedan 'percolate up' tills heap-egenskapen gäller. " +
                "Varje insert tar O(log n) i värsta fall, och för n element blir totalen O(n log n).\n\n" +
                "• Blå linje – BuildHeap (bottom-up): Alla element kopieras in i arrayen på en gång, " +
                "sedan anropas percolateDown från mitten (⌊n/2⌋) ner till rot. Denna metod är O(n) " +
                "tack vare att de flesta noder befinner sig nära botten och behöver röra sig kort.\n\n" +
                "HUR MÄTNINGEN GÖRS\n" +
                "═══════════════════\n" +
                "1. För varje storlek N genereras samma slumpmässiga data (seed = 42).\n" +
                "2. JIT-uppvärmning: 3 omgångar körs innan mätningen börjar, så att JVM:s " +
                "Just-In-Time-kompilator har optimerat koden.\n" +
                "3. Varje mätpunkt körs " + RUNS + " gånger. Medianen (mittvärdet) väljs för att " +
                "filtrera bort enstaka avvikelser orsakade av garbage collection eller OS-schemaläggning.\n" +
                "4. Tid mäts med System.nanoTime() som ger nanosekundsprecision.\n\n" +
                "MÄTDATA\n" +
                "═══════\n" +
                tableText;

            // Update UI on EDT
            SwingUtilities.invokeLater(() -> {
                graphPanel.clearData();
                graphPanel.addSeries("Insert one-by-one  O(n log n)", xData, yInsert);
                graphPanel.addSeries("BuildHeap  O(n)", xData, yBuild);
                graphExplanation.setText(explanation);
                graphExplanation.setCaretPosition(0);
                cardLayout.show(visualPanel, "GRAPH");
            });
        }, "TaskD-benchmark").start();
    }

    private void runTaskE(){
        /** run Task 1e: compare deleteMin vs insert costs with graph (in background thread) */
        currentTask = "E";

        // Show a "running" message immediately
        graphPanel.clearData();
        graphExplanation.setText("Mäter... vänta.");
        cardLayout.show(visualPanel, "GRAPH");

        new Thread(() -> {
            int[] sizes = {100, 500, 1000, 5000, 10000};
            int opsPerSize = 1000;
            int RUNS = 5;

            double[] xData = new double[sizes.length];
            double[] yInsertAvg = new double[sizes.length];
            double[] yDeleteAvg = new double[sizes.length];

            StringBuilder sb = new StringBuilder();
            sb.append("Task 1e – Priority Queue: deleteMin vs insert (median of ").append(RUNS).append(" runs)\n\n");
            sb.append(String.format("%-8s %20s %20s\n", "N", "Avg Insert (ns)", "Avg DeleteMin (ns)"));
            sb.append("─".repeat(50)).append("\n");

            for(int si = 0; si < sizes.length; si++){
                int n = sizes[si];
                Random rand0 = new Random(42);
                int[] input = new int[n];
                for(int i = 0; i < n; i++){
                    input[i] = rand0.nextInt(n * 10);
                }

                // Warm up JIT
                for(int w = 0; w < 3; w++){
                    BinaryHeap warmup = new BinaryHeap(n + opsPerSize + 10);
                    warmup.buildHeap(input);
                    Random rw = new Random(42);
                    for(int i = 0; i < opsPerSize; i++) warmup.insert(rw.nextInt(n * 10));
                    warmup = new BinaryHeap(n + 10);
                    warmup.buildHeap(input);
                    for(int i = 0; i < Math.min(opsPerSize, n - 1); i++) warmup.deleteMin();
                }

                long[] timesInsert = new long[RUNS];
                long[] timesDelete = new long[RUNS];
                int delOps = Math.min(opsPerSize, n - 1);

                for(int r = 0; r < RUNS; r++){
                    // Insert test
                    BinaryHeap heapIns = new BinaryHeap(n + opsPerSize + 10);
                    heapIns.buildHeap(input);
                    Random rand = new Random(42);

                    long start = System.nanoTime();
                    for(int i = 0; i < opsPerSize; i++){
                        heapIns.insert(rand.nextInt(n * 10));
                    }
                    timesInsert[r] = System.nanoTime() - start;

                    // DeleteMin test
                    BinaryHeap heapDel = new BinaryHeap(n + 10);
                    heapDel.buildHeap(input);

                    start = System.nanoTime();
                    for(int i = 0; i < delOps; i++){
                        heapDel.deleteMin();
                    }
                    timesDelete[r] = System.nanoTime() - start;
                }

                long insertTime = median(timesInsert);
                long deleteTime = median(timesDelete);

                xData[si] = n;
                yInsertAvg[si] = (double) insertTime / opsPerSize;
                yDeleteAvg[si] = (double) deleteTime / delOps;

                sb.append(String.format("%-8d %17.0f ns %17.0f ns\n",
                        n, yInsertAvg[si], yDeleteAvg[si]));
            }

            String tableText = sb.toString();

            String explanation =
                "VAD GRAFEN VISAR\n" +
                "════════════════\n" +
                "Grafen jämför den genomsnittliga tiden (i nanosekunder) per operation " +
                "för insert och deleteMin i en prioritetskö (min-heap), med ökande heapstorlek " +
                "(N = 100 till 10 000).\n\n" +
                "• Röd linje – Genomsnittlig insert-tid: Varje insert placerar elementet sist " +
                "och 'percolate up'. Värsta fallet är O(log n), men i praktiken hamnar de flesta " +
                "nya element nära botten, så genomsnittet närmar sig O(1).\n\n" +
                "• Blå linje – Genomsnittlig deleteMin-tid: DeleteMin tar bort roten (minsta), " +
                "flyttar sista elementet till roten och 'percolate down'. Elementet måste alltid " +
                "jämföras nedåt genom hela höjden, så det är O(log n) varje gång.\n\n" +
                "Slutsats: DeleteMin är konsekvent dyrare än insert eftersom percolate down " +
                "alltid traverserar hela trädets höjd, medan percolate up ofta stannar tidigt.\n\n" +
                "HUR MÄTNINGEN GÖRS\n" +
                "═══════════════════\n" +
                "1. För varje storlek N byggs en min-heap med buildHeap (seed = 42).\n" +
                "2. JIT-uppvärmning: 3 omgångar körs innan mätningen börjar.\n" +
                "3. " + opsPerSize + " operationer utförs per mätning.\n" +
                "4. Varje mätpunkt körs " + RUNS + " gånger. Medianen väljs för stabilitet.\n" +
                "5. Genomsnittstid = total tid / antal operationer.\n" +
                "6. Tid mäts med System.nanoTime() (nanosekundsprecision).\n\n" +
                "MÄTDATA\n" +
                "═══════\n" +
                tableText;

            // Update UI on EDT
            SwingUtilities.invokeLater(() -> {
                graphPanel.clearData();
                graphPanel.addSeries("Avg Insert", xData, yInsertAvg);
                graphPanel.addSeries("Avg DeleteMin", xData, yDeleteAvg);
                graphExplanation.setText(explanation);
                graphExplanation.setCaretPosition(0);
                cardLayout.show(visualPanel, "GRAPH");
            });
        }, "TaskE-benchmark").start();
    }

    private void runAll(){
        /** show all text output */
        currentTask = "ALL";
        output.setText(
                TaskA.run() + "\n\n" +
                TaskB.run() + "\n\n" +
                TaskC.run() + "\n\n" +
                TaskD.run() + "\n\n" +
                TaskE.run());
        cardLayout.show(visualPanel, "TEXT");
    }
}
