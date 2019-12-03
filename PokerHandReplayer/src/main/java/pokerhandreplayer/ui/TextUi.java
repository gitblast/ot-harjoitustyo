
package pokerhandreplayer.ui;

import java.util.ArrayList;
import java.util.Scanner;
import pokerhandreplayer.domain.GameState;

    // for testing purposes and proof of concept
public class TextUi {

    public TextUi() {
    }
    
    public static void launch(ArrayList<GameState> replay) {        
        Scanner s = new Scanner(System.in);
        int i = 0;
        
        if (replay.size() == 0) {
            System.out.println("No replay");
            return;
        }
        
        System.out.println("HAND REPLAY: (" + replay.size() + " states)");
        System.out.println("");
        while (true) {
            System.out.println("");
            System.out.println("-------------------------------");
            System.out.println("STATE NRO " + (i+1) + (i == replay.size() - 1 ? " (SUMMARY)" : ""));
            System.out.println("");
            System.out.println(replay.get(i));
            System.out.println("");
            System.out.println("Select 'n' for next state, 'p' for previous or 'x' to exit:");
            String x = s.nextLine();
            
            if (x.equals("x")) {                
                System.out.println("------------------------");
                break;
            }
            
            if (x.equals("n")) {
                i = i + 1 > replay.size() - 1 ? 0 : i + 1;
            }
            
            if (x.equals("p")) {
                i = i - 1 < 0 ? replay.size() - 1 : i - 1;
            }
        }
    }
    
}
