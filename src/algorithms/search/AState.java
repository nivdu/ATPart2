package algorithms.search;

import java.io.Serializable;

/**
 * an abstract class of state which holds a string of the state,
 * a state which the current state came from
 * and the cost of the state.
 */
public abstract class AState implements Serializable {
    private String stringState;
    private AState fatherState;
    private int cost;

    /**
     * getter of state's cost
     * @return the cost of the state
     */
    public int getCost() {
        return cost;
    }

    /**
     * setter of state's cost
     * @param cost - the cost that will set to the state's cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * getter of state's string
     * @return the string of the state
     */
    public String getStringState() {
        return stringState;
    }

    /**
     * an abstract function of toString
     * @return the string of the state
     */
    public abstract String toString();

    /**
     * setter of state's string
     * @param state - the new string that will set to the state's string
     */
    public void setStringState(String state) {
        this.stringState = state;
    }

    /**
     * getter of the state that the current state came from
     * @return the state of the previous state
     */
    public AState getFatherState() {
        return fatherState;
    }

    /**
     * setter of the state that the current state came from
     * @param fatherState - the state that the current state came from
     */
    public void setFatherState(AState fatherState) {
        this.fatherState = fatherState;
    }

}