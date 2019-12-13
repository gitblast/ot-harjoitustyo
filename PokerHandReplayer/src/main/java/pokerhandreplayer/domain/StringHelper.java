/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerhandreplayer.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author jmammela
 */
public class StringHelper {
    
    /**
     * Takes the name of a player and returns the amount of words separated by spaces in the name.
     * @param name
     * @return amount of words in the name
     */
    public static int getActionIndex(String name) {
        // return the index indicating player action
        return name.split(" ").length;
    }
    
    /**
     * Converts a string formatted '(x,xxx.xx)' to integer 'xxxxxx', ie. (1,999.26) returns 199926
     * @param stackString
     * @return integer value of the parameter string
     */
    public static int getAmountInCentsFromString(String stackString) {
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
    
    /**
     * Converts a list of cards into a string representation.
     * @param cards
     * @return string representation of the list of cards
     */
    public static String getCardsString(ArrayList<Card> cards) {
        String s = "[ ";
        
        for (Card c : cards) {
            s += c.toString() + " ";
        }
        
        return s + "]";
    }
    
    /**
     * Exctracts cards from the string, turns them into objects and returns them as a list
     * @param line
     * @return list of cards
     */
    public static ArrayList<Card> getCardsFromLine(String line) {
        String[] splittedLine = line.split(" ");
        String firstCardString = Arrays.stream(splittedLine)
                .filter(s -> s.startsWith("["))
                .findFirst()
                .get();
        
        ArrayList<Card> cardsToAdd = new ArrayList<>();

        // get first card index in the splitted line
        int firstCardIndex = Arrays.asList(splittedLine).indexOf(firstCardString);

        for (int i = firstCardIndex; i < splittedLine.length; i++) {
            // remove possible brackets from card string
            String bracketless = splittedLine[i].replaceAll("\\[|\\]", "");

            if (bracketless.equals("X")) {
                // if card is unknown, add blank card (value -1)
                cardsToAdd.add(new Card());
            } else {
                // get card integer value
                int value = Card.intValue(bracketless.substring(0, 1));
                // get card suit
                Suit suit = Card.stringToSuit(bracketless.substring(1));
                // add card
                cardsToAdd.add(new Card(value, suit));
            }
        }
        
        return cardsToAdd;
    }
    
    /**
     * Separates a player name from a line and returns it without possible colons
     * @param line
     * @return player name
     */
    public static String getNameFromLine(String line) {
        String[] splittedLine = line.split(" ");
        String name = splittedLine[2];

        // if the name has spaces, add the missing parts
        int i = 3;
        while (i < splittedLine.length && !splittedLine[i].startsWith("(") && !splittedLine[i].startsWith("[")) {
            name += " " + splittedLine[i];
            i++;
        }
        
        // return the name without possible colon at the end
        return name.replaceAll(":", "");
    }
    
    /**
     * Finds a stack size from a line, converts it to an integer and returns it 
     * @param line
     * @return integer of stack size
     */
    public static int getStackSizeFromLine(String line) {
        String[] splittedLine = line.split(" ");
        String stackString = splittedLine[splittedLine.length - 1];
        return StringHelper.getAmountInCentsFromString(stackString);
    }
    
    public static ArrayList<String> getLinesFromFile(File file) {        
        ArrayList<String> lines = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return lines;
    }
}
