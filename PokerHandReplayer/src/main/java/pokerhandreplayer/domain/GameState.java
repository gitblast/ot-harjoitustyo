package pokerhandreplayer.domain;

import java.util.ArrayList;

public class GameState {
    private ArrayList<Player> players;
    private int pot; // in cents
    private String comment;

    /**
     * Creates an instance of a state of a poker hand. Needs to be given a list of players and the pot size as parameter
     * @param players
     * @param pot 
     */
    public GameState(ArrayList<Player> players, int pot) {
        this.players = players;
        this.pot = pot;
        this.comment = "";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
    
    

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPot() {
        return pot;
    }
    
    public ArrayList<String> getPlayerNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Player p : players) {
            names.add(p.getName());
        }
        return names;
    }
    
    public String toString() {    
        String s = "";
        for (Player p : players) {
            s += p + " " 
                    + StringHelper.getCardsString(p.getCards()) + " "
                    + p.getLabel() + " " 
                    + (p.getBet() != 0 ? p.getBet() : "") + (p.hasTurn() ? " *<" : "") 
                    + "\n";
        }
        
        s += "\nPOT: " + pot + "\n";
        
        return s;
    }
}
