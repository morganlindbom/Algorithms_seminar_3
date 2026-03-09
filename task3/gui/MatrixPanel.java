// MatrixPanel.java

package task3.gui;

import java.awt.*;
import javax.swing.*;
import task3.solver.*;

public class MatrixPanel extends JPanel {

    private char[][] grid;

    private SolverStep currentStep;

    private Color highlightColor = new Color(255,255,0,120);

    public MatrixPanel(){
        /** matrix constructor

        Initializes the puzzle grid with the default matrix
        defined in the assignment.
        */

        grid = new char[][]{

                {'t','h','i','s'},
                {'w','a','t','s'},
                {'o','a','h','g'},
                {'f','g','d','t'}

        };
    }

    public char[][] getGrid(){
        /** return puzzle grid

        Returns the current grid used by the solver.
        */

        return grid;
    }

    public void setHighlightColor(Color color){
        /** set highlight color

        Changes the color used to highlight cells.
        */
        highlightColor = color;
    }

    public void highlight(SolverStep step){
        /** highlight step

        Updates the currently highlighted path.
        */

        currentStep = step;
        repaint();
    }

    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        int rows = grid.length;
        int cols = grid[0].length;
        int cellW = getWidth() / cols;
        int cellH = getHeight() / rows;
        int cell = Math.max(1, Math.min(cellW, cellH));

        Font font = new Font("SansSerif", Font.BOLD, cell / 2);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        for(int r=0;r<rows;r++){

            for(int c=0;c<cols;c++){

                int x = c*cell;
                int y = r*cell;

                g.setColor(Color.WHITE);
                g.fillRect(x,y,cell,cell);

                g.setColor(Color.BLACK);
                g.drawRect(x,y,cell,cell);

                String ch = String.valueOf(grid[r][c]).toUpperCase();
                int tx = x + (cell - fm.stringWidth(ch)) / 2;
                int ty = y + (cell + fm.getAscent() - fm.getDescent()) / 2;
                g.drawString(ch, tx, ty);
            }
        }

        if(currentStep!=null){

            g.setColor(highlightColor);

            int dr=currentStep.direction.dr;
            int dc=currentStep.direction.dc;

            for(int i=0;i<currentStep.word.length();i++){

                int r=currentStep.row+dr*i;
                int c=currentStep.col+dc*i;

                g.fillRect(c*cell,r*cell,cell,cell);
            }
        }
    }
}