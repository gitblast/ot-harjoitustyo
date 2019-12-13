package pokerhandreplayer.domain;

import java.util.ArrayList;

public class Replay {
    private ArrayList<GameState> states;
    private int counter;
    private ArrayList<String> raw;

    public Replay(ArrayList<GameState> states, ArrayList<String> lines) {
        if (states.size() == 0) {
            throw new IllegalArgumentException("Error: Empty replay");
        }
        this.states = states;
        this.counter = 0;
        this.raw = lines;
    }
    
    public String getAsStringWithComments() {
        StringBuilder history = new StringBuilder(String.join("\n", raw));
        history.append("\n#####\n");
        for (int i = 0; i < states.size(); i++) {
            history.append(String.valueOf(i) + ":::" + states.get(i).getComment() + "\n");
        }
        history.append("#####");
        return history.toString();
    }
    
    public GameState getCurrent() {
        return states.get(counter);
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
