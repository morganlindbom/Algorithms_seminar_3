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

    private JLabel directionLabel;

    private Timer timer;

    private WordPuzzleSolver solver;

    private Dictionary dictionary;

    private DefaultListModel<String> dictModel;

    private int stepCount;

    public PuzzleGUI(){
        /** gui constructor

        Builds the graphical interface for the puzzle solver
        and connects solver logic with visual components.
        */

        setTitle("Word Puzzle Solver");

        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(1000,(int)(screenHeight * 0.9));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(8, 8));

        matrixPanel = new MatrixPanel();

        logArea = new JTextPane();

        logArea.setEditable(false);

        foundModel = new DefaultListModel<>();

        JList<String> foundList = new JList<>(foundModel);

        dictModel = new DefaultListModel<>();

        Dictionary tempDict = new Dictionary();

        java.util.List<String> dictWords = tempDict.getWords();

        for(String w : dictWords){
            dictModel.addElement(w);
        }

        JList<String> dictList = new JList<>(dictModel);

        directionLabel = new JLabel("Direction:");

        JButton startButton = new JButton("Start Solver");

        startButton.addActionListener(e -> startSolver());

        JButton clearButton = new JButton("Clear");

        clearButton.addActionListener(e -> {
            foundModel.clear();
            logArea.setText("");
            matrixPanel.highlight(null);
            directionLabel.setText("Direction:");
            stepCount = 0;
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

        JScrollPane dictScroll = new JScrollPane(dictList);
        dictScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),"Dictionary (editable)",
                TitledBorder.LEFT,TitledBorder.TOP));
        dictScroll.setPreferredSize(new Dimension(120, 200));

        // Left panel: matrix on top, buttons fill remaining space below
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints lgbc = new GridBagConstraints();
        lgbc.gridx = 0;
        lgbc.gridy = 0;
        lgbc.weightx = 1.0;
        lgbc.weighty = 0;
        lgbc.fill = GridBagConstraints.NONE;
        lgbc.anchor = GridBagConstraints.NORTH;
        leftPanel.add(matrixPanel, lgbc);

        lgbc.gridy = 1;
        lgbc.weighty = 1.0;
        lgbc.fill = GridBagConstraints.BOTH;
        lgbc.insets = new Insets(8, 10, 0, 10);
        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        startButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnPanel.add(startButton);
        btnPanel.add(clearButton);
        leftPanel.add(btnPanel, lgbc);

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
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        centerPanel.add(leftPanel,gbc);

        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 1;
        gbc.weightx = 0.50;
        centerPanel.add(logScroll,gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.25;
        centerPanel.add(foundScroll,gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.25;
        centerPanel.add(dictPanel,gbc);

        add(directionLabel,BorderLayout.NORTH);

        add(centerPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    private void startSolver(){
        /** solver starter

        Initializes solver using current dictionary list
        and begins timer-driven execution.
        */

        java.util.List<String> wordList = new java.util.ArrayList<>();
        for(int i = 0; i < dictModel.size(); i++){
            wordList.add(dictModel.getElementAt(i));
        }
        dictionary = new Dictionary(wordList);

        stepCount = 0;

        solver = new WordPuzzleSolver(matrixPanel.getGrid(),dictionary);

        timer = new Timer(250,e -> runStep());

        timer.start();
    }

    private void runStep(){
        /** solver execution step

        Executes one solver step and updates GUI components.
        */

        SolverStep step = solver.nextStep();

        if(step==null){

            timer.stop();

            appendStyled(stepCount + ". Search complete\n", null, null);

            return;
        }

        stepCount++;

        if(step.wordFound){
            matrixPanel.setHighlightColor(new Color(100,220,100,140));
        }else{
            matrixPanel.setHighlightColor(new Color(255,255,0,120));
        }

        matrixPanel.highlight(step);

        directionLabel.setText(
                "Direction: "+step.direction
        );

        if(step.wordFound){

            int endRow = step.row + step.direction.dr * (step.word.length() - 1);
            int endCol = step.col + step.direction.dc * (step.word.length() - 1);
            String coords = "(" + step.row + "," + step.col + ")\u2192(" + endRow + "," + endCol + ")";
            String line = stepCount + ". \u2713 FOUND: " + step.word + "\t" + coords + "\n";

            appendStyled(line, new Color(144, 238, 144), new Color(0, 50, 0));

            foundModel.addElement(step.word);

            timer.stop();
            timer.setDelay(2000);
            timer.setInitialDelay(2000);
            timer.start();

        }else if(!step.prefixValid){

            appendStyled(stepCount + ". ✗ "+step.word+"\n", null, null);

            timer.stop();
            timer.setDelay(250);
            timer.setInitialDelay(250);
            timer.start();

        }else{

            appendStyled(stepCount + ". Checking: "+step.word+"\n", null, null);

            timer.stop();
            timer.setDelay(250);
            timer.setInitialDelay(250);
            timer.start();
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
            e.printStackTrace();
        }
        logArea.setCaretPosition(doc.getLength());
    }
}