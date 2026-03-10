// WordPuzzleSolver.java

package task3.solver;

public class WordPuzzleSolver {

    private final char[][] grid;
    private final Dictionary dictionary;

    private int row;
    private int col;
    private int directionIndex;
    private int length;

    private final Direction[] directions;

    private long checkCounter;

    public WordPuzzleSolver(char[][] grid,Dictionary dictionary){
        /** solver constructor

        Initializes solver state for step-by-step execution.
        This design allows GUI animation of the algorithm.
        */

        this.grid = grid;
        this.dictionary = dictionary;

        row = 0;
        col = 0;
        directionIndex = 0;
        length = 1;
        checkCounter = 0;

        directions = Direction.values();
    }

    public long getCheckCounter(){
        return checkCounter;
    }

    public SolverStep nextStep(){
        /** next algorithm step

        Performs a single search step. Each call evaluates
        one word candidate so the GUI can animate the process.
        Single letters are checked once per cell position.
        Directions are only used from length 2 and up.
        */

        int rows = grid.length;
        int cols = grid[0].length;

        while(row < rows){

            while(col < cols){

                if(length == 1){

                    String letter = String.valueOf(grid[row][col]).toLowerCase();
                    checkCounter++;

                    if(!dictionary.isPrefix(letter)){

                        SolverStep step =
                                new SolverStep(letter,row,col,directions[0],false,false);

                        col++;
                        directionIndex = 0;

                        return step;
                    }

                    boolean isWord = dictionary.isWord(letter);

                    SolverStep step =
                            new SolverStep(letter,row,col,directions[0],true,isWord);

                    length = 2;

                    return step;
                }

                while(directionIndex < directions.length){

                    Direction dir = directions[directionIndex];

                    String word = buildWord(row,col,dir,length);

                    if(word == null){

                        directionIndex++;
                        length = 2;
                        continue;
                    }
                    checkCounter++;

                    if(!dictionary.isPrefix(word)){

                        SolverStep step =
                                new SolverStep(word,row,col,dir,false,false);

                        directionIndex++;
                        length = 2;

                        return step;
                    }

                    if(dictionary.isWord(word)){

                        SolverStep step =
                                new SolverStep(word,row,col,dir,true,true);

                        length++;

                        return step;
                    }

                    SolverStep step =
                            new SolverStep(word,row,col,dir,true,false);

                    length++;

                    return step;
                }

                directionIndex = 0;
                length = 1;
                col++;
            }

            col = 0;
            row++;
        }

        return null;
    }

    private String buildWord(int r,int c,Direction dir,int len){
        /** build candidate word

        Constructs a string from the puzzle grid using the
        provided direction and length.
        */

        StringBuilder sb = new StringBuilder();

        int rows = grid.length;
        int cols = grid[0].length;

        for(int i=0;i<len;i++){

            int nr = r + dir.dr*i;
            int nc = c + dir.dc*i;

            if(nr<0 || nc<0 || nr>=rows || nc>=cols)
                return null;

            sb.append(grid[nr][nc]);
        }

        return sb.toString().toLowerCase();
    }
}