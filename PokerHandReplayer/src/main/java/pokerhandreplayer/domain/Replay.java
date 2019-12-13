package pokerhandreplayer.domain;

import java.util.ArrayList;

public class Replay {
    private ArrayList<GameState> states;
    private int counter;

    public Replay(ArrayList<GameState> states) {
        if (states.size() == 0) {
            throw new IllegalArgumentException("Error: Empty replay");
        }
        this.states = states;
        this.counter = 0;
    }
    
    public GameState getByIndex(int index) {
        if (index >= states.size() || index < 0) {
            return null;
        }
        
        return states.get(index);
    }
    
    public GameState getNext() {
        if (counter + 1 < states.size()) {
            counter++;
        } else {
            counter = 0;
        }
        
        return states.get(counter);
    }
    
    public GameState getPrev() {
        if (counter - 1 >= 0) {
            counter--;
        } else {
            counter = states.size() - 1;
        }
        
        return states.get(counter);
    }
}
