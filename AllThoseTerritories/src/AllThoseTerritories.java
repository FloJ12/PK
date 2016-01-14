/**
 * Created by nam on 08.01.16.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllThoseTerritories {

    private Map<String, Territory> territories;
    private Map<String, Continent> continents;
    private Player[] humanPlayers;
    private Player[] kiPlayers;

    public AllThoseTerritories(Player[] humanPlayers, Player[] kiPlayers, String pathToMap) {
        this.territories = readTerritories(pathToMap);
        this.continents = readContinents(pathToMap);
        this.humanPlayers = humanPlayers;
        this.kiPlayers = kiPlayers;
    }

    // Returns a Map containing all Territories. By doing this,
    // it instantiates all Territory objects, adds it to the map,
    // and then calls addNeighbors.
    private Map<String, Territory> readTerritories(String pathToMap) {
        try {
            // Two buffered reader exceptions like the examples in the lecture
            BufferedReader in = null;
            try {
                Map<String, Territory> territoryMap = new HashMap<>();

                // Fill array with Territories
                in = new BufferedReader(new FileReader(pathToMap));
                String line;
                for(int i = 0; (line = in.readLine()) != null; i++){
                    String[] parts = line.split(" ");
                    String territoryName = getName(parts);
                    if(parts[0].equals("patch-of")) {
                        territoryMap.put(territoryName, new Territory(territoryName));
                    }
                }
                addNeighbors(pathToMap, territoryMap);
                return territoryMap;
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

    // Adds all neighbors of territories. Territories and all neighbors of these territories
    // specified in "neighbor-of" lines must exist in territoryMap
    private void addNeighbors(String pathToMap, Map<String, Territory> territoryMap) {
        try {
            // Two buffered reader exceptions like the examples in the lecture
            BufferedReader in = null;
            try {
                // Read file line by line, process only "neighbors-of" lines
                in = new BufferedReader(new FileReader(pathToMap));
                String line;
                for(int i = 0; (line = in.readLine()) != null; i++){
                    String[] parts = line.split(" ");
                    String territoryName = getName(parts);
                    if(parts[0].equals("neighbors-of")) {
                        // Get place of first neighbor in line
                        int firstNeighborIndex = getFirstTerritoryIndex(parts);

                        // Build a String containing only neighbors seperated by -
                        // i.e. Alaska-Great Britain-North Western Territory
                        StringBuilder builder = new StringBuilder();
                        for (int j = firstNeighborIndex; j < parts.length; j++) {
                            // Add space only for Territories, not before and after separator - and :
                            if(!parts[j-1].equals("-") && !parts[j-1].equals(":") && !parts[j].equals("-")) {
                                builder.append(" " + parts[j]);
                            } else {
                                builder.append(parts[j]);
                            }
                        }
                        String neighborsString = builder.toString();

                        // Split String to get Array containing only neighbors as strings
                        String[] neighborsArray = neighborsString.split("-");

                        // Add every corresponding neighbor Territory object to the current territory
                        for (String neighborString : neighborsArray) {
                            Territory neighbor = territoryMap.get(neighborString);
                            territoryMap.get(territoryName).addNeighbor(neighbor);
                        }
                    }
                }

            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }
        catch(IOException ex) {
            System.err.println("I/O Error: " + ex.getMessage());
        }
    }

    // Returns a map containing all continents. By doing this, it instantiates and creates all
    // necessary Continent objects. Members of continents must
    // exist in the territoryMap of "this" object.
    private Map<String, Continent> readContinents(String pathToMap) {
        try {
            // Two buffered reader exceptions like the examples in the lecture
            BufferedReader in = null;
            try {
                Map<String, Continent> continentMap = new HashMap<>();

                // Read file line after line, process only continents
                in = new BufferedReader(new FileReader(pathToMap));
                String line;
                for(int i = 0; (line = in.readLine()) != null; i++){
                    String[] parts = line.split(" ");
                    String continentName = getName(parts);
                    if(parts[0].equals("continent")) {
                        // Get place of first member in line
                        int firstTerritoryIndex = getFirstTerritoryIndex(parts);
                        // Instantiate continent object and add it to the map
                        int bonus = Integer.parseInt(parts[firstTerritoryIndex - 2]);
                        continentMap.put(continentName, new Continent(continentName, bonus));

                        // Build a String containing only members seperated by -
                        // i.e. Alaska-Great Britain-North Western Territory
                        StringBuilder builder = new StringBuilder();
                        for (int j = firstTerritoryIndex; j < parts.length; j++) {
                            // Add space only for Territories, not before and after separator - and :
                            if(!parts[j-1].equals("-") && !parts[j-1].equals(":") && !parts[j].equals("-")) {
                                builder.append(" " + parts[j]);
                            } else {
                                builder.append(parts[j]);
                            }
                        }
                        String territoriesString = builder.toString();

                        // Split String to get Array containing only members as strings
                        String[] territoriesArray = territoriesString.split("-");

                        // Add every corresponding member Territory object to the current continent
                        for (String territoryString : territoriesArray) {
                            Territory territory = territories.get(territoryString);
                            continentMap.get(continentName).addTerritory(territory);
                        }
                    }
                }
                return continentMap;
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

    // Returns first index containing a Territory for a "neighbor-of" line
    // or a "continent" line in an array resulting from a split line (separator " ")
    private int getFirstTerritoryIndex(String[] parts) {
        int firstTerritoryIndex = 2;
        for(; !parts[firstTerritoryIndex].equals(":");
            firstTerritoryIndex++) {}
        return firstTerritoryIndex + 1;
    }

    // Returns territory name from an array resulting
    // from a split line (separator " ")
    private String getName(String[] parts) {
        String territoryName = parts[1];

        // find Territory name (can be 1, 2 or 3 Words)
        int firstCoordinateIndex = 2;
        for(; !parts[firstCoordinateIndex].matches("[0-9]+") && !parts[firstCoordinateIndex].equals(":");
            firstCoordinateIndex++) {

            // For every item that is not a coordinate concatenate the items
            // to get territoryName
            territoryName = territoryName + " " + parts[firstCoordinateIndex];

        }
        return territoryName;
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
        // Get any entry from the Map (http://stackoverflow.com/questions/1509391/how-to-get-the-one-entry-from-hashmap-without-iterating)
        Map.Entry<String, Territory> random_entry = territories.entrySet().iterator().next();
        Territory random_territory = random_entry.getValue();
        while(!isWon(random_territory.owned_by)) {
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
        // Iterate over whole Map
        for (Map.Entry<String, Territory> entry : territories.entrySet()) {
            Territory territory = entry.getValue();
            if (territory.owned_by == user) {
                return false;
            }
        }
        return true;
    }
}


