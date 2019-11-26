package pokerhandreplayer.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class HandCreator {
    private ArrayList<Player> players;

    public HandCreator() {
        this.players = new ArrayList<>();
    }   

    public void createHand(ArrayList<String> lines) {
        extractPlayers(lines);
        //buildActionLog(lines);
    }
    
    public void extractPlayers(ArrayList<String> lines) {
        ArrayList<String> usedNames = new ArrayList<>();
        
        for (String line : lines) {
            // get player names
            if (line.startsWith("Seat ")) {
                String[] splittedLine = line.split(" ");
                String name = splittedLine[2];
                
                // if the name has spaces, add the missing parts
                int i = 3;
                while (i < splittedLine.length && !splittedLine[i].startsWith("(")) {
                    name += " " + splittedLine[i];
                    i++;
                }
                
                // if the name hasn't been added yet, get the stack size and add to players -list
                if (!usedNames.contains(name)) {
                    usedNames.add(name);
                    
                    String stackString = splittedLine[splittedLine.length - 1];
                    int stackSize = getStackSizeFromString(stackString);
                    players.add(new Player(name, stackSize));
                }
            }
            
            // get player hole cards
            if (line.startsWith("Dealt")) {
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
                        
                        System.out.println("PLAYER: " + player);
                        System.out.println("CARDS: " + player.getCards());
                        System.out.println("");
                    }
                }
            }
            
        }
        
    }
    
    public void buildActionLog(ArrayList<String> lines) {
        ArrayList<GameState> actionLog = new ArrayList<>();
        // testing
        for (String line : lines) {
            for (Player player : players) {
                if (line.startsWith(player.getName())) {
                    String[] splittedLine = line.split(" ");
                    // pitää ottaa vielä huomioon nimet joissa välejä
                    String action = splittedLine[1];
                    int bet = 0;
                    
                    // pitää ottaa vielä huomioon lopun summaryt jne
                    if (splittedLine[2].equals("ante")) {
                        bet = Integer.valueOf(splittedLine[3]);
                    } else if (splittedLine[2].equals("in")) {
                        bet = Integer.valueOf(splittedLine[4]);
                    } else {
                        bet = Integer.valueOf(splittedLine[2]);
                    }
                    
                    // ei toimi samalla tavalla ku js, lue mikä ero
                    //ArrayList<Player> thisStatePlayers = players.stream().map(p -> new Player(p.getName(), p.getStackSize()));
                    
                    // luodaan gamestate jossa pelaajat mapattuna niin että kyseisen rivin actionin pelaaja mapattu, muut samoja ja potsize lasketaan rivin muutoksen mukaan
                    // pitää lisätä myös maholliset jaetut kortit
                }
            }
        }
    }
    
    public int getStackSizeFromString(String stackString) {
        /**
         * Converts a string formatted '(x,xxx.xx)' to integer 'xxxxxx', ie. (1,999.26) returns 199926
         */
        
        // remove parenthesis
        stackString = stackString.substring(1, stackString.length() - 1);
        
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
