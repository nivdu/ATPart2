package algorithms.search;

/**
 * a class of Maze State which holds a string of the state which represents the position of the state,
 * a state which the current state came from
 * and the cost of the state.
 */

public class MazeState extends AState {

    /**
     * constructor of Maze's state
     */
    public MazeState(){
        setCost(0);
    }

    /**
     * constructor that insert a given value to the string state
     * @param s - the string that will insert to the string state
     */
    public MazeState(String s){
        setStringState(s);
    }

    /**
     * toString function that return the string of the state
     * @return the string of the state
     */
    @Override
    public String toString() {
        return getStringState();
    }

}