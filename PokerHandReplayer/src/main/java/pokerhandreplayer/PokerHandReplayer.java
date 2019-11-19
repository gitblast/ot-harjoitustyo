package pokerhandreplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import pokerhandreplayer.domain.Card;
import pokerhandreplayer.domain.HandCreator;
import pokerhandreplayer.domain.Suit;


public class PokerHandReplayer {

    public static void main(String[] args) {
        ArrayList<String> lines = getTestHandFromFile();
        HandCreator hc = new HandCreator();
        hc.createHand(lines);
    }
    
    public static ArrayList<String> getTestHandFromFile() {        
        ArrayList<String> lines = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(new File("handhistory.txt"));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return lines;
    }
    
}
