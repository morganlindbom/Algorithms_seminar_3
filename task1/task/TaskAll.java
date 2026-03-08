
// TaskAll.java
package task1.task;

public class TaskAll {

    public static String run(){
        /** run all tasks

        Executes all tasks and returns a compact combined output.
        */

        return TaskA.run() + "\n\n" +
               TaskB.run() + "\n\n" +
               TaskC.run() + "\n\n" +
               TaskD.run() + "\n\n" +
               TaskE.run();
    }
}
