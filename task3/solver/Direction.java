// Direction.java

package task3.solver;

public enum Direction {

    RIGHT(0,1),
    LEFT(0,-1),
    DOWN(1,0),
    UP(-1,0),
    DOWN_RIGHT(1,1),
    DOWN_LEFT(1,-1),
    UP_RIGHT(-1,1),
    UP_LEFT(-1,-1);

    public int dr;
    public int dc;

    Direction(int dr,int dc){
        /** direction constructor

        Stores row and column increments describing how the
        solver moves through the puzzle grid.
        */

        this.dr = dr;
        this.dc = dc;
    }
}