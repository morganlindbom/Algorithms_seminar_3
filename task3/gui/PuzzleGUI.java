// PuzzleGUI.java

package task3.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import task3.solver.*;

public class PuzzleGUI extends JFrame {

    private MatrixPanel matrixPanel;

    private JTextPane logArea;

    private DefaultListModel<String> foundModel;

    private DefaultListModel<String> prefixModel;

    private JLabel directionLabel;

    private Timer timer;

    private WordPuzzleSolver solver;

    private Dictionary dictionary;

    private DefaultListModel<String> dictModel;

    private int stepCount;

    private JButton startButton;

    private boolean running;

    private JTextField stepDelayField;

    private JTextField foundDelayField;

    private ComplexityGraphPanel graphPanel;

    private CardLayout cardLayout;

    private JPanel cardPanel;

    private int bruteForceStepCount;

    private int totalBruteForce;

    private JLabel statsIterations;

    private JLabel statsChecks;

    private JLabel statsWordsFound;

    private JLabel statsPrefixFails;

    private int prefixFailCount;

    public PuzzleGUI(){
        /** gui constructor

        Builds the graphical interface for the puzzle solver
        and connects solver logic with visual components.
        */

        setTitle("Word Puzzle Solver");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(screenSize.width * 0.9),(int)(screenSize.height * 0.9));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(8, 8));

        matrixPanel = new MatrixPanel();

        graphPanel = new ComplexityGraphPanel();

        logArea = new JTextPane();

        logArea.setEditable(false);

        foundModel = new DefaultListModel<>();

        JList<String> foundList = new JList<>(foundModel);

        prefixModel = new DefaultListModel<>();

        JList<String> prefixList = new JList<>(prefixModel);

        dictModel = new DefaultListModel<>();

        Dictionary tempDict = new Dictionary();

        java.util.List<String> dictWords = tempDict.getWords();

        for(String w : dictWords){
            dictModel.addElement(w);
        }

        JList<String> dictList = new JList<>(dictModel);

        directionLabel = new JLabel("Direction:");

        startButton = new JButton("Start Solver");

        running = false;

        startButton.addActionListener(e -> toggleSolver());

        JButton clearButton = new JButton("Clear");

        clearButton.addActionListener(e -> {
            if(timer != null) timer.stop();
            running = false;
            startButton.setText("Start Solver");
            foundModel.clear();
            prefixModel.clear();
            logArea.setText("");
            matrixPanel.highlight(null);
            directionLabel.setText("Direction:");
            stepCount = 0;
            bruteForceStepCount = 0;
            prefixFailCount = 0;
            graphPanel.reset();
            statsIterations.setText("Solver iterations: 0");
            statsChecks.setText("Total checks: 0");
            statsWordsFound.setText("Words found: 0");
            statsPrefixFails.setText("Prefix fails: 0");
        });

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),"Progress",
                TitledBorder.LEFT,TitledBorder.TOP));
        logScroll.setPreferredSize(new Dimension(250, 200));

        JScrollPane foundScroll = new JScrollPane(foundList);
        foundScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),"Found Words",
                TitledBorder.LEFT,TitledBorder.TOP));
        foundScroll.setPreferredSize(new Dimension(120, 200));

        JScrollPane prefixScroll = new JScrollPane(prefixList);
        prefixScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),"Prefixes Checked",
                TitledBorder.LEFT,TitledBorder.TOP));
        prefixScroll.setPreferredSize(new Dimension(120, 200));

        JScrollPane dictScroll = new JScrollPane(dictList);
        dictScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),"Dictionary (editable)",
                TitledBorder.LEFT,TitledBorder.TOP));
        dictScroll.setPreferredSize(new Dimension(120, 200));

        // Left panel: matrix on top, buttons, speed below
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints lgbc = new GridBagConstraints();
        lgbc.gridx = 0;
        lgbc.gridy = 0;
        lgbc.weightx = 1.0;
        lgbc.weighty = 1.0;
        lgbc.fill = GridBagConstraints.BOTH;
        leftPanel.add(matrixPanel, lgbc);

        lgbc.gridy = 1;
        lgbc.weighty = 0;
        lgbc.fill = GridBagConstraints.HORIZONTAL;
        lgbc.insets = new Insets(8, 10, 0, 10);
        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        startButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnPanel.add(startButton);
        btnPanel.add(clearButton);
        leftPanel.add(btnPanel, lgbc);

        // Delay controls
        JPanel delayPanel = new JPanel(new GridBagLayout());
        GridBagConstraints dgbc = new GridBagConstraints();
        dgbc.insets = new Insets(2, 4, 2, 4);
        dgbc.anchor = GridBagConstraints.WEST;

        dgbc.gridx = 0; dgbc.gridy = 0;
        delayPanel.add(new JLabel("Step delay (ms):"), dgbc);
        dgbc.gridx = 1;
        stepDelayField = new JTextField("250", 5);
        delayPanel.add(stepDelayField, dgbc);

        dgbc.gridx = 0; dgbc.gridy = 1;
        delayPanel.add(new JLabel("Found delay (ms):"), dgbc);
        dgbc.gridx = 1;
        foundDelayField = new JTextField("2000", 5);
        delayPanel.add(foundDelayField, dgbc);

        delayPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Speed",
                TitledBorder.LEFT, TitledBorder.TOP));

        lgbc.gridy = 2;
        lgbc.weighty = 0;
        lgbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(delayPanel, lgbc);

        // Statistics panel
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 0, 2));
        statsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Statistics",
                TitledBorder.LEFT, TitledBorder.TOP));
        statsIterations = new JLabel("Solver iterations: 0");
        statsChecks = new JLabel("Total checks: 0");
        statsWordsFound = new JLabel("Words found: 0");
        statsPrefixFails = new JLabel("Prefix fails: 0");
        Font statsFont = new Font("SansSerif", Font.PLAIN, 11);
        statsIterations.setFont(statsFont);
        statsChecks.setFont(statsFont);
        statsWordsFound.setFont(statsFont);
        statsPrefixFails.setFont(statsFont);
        statsPanel.add(statsIterations);
        statsPanel.add(statsChecks);
        statsPanel.add(statsWordsFound);
        statsPanel.add(statsPrefixFails);

        lgbc.gridy = 3;
        leftPanel.add(statsPanel, lgbc);

        // Dictionary editing buttons
        JPanel dictEditPanel = new JPanel(new GridLayout(1, 2, 4, 0));
        JButton addWordBtn = new JButton("Add");
        JButton removeWordBtn = new JButton("Remove");
        addWordBtn.addActionListener(e -> {
            String word = JOptionPane.showInputDialog(this,
                    "Enter word to add:", "Add Word",
                    JOptionPane.PLAIN_MESSAGE);
            if(word != null && !word.trim().isEmpty()){
                dictModel.addElement(word.trim().toLowerCase());
            }
        });
        removeWordBtn.addActionListener(e -> {
            int idx = dictList.getSelectedIndex();
            if(idx >= 0){
                dictModel.remove(idx);
            }
        });
        dictEditPanel.add(addWordBtn);
        dictEditPanel.add(removeWordBtn);

        JPanel dictPanel = new JPanel(new BorderLayout(0, 4));
        dictPanel.add(dictScroll, BorderLayout.CENTER);
        dictPanel.add(dictEditPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(leftPanel,gbc);

        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 1;
        gbc.weightx = 0.50;
        centerPanel.add(logScroll,gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.20;
        centerPanel.add(foundScroll,gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.20;
        centerPanel.add(prefixScroll,gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.20;
        centerPanel.add(dictPanel,gbc);

        // CardLayout: data view vs graph view
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(centerPanel, "data");
        cardPanel.add(graphPanel, "graph");

        // Tab buttons
        JButton dataTabBtn = new JButton("Data");
        JButton graphTabBtn = new JButton("Graph");
        dataTabBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
        graphTabBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
        dataTabBtn.addActionListener(e -> cardLayout.show(cardPanel, "data"));
        graphTabBtn.addActionListener(e -> cardLayout.show(cardPanel, "graph"));
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel tabPanel = new JPanel(new GridLayout(1, 2, 4, 0));
        tabPanel.add(dataTabBtn);
        tabPanel.add(graphTabBtn);
        topPanel.add(directionLabel, BorderLayout.CENTER);
        topPanel.add(tabPanel, BorderLayout.EAST);

        add(topPanel,BorderLayout.NORTH);

        add(cardPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    private void toggleSolver(){
        /** solver toggle

        Toggles between start, pause and resume.
        */

        if(running){
            timer.stop();
            running = false;
            startButton.setText("Resume");
            return;
        }

        if(solver == null){
            java.util.List<String> wordList = new java.util.ArrayList<>();
            for(int i = 0; i < dictModel.size(); i++){
                wordList.add(dictModel.getElementAt(i));
            }
            dictionary = new Dictionary(wordList);

            stepCount = 0;
            prefixModel.clear();
            prefixFailCount = 0;

            // Calculate total brute force steps (no pruning)
            char[][] grid = matrixPanel.getGrid();
            int rows = grid.length;
            int cols = grid[0].length;
            int dirs = task3.solver.Direction.values().length;
            int maxLen = Math.max(rows, cols);
            totalBruteForce = 0;
            for(int r = 0; r < rows; r++){
                for(int c = 0; c < cols; c++){
                    totalBruteForce++; // length 1
                    for(int d = 0; d < dirs; d++){
                        task3.solver.Direction dir = task3.solver.Direction.values()[d];
                        for(int len = 2; len <= maxLen; len++){
                            int er = r + dir.dr * (len - 1);
                            int ec = c + dir.dc * (len - 1);
                            if(er >= 0 && er < rows && ec >= 0 && ec < cols){
                                totalBruteForce++;
                            }
                        }
                    }
                }
            }
            bruteForceStepCount = 0;
            graphPanel.reset();
            graphPanel.setTotalBruteForce(totalBruteForce);

            solver = new WordPuzzleSolver(matrixPanel.getGrid(),dictionary);

            int stepDelay = getStepDelay();
            timer = new Timer(stepDelay,e -> runStep());
        }

        running = true;
        startButton.setText("Pause");
        timer.start();
    }

    private void runStep(){
        /** solver execution step

        Executes one solver step and updates GUI components.
        */

        SolverStep step = solver.nextStep();

        if(step==null){

            timer.stop();
            running = false;
            startButton.setText("Start Solver");
            solver = null;

            appendStyled(stepCount + ". Search complete\n", null, null);

            return;
        }

        stepCount++;

        // Track brute force equivalent: without pruning each prefix-invalid
        // step would have continued to max length in that direction
        if(step.prefixValid){
            bruteForceStepCount++;
        } else {
            // Without pruning, all remaining lengths in this direction would also be checked
            char[][] grid = matrixPanel.getGrid();
            int maxLen = Math.max(grid.length, grid[0].length);
            int remaining = maxLen - step.word.length();
            bruteForceStepCount += 1 + Math.max(0, remaining);
        }

        graphPanel.addStep((int) solver.getCheckCounter(), bruteForceStepCount);

        if(!step.prefixValid) prefixFailCount++;

        statsIterations.setText("Solver iterations: " + stepCount);
        statsChecks.setText("Total checks: " + solver.getCheckCounter());
        statsWordsFound.setText("Words found: " + foundModel.size());
        statsPrefixFails.setText("Prefix fails: " + prefixFailCount);

        if(step.wordFound){
            matrixPanel.setHighlightColor(new Color(100,220,100,140));
        }else{
            matrixPanel.setHighlightColor(new Color(255,255,0,120));
        }

        matrixPanel.highlight(step);

        directionLabel.setText(
                "Direction: "+step.direction
        );

        if(step.prefixValid && !prefixModel.contains(step.word)){
            prefixModel.addElement(step.word);
        }

        if(step.wordFound){

            int endRow = step.row + step.direction.dr * (step.word.length() - 1);
            int endCol = step.col + step.direction.dc * (step.word.length() - 1);
            String coords = "(" + step.row + "," + step.col + ")\u2192(" + endRow + "," + endCol + ")";
            String line = stepCount + ". \u2713 FOUND: " + step.word + "\t" + coords + "\n";

            appendStyled(line, new Color(144, 238, 144), new Color(0, 50, 0));

            if(!foundModel.contains(step.word)){
                foundModel.addElement(step.word);
            }

            int foundDelay = getFoundDelay();
            timer.setDelay(foundDelay);
            timer.setInitialDelay(foundDelay);
            timer.start();

        }else if(!step.prefixValid){

            appendStyled(stepCount + ". \u2717 prefix fail: "+step.word+"\n",
                    new Color(255, 230, 220), new Color(180, 50, 30));

            int stepDelay = getStepDelay();
            timer.stop();
            timer.setDelay(stepDelay);
            timer.setInitialDelay(stepDelay);
            timer.start();

        }else{

            appendStyled(stepCount + ". Checking: "+step.word+"\n", null, null);

            int stepDelay = getStepDelay();
            timer.stop();
            timer.setDelay(stepDelay);
            timer.setInitialDelay(stepDelay);
            timer.start();
        }
    }

    private int getStepDelay(){
        try {
            return Integer.parseInt(stepDelayField.getText().trim());
        } catch(NumberFormatException e){
            return 250;
        }
    }

    private int getFoundDelay(){
        try {
            return Integer.parseInt(foundDelayField.getText().trim());
        } catch(NumberFormatException e){
            return 2000;
        }
    }

    private void appendStyled(String text, Color bg, Color fg){
        /** append styled text to log

        Adds text to the JTextPane with optional background
        and foreground color for highlighting found words.
        */
        StyledDocument doc = logArea.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        if(bg != null){
            StyleConstants.setBackground(attrs, bg);
        }
        if(fg != null){
            StyleConstants.setForeground(attrs, fg);
        }
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 12);
        try {
            doc.insertString(doc.getLength(), text, attrs);
        } catch(BadLocationException e){
            System.err.println("Error inserting text into log: " + e.getMessage());
        }
        logArea.setCaretPosition(doc.getLength());
    }
}