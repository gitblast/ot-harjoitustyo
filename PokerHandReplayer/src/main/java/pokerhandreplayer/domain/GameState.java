package pokerhandreplayer.domain;

import java.util.ArrayList;

public class GameState {
    private ArrayList<Player> players;
    private int pot;

    public GameState(ArrayList<Player> players, int pot) {
        this.players = players;
        this.pot = pot;
    }
    
    
}
