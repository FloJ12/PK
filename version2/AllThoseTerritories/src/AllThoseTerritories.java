/**
 * Created by nam on 08.01.16.
 */
public class AllThoseTerritories {
    Player user = new Player("User", true);
    Player ki = new Player("KI", false);
    Territory[] territories = {}; // TODO: Read Territories from file
    // TODO: Make methods static

    public static void main(String[] args) {
    }

    public void phaseOccupy() {
        while(!allOccupied()) {
            user.occupy();
            ki.occupy();
        }
    }

    public void phaseConquer() {
        user.attackAndMove();
        ki.attackAndMove();
    }

    public boolean allOccupied() {
        for (Territory t : territories) {
            if (t.owned_by == null) {
                return false;
            }
        }
        return true;
    }
}

class Continent {
    String c_name;
    Territory[] territories;

    Continent(String c_name, int tNum) {
        this.c_name = c_name;
        this.territories = new Territory[tNum];
    }
}

class Territory {
    String t_name;
    Player owned_by;
    int armyStrength;
    Territory[] adjacency;

    Territory(String t_name, int edgesNum) {
        this.t_name = t_name;
        this.owned_by = null;
        this.armyStrength = 0;
        this.adjacency = new Territory[edgesNum];
    }
}

class Player {
    int availableReinforcements = 0;
    String name;
    boolean isHuman;
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
    }

    public void occupy() {
        // TODO: acquire an uncontested territory
    }

    public void deployReinforcements() {
        availableReinforcements = checkCountOfReinforcements();
        if (availableReinforcements > 0) {
            // TODO: deploy reinforcements
        }
    }

    public void attackAndMove() {
        boolean turnCompleted = false;
        boolean gameWon   = false;

        while (!turnCompleted && !gameWon) {
            gameWon = checkIfGameWon();
            // TODO: attacking and moving of armies
        }
    }

    public int checkCountOfReinforcements() {
        // TODO: count territories owned by this player and calculate resulting available reinforcements
        return 0;
    }

    public boolean checkIfGameWon() {
        // TODO: check if all territories are owned by this player
        return false;
    }
}


