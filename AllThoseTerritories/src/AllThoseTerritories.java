/**
 * Created by nam on 08.01.16.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AllThoseTerritories {

    private Territory[] territories = {};
    private Player[] humanPlayers;
    private Player[] kiPlayers;

    public AllThoseTerritories(Player[] humanPlayers, Player[] kiPlayers, String pathToMap) {
        this.territories = readTerritories(pathToMap);
        this.humanPlayers = humanPlayers;
        this.kiPlayers = kiPlayers;
    }

    public Territory[] readTerritories(String pathToMap) {
        try {
            // Two buffered reader exceptions like the examples in the lecture
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(pathToMap));

                String line;
                int countTerritories = 0;

                // Find out count of Territories
                for(int i = 0; (line = in.readLine()) != null; i++){
                    String[] parts = line.split(" ");
                    if(parts[0].equals("patch-of")) {
                        countTerritories++;
                    }
                }
                in.close();
                Territory[] readTerritories = new Territory[countTerritories];
                // Ensure that readTerritories is filled up one index after anoter
                int tIndex = 0;

                // Fill array with Territories
                in = new BufferedReader(new FileReader(pathToMap));
                for(int i = 0; (line = in.readLine()) != null; i++){
                    String[] parts = line.split(" ");
                    if(parts[0].equals("patch-of")) {
                        String territoryName = parts[1];

                        // find Territory name (can be 1, 2 or 3 Words)
                        int firstCoordinateIndex = 2;
                        for(; !parts[firstCoordinateIndex].matches("[0-9]+");
                            firstCoordinateIndex++) {

                            // For every item that is not a coordinate concatenate the items
                            // to get territoryName
                            territoryName = territoryName + " " + parts[firstCoordinateIndex];

                        }
                        readTerritories[tIndex] = new Territory(territoryName);
                        tIndex++;
                    }
                }
                return readTerritories;
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }
        catch(IOException ex) {
            System.err.println("I/O Error: " + ex.getMessage());
        }
        return null;
    }

    public void phaseOccupy() {
        // As long as not all territories are occupied, this phase continues
        while(!allOccupied()) {
            for(Player user : humanPlayers) {
                user.occupy();
            }
            for(Player ki : kiPlayers) {
                ki.occupy();
            }
        }
    }

    public void phaseConquer() {
        // As long as the game is not won, this phase continues
        // Parameter for isWon(Player player) can be anybody who owns a territory
        while(!isWon(territories[0].owned_by)) {
            for (Player user : humanPlayers) {
                user.deployReinforcements();
            }
            for (Player ki : kiPlayers) {
                ki.deployReinforcements();
            }
            for (Player user : humanPlayers) {
                user.attackAndMove();
            }
            for (Player ki : kiPlayers) {
                ki.attackAndMove();
            }
        }
    }

    private boolean allOccupied() {
        return isWon(null);
    }

    private boolean isWon(Player user) {
        for (Territory t : territories) {
            if (t.owned_by == user) {
                return false;
            }
        }
        return true;
    }
}


