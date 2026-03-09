// SolverStep.java

package task3.solver;

public class SolverStep {

    public String word;
    public int row;
    public int col;
    public Direction direction;
    public boolean prefixValid;
    public boolean wordFound;

    public SolverStep(String word,int row,int col,
                      Direction direction,
                      boolean prefixValid,
                      boolean wordFound){
        /** step container

        Represents one algorithm step so that the GUI can
        visualize the solver progress and highlight letters
        currently being examined.
        */

        this.word = word;
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.prefixValid = prefixValid;
        this.wordFound = wordFound;
    }
}