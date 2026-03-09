// ProcessPanel.java

package task2.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ProcessPanel extends JPanel {

    private JTextArea processArea;

    public ProcessPanel() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Algorithm Process"));
        setPreferredSize(new Dimension(350, 0));

        processArea = new JTextArea();
        processArea.setEditable(false);
        processArea.setLineWrap(true);
        processArea.setWrapStyleWord(true);
        processArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        processArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(processArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateProcess(String text) {
        processArea.setText(text);
    }

    public void appendProcess(String text) {
        processArea.append(text);
        processArea.setCaretPosition(processArea.getDocument().getLength());
    }

    public void clear() {
        processArea.setText("");
    }

}
