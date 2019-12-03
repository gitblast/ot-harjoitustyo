package pokerhandreplayer.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class HandCreator {
    private ArrayList<String> playerNames;
    private ArrayList<GameState> states;
    
    // ONGELMA: JOS ON ANTE JA BRING IN, LASKETAAN VAIN BRING IN

    public HandCreator() {
        this.playerNames = new ArrayList<>();
        this.states = new ArrayList<>();
    }   

    public ArrayList<GameState> createHand(ArrayList<String> lines) {
        buildInitialState(lines);
        buildActionLog(lines);
        return states;
    }
    
    public void buildInitialState(ArrayList<String> lines) {
        ArrayList<Player> players = new ArrayList<>();
        
        for (String line : lines) {
            // get player names
            if (line.startsWith("Seat ")) {
                String name = getNameFromLine(line);
                
                // if the name hasn't been added yet, get the stack size and add to players -list
                if (!playerNames.contains(name)) {
                    playerNames.add(name);
                    
                    // get player stacksize
                    int stackSize = getStackSizeFromLine(line);
                    players.add(new Player(name, stackSize));
                }
            }
            
            // get player hole cards
            if (line.startsWith("Dealt")) {
                setPlayerHoleCards(line, players);
            }
            
        }
        
        // add initial state to state -list
        states.add(new GameState(players, 0));
    }
    
    private String getNameFromLine(String line) {
        String[] splittedLine = line.split(" ");
        String name = splittedLine[2];

        // if the name has spaces, add the missing parts
        int i = 3;
        while (i < splittedLine.length && !splittedLine[i].startsWith("(")) {
            name += " " + splittedLine[i];
            i++;
        }
        
        return name;
    }
    
    private int getStackSizeFromLine(String line) {
        String[] splittedLine = line.split(" ");
        String stackString = splittedLine[splittedLine.length - 1];
        return getAmountInCentsFromString(stackString);
    }
    
    private void setPlayerHoleCards(String line, ArrayList<Player> players) {
        String[] splittedLine = line.split(" ");
                
        // only add cards if player has none
        for (Player player : players) {
            if (line.contains(player.getName()) && player.getCards().size() == 0) {
                // get the first card
                String firstCardString = Arrays.stream(splittedLine)
                        .filter(s -> s.startsWith("["))
                        .findFirst()
                        .get();

                // get first card index in the splitted line
                int firstCardIndex = Arrays.asList(splittedLine).indexOf(firstCardString);

                for (int i = firstCardIndex; i < splittedLine.length; i++) {
                    // remove possible brackets from card string
                    String bracketless = splittedLine[i].replaceAll("\\[|\\]", "");

                    if (bracketless.equals("X")) {
                        // if card is unknown, add blank card (value -1)
                        player.addCard(new Card());
                    } else {
                        // get card integer value
                        int value = Card.intValue(bracketless.substring(0, 1));
                        // get card suit
                        Suit suit = Card.stringToSuit(bracketless.substring(1));
                        // add card
                        player.addCard(new Card(value, suit));
                    }
                }
            }
        }
    }
    
    public void buildActionLog(ArrayList<String> lines) {
        // go through all the lines
        for (String line : lines) {
            // check if line starts with a player name
            for (String player : playerNames) {
                if (line.startsWith(player)) {
                    // if true, add a game state with the action described in the line
                    boolean handComplete = addPlayerAction(line, player);
                    
                    if (handComplete) {
                        // handle hand ending
                        System.out.println("HAND COMPLETED on line " + line);
                        return;
                    }
                }
            }
            
            // check if line starts with dealing additional cards
            // TODO
            
            // check for new street
            if (line.startsWith("***")) {
                // set new pot and labels
                setNewPot();
            }
        }
    }
    
    public void setNewPot() {
        ArrayList<Player> previous = states.get(states.size() - 1).getPlayers();
        int pot = states.get(states.size() - 1).getPot();
        
        ArrayList<Player> newPlayers = new ArrayList<>();
        
        // add all bets to the pot
        for (Player p : previous) {
            pot += p.getBet();
            Player newPlayer = new Player(p.getName(), p.getStackSize()); // kortit puuttuu atm, ei tarvi laittaakkaa ku uudelle..
            if (p.getLabel().equals("fold") || p.getLabel().equals("folded")) {
                newPlayer.setLabel("folded");
            } else {
                newPlayer.setLabel("waiting for turn");
            }
            
            newPlayers.add(newPlayer);
        }
        
        // create new game state with updated pot and labels
        states.add(new GameState(newPlayers, pot));
    }
    
    public boolean addPlayerAction(String line, String player) {
        String[] splittedLine = line.split(" ");
        int i = getActionIndex(player);

        // get the word after the player's name as the action of the line
        String action = splittedLine[i];
        int bet = 0;
        int potIncrement = 0;

        // handle folds, calls and 'wins'
        if (action.equals("folds") || action.equals("checks")) {
            // remove the "s" from action string
            action = action.substring(0, action.length()-1); // ehkä jos "wins" niin bet vois olla potin koko
        }

        // handle bets and raises and calls
        if (action.equals("bets") || action.equals("raises") || action.equals("calls")) {
            // remove the "s" from action string
            action = action.substring(0, action.length()-1);
            bet = getAmountInCentsFromString(splittedLine[i+1]);
        }

        // handle antes, bring ins and blinds
        if (splittedLine.length > i+1) {
            if (splittedLine[i+1].equals("ante")) {
                action = "post ante"; // vai pelkkä ante?
                bet = getAmountInCentsFromString(splittedLine[i+2]);
                potIncrement = bet;
            } else if (splittedLine[i+1].equals("in")) {
                action = "bring in";
                bet = getAmountInCentsFromString(splittedLine[i+3]);
                potIncrement = bet;
            } else  if (splittedLine[i+1].equals("small")) {
                action = "small blind";
                bet = getAmountInCentsFromString(splittedLine[i+2]);
                potIncrement = bet;
            } else  if (splittedLine[i+1].equals("big")) {
                action = "big blind";
                bet = getAmountInCentsFromString(splittedLine[i+2]);
                potIncrement = bet;
            }
        }
        
        // if hand has a winner, handle win and return true
        if (action.equals("wins")) { // pitää tarkistaa splittipotit !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            createSummary(player);
            return true;
        }
        
        // add a new state to the action log with the current state of the game and return false
        createState(player, action, bet, potIncrement);
        return false;
                    // pitää lisätä myös maholliset jaetut kortit
    }
    
    // ei ota tällä hetkellä rakea huomioon eikä jakopotteja, mahollisesti pitää tehä playerille win -flägi
    private void createSummary(String name) {
        // get players last bet to return it to the player
        int lastBet = states.get(states.size() - 1).getPlayers().stream().filter(p -> p.getName().equals(name)).findFirst().get().getBet();
        int pot = states.get(states.size() - 1).getPot();
        
        // add winscreen
        createState(name, "wins " + pot, -1 * lastBet, 0);
        
        // form summary screen
        ArrayList<Player> players = states.get(states.size() - 1).getPlayers();
        ArrayList<Player> summaryPlayers = new ArrayList<>();
        
        pot = states.get(states.size() - 1).getPot();
        for (Player p : players) {
            Player newPlayer;
            // add the pot in the winners stack
            if (p.getName().equals(name)) { // ehkä if winner == true ?
                newPlayer = new Player(name, p.getStackSize() + pot);
                newPlayer.setLabel("WON " + calculateProfit(name, pot));
            } else {
                newPlayer = new Player(p.getName(), p.getStackSize());
                newPlayer.setLabel(("LOST " + calculateProfit(p.getName(), 0)));
                
            }
            summaryPlayers.add(newPlayer);
        }
        
        states.add(new GameState(summaryPlayers, pot));
    }
    
    private int calculateProfit(String name, int addition) {
        ArrayList<Player> initialPlayers = states.get(0).getPlayers();
        ArrayList<Player> endPlayers = states.get(states.size() - 1).getPlayers();
        int initialStack = initialPlayers.stream().filter(p -> p.getName().equals(name)).findFirst().get().getStackSize();
        //  fix end stack in case of hand winner by adding the pot
        int endStack = endPlayers.stream().filter(p -> p.getName().equals(name)).findFirst().get().getStackSize() + addition;
        return endStack - initialStack;
    }
     // ja getcards ehkä ongelma koska tulee shallow?
    private void createState(String name, String label, int bet, int potIncrement) { // bet needs to be in cents
        // get player list and from previous state
        ArrayList<Player> previousPlayers = states.get(states.size() - 1).getPlayers();
        
        // add increment to pot in case of antes, blinds or bring ins
        int pot = states.get(states.size() -1 ).getPot() + potIncrement;
        
        ArrayList<Player> newPlayers = new ArrayList<>();
        
        // change the player whose name is given as a parameter and copy the rest to newPlayers
        for (Player p : previousPlayers) {
            if (p.getName().equals(name)) {
                int newStackSize = p.getStackSize() - bet; // bet needs to be cents
                // create new player with new data (bet can only be negative in case of hand completion)
                Player newPlayer = new Player(name, newStackSize, p.getCards(), bet > 0 ? bet : 0, label);
                // set player's hasTurn to true
                newPlayer.setHasTurn(true);
                newPlayers.add(newPlayer);
            } else {
                // set hasTurn to false
                Player newPlayer = new Player(p.getName(), p.getStackSize(), p.getCards(), p.getBet(), p.getLabel());
                newPlayers.add(newPlayer);
            }
        }
        
        // add a new state with updated player list
        GameState newState = new GameState(newPlayers, pot);
        states.add(newState);
    }
    
    private int getActionIndex(String name) {
        // return the index indicating player action
        return name.split(" ").length;
    }
    
    public int getAmountInCentsFromString(String stackString) {
        /**
         * Converts a string formatted '(x,xxx.xx)' to integer 'xxxxxx', ie. (1,999.26) returns 199926
         */
        
        // remove parenthesis if they exist
        if (stackString.startsWith("(")) {
            stackString = stackString.substring(1, stackString.length() - 1);
        }
        
        // if a decimal dot exists, remove it, if not, add two zeros to convert to cents
        if (stackString.contains(".")) {
            stackString = stackString.replaceAll("[.]", "");
        } else {
            stackString += "00";
        }
        
        // remove possible commas
        stackString = stackString.replaceAll("[,]", "");
        
        // convert to int
        int stackSize = Integer.valueOf(stackString);
        
        return stackSize;
    }
}
