package pokerhandreplayer.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class HandCreator {
    private ArrayList<String> playerNames;
    private ArrayList<GameState> states;

    public HandCreator() {
        this.playerNames = new ArrayList<>();
        this.states = new ArrayList<>();
    }   

    public ArrayList<GameState> getStates() {
        return states;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }
    
    /**
     * Takes an arraylist of strings from a hand history -text file and converts it to an arraylist of game states.
     * @param lines
     * @return list of gamestates representing the hand history given as parameter
     */
    public Replay createHand(File handHistory) {
        ArrayList<String> lines = StringHelper.getLinesFromFile(handHistory);
        buildInitialState(lines);
        buildActionLog(lines);
        return new Replay(states);
    }
    
    public Replay createHand(ArrayList<String> lines) {
        buildInitialState(lines);
        buildActionLog(lines);
        return new Replay(states);
    }
    
    /**
     * Extracts the players and their stack sizes from the hand history and creates the initial game state.
     * @param lines 
     */
    public void buildInitialState(ArrayList<String> lines) {
        ArrayList<Player> players = new ArrayList<>();
        
        for (String line : lines) {
            // get player names
            if (line.startsWith("Seat ")) {
                String name = StringHelper.getNameFromLine(line);
                
                // if the name hasn't been added yet, get the stack size and add to players -list
                if (!playerNames.contains(name)) {
                    playerNames.add(name);
                    
                    // get player stacksize
                    int stackSize = StringHelper.getStackSizeFromLine(line);
                    players.add(new Player(name, stackSize));
                }
            }
        }
        
        // add initial state to state -list
        states.add(new GameState(players, 0));
    }
    
    /**
     * Converts every line of the hand history into a game state and stacks them in the states -object.
     * @param lines 
     */
    public void buildActionLog(ArrayList<String> lines) {
        // go through all the lines
        for (String line : lines) {
            // check if line starts with a player name
            for (String player : playerNames) {
                if (line.startsWith(player)) {
                    // if true, add a game state with the action described in the line
                    boolean handComplete = addPlayerAction(line, player);
                    
                    if (handComplete) {
                        // handle hand end
                        return;
                    }
                }
            }
            
            // check if line starts with dealing additional cards
            if (line.startsWith("Dealt ")) { 
                setNewCards(line);
                
            }
            
            // check for new street
            if (line.startsWith("***")) {
                // set new pot and labels
                setNewPot();
            }
        }
    }
    
    /**
     * Converts text from the line given as parameter into card objects, adds the cards to the corret player and creates a new game state with updated cards.
     * @param line 
     */
    public void setNewCards(String line) {
        ArrayList<Player> previous = states.get(states.size() - 1).getPlayers();
        String name = StringHelper.getNameFromLine(line);
        ArrayList<Card> newCards = StringHelper.getCardsFromLine(line);
        createState(name, "dealt cards", -1, 0, newCards);
    }
    
    /**
     * Calculates all the bets of the betting round together, substracts them from the corresponding player's stack and adds them in the main pot.
     * Then creates a new game state with all the players' bets set to 0 and the pot updated.
     */
    public void setNewPot() {
        ArrayList<Player> previous = states.get(states.size() - 1).getPlayers();
        int pot = states.get(states.size() - 1).getPot();
        
        ArrayList<Player> newPlayers = new ArrayList<>();
        
        // add all bets to the pot
        for (Player p : previous) {
            pot += p.getBet();
            Player newPlayer = new Player(p.getName(), p.getStackSize());
            newPlayer.setCards(p.getCardsDeepCopy());
            if (p.getLabel().equals("fold") || p.getLabel().equals("folded")) {
                newPlayer.setLabel("folded");
                newPlayer.setHasFolded(true);
            } else {
                newPlayer.setLabel("waiting for turn");
            }
            
            newPlayers.add(newPlayer);
        }
        
        // create new game state with updated pot and labels
        states.add(new GameState(newPlayers, pot));
    }
    
    /**
     * Handles the lines where a player makes an action (bets, calls, checks, folds, posts ante/bring in/blind). Creates a new state updating the player's label, bet and
     * in case of antes of winnig the hand, the pot.
     * @param line
     * @param player
     * @return 
     */
    public boolean addPlayerAction(String line, String player) {
        String[] splittedLine = line.split(" ");
        int i = StringHelper.getActionIndex(player);

        // get the word after the player's name as the action of the line
        String action = splittedLine[i];
        int bet = 0;
        int potIncrement = 0;

        // handle folds, calls and 'wins'
        if (action.equals("folds") || action.equals("checks")) {
            // tähän vois lisätä sellasen joka tarkistaa edellisen staten labelin ja jos se on bring in tai blindi, asettaa betsiks edellisen bet
            
            // remove the "s" from action string
            action = action.substring(0, action.length() - 1); // ehkä jos "wins" niin bet vois olla potin koko
        }

        // handle bets and raises and calls
        if (action.equals("bets") || action.equals("raises") || action.equals("calls")) {
            // remove the "s" from action string
            action = action.substring(0, action.length() - 1);
            bet = StringHelper.getAmountInCentsFromString(splittedLine[i + 1]);
        }

        // handle antes, bring ins and blinds
        if (splittedLine.length > i + 1) {
            if (splittedLine[i + 1].equals("ante")) {
                action = "post ante"; // vai pelkkä ante?
                bet = StringHelper.getAmountInCentsFromString(splittedLine[i + 2]);
                potIncrement = bet;
            } else if (splittedLine[i + 1].equals("in")) {
                action = "bring in";
                bet = StringHelper.getAmountInCentsFromString(splittedLine[i + 3]);
                potIncrement = bet;
            }
//            } else  if (splittedLine[i + 1].equals("small")) {
//                action = "small blind";
//                bet = StringHelper.getAmountInCentsFromString(splittedLine[i + 2]);
//                potIncrement = bet;
//            } else  if (splittedLine[i + 1].equals("big")) {
//                action = "big blind";
//                bet = StringHelper.getAmountInCentsFromString(splittedLine[i + 2]);
//                potIncrement = bet;
//            }
        }
        
        // if hand has a winner, handle win and return true
        if (action.equals("wins")) { // pitää tarkistaa splittipotit !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            createSummary(player);
            return true;
        }
        
        // add a new state to the action log with the current state of the game and return false
        createState(player, action, bet, potIncrement, null);
        return false;
                    // pitää lisätä myös maholliset jaetut kortit
    }
    
    // ei ota tällä hetkellä rakea huomioon eikä jakopotteja, mahollisesti pitää tehä playerille win -flägi
    /**
     * Creates a summary state that shows the winner of the hand and the net profits or losses of the players.
     * @param name 
     */
    public void createSummary(String name) {
        // get players last bet to return it to the player
        int lastBet = states.get(states.size() - 1).getPlayers().stream().filter(p -> p.getName().equals(name)).findFirst().get().getBet();
        int pot = states.get(states.size() - 1).getPot();
        
        // add winscreen
        createState(name, "wins " + pot / 100, -1 * lastBet, 0, null);
        
        // form summary screen
        ArrayList<Player> players = states.get(states.size() - 1).getPlayers();
        ArrayList<Player> summaryPlayers = new ArrayList<>();
        
        pot = states.get(states.size() - 1).getPot();
        for (Player p : players) {
            Player newPlayer;
            // add the pot in the winners stack
            if (p.getName().equals(name)) { // ehkä if winner == true ?
                newPlayer = new Player(name, p.getStackSize() + pot);
                newPlayer.setLabel("WON " + calculateProfit(name, pot) / 100);
            } else {
                newPlayer = new Player(p.getName(), p.getStackSize());
                newPlayer.setLabel(("LOST " + calculateProfit(p.getName(), 0) / 100));
                
            }
            summaryPlayers.add(newPlayer);
        }
        
        states.add(new GameState(summaryPlayers, pot));
    }
    
    /**
     * Calculates the profit of a player by comparing the stacksizes of the first and last game states. Can be given an addition (pot size) in case of the winner of the hand. 
     * @param name
     * @param addition
     * @return 
     */
    public int calculateProfit(String name, int addition) {
        ArrayList<Player> initialPlayers = states.get(0).getPlayers();
        ArrayList<Player> endPlayers = states.get(states.size() - 1).getPlayers();
        int initialStack = initialPlayers.stream().filter(p -> p.getName().equals(name)).findFirst().get().getStackSize();
        //  fix end stack in case of hand winner by adding the pot
        int endStack = endPlayers.stream().filter(p -> p.getName().equals(name)).findFirst().get().getStackSize() + addition;
        return endStack - initialStack;
    }
    
    /**
     * Creates a new state by updating the player given as parameter and copying the remaining players from the previous state.
     * @param name
     * @param label
     * @param bet
     * @param potIncrement
     * @param cardsToAdd 
     */
    public void createState(String name, String label, int bet, int potIncrement, ArrayList<Card> cardsToAdd) { // bet needs to be in cents
        // get player list and from previous state
        ArrayList<Player> previousPlayers = states.get(states.size() - 1).getPlayers();
        
        // add increment to pot in case of antes, blinds or bring ins
        int pot = states.get(states.size() - 1).getPot() + potIncrement;
        
        ArrayList<Player> newPlayers = new ArrayList<>();
        boolean cardsDealt = cardsToAdd != null && cardsToAdd.size() != 0;
        
        // change the player whose name is given as a parameter and copy the rest to newPlayers
        for (Player p : previousPlayers) {
            if (p.getName().equals(name)) {
                int newStackSize = p.getStackSize() - bet; // bet needs to be cents
                // get a deep copy of players cards
                ArrayList<Card> newCards = p.getCardsDeepCopy();
                // add new cards if given as parameter
                if (cardsDealt) {
                    for (Card c : cardsToAdd) {
                        newCards.add(c);
                    }
                }
                
                // create new player with new data (bet can only be negative in case of hand completion or dealing cards)
                Player newPlayer = new Player(name, newStackSize, newCards, bet > 0 ? bet : 0, label);
                // set player's hasTurn to true
                newPlayer.setHasTurn(true);
                
                if (label.equals("folded") || label.equals("fold")) {
                    newPlayer.setHasFolded(true);
                }
                
                newPlayers.add(newPlayer);
            } else {
                // check if cards are being dealt, and if so set bet to 0
                int correctPot = cardsDealt ? 0 : p.getBet();
                Player newPlayer = new Player(p.getName(), p.getStackSize(), p.getCards(), correctPot, p.getLabel());
                if (p.getLabel().equals("folded") || p.getLabel().equals("fold")) {
                    newPlayer.setHasFolded(true);
                }
                // set hasTurn to false
                newPlayer.setHasTurn(false);
                newPlayers.add(newPlayer);
            }
        }
        
        // add a new state with updated player list
        GameState newState = new GameState(newPlayers, pot);
        states.add(newState);
    }
}
