/**
 * Created by nam on 08.01.16.
 */
public class AllThoseTerritories {

    private Territory[] territories = {};
    private Player[] humanPlayers;
    private Player[] kiPlayers;

    public AllThoseTerritories(Player[] humanPlayers, Player[] kiPlayers) {
        this.territories = null; // TODO: Read Territories from file
        this.humanPlayers = humanPlayers;
        this.kiPlayers = kiPlayers;
    }

    public void phaseOccupy() {
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
        for(Player user : humanPlayers) {
            user.attackAndMove();
        }
        for(Player ki : kiPlayers) {
            ki.attackAndMove();
        }
    }

    private boolean allOccupied() {
        for (Territory t : territories) {
            if (t.owned_by == null) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Player user = new Player("User", true);
        Player ki = new Player("KI", false);
        AllThoseTerritories game = new AllThoseTerritories(new Player[] {user}, new Player[] {ki});
        game.phaseOccupy();
        game.phaseConquer();
    }
}

class Continent {
    String c_name;
    Territory[] territories;

    public Continent(String c_name, int tNum) {
        this.c_name = c_name;
        this.territories = new Territory[tNum];
    }
}

class Territory {
    String t_name;
    Player owned_by;
    int armyStrength;
    Territory[] adjacency;

    public Territory(String t_name, int edgesNum) {
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

    public void attackAndMove() {
        boolean turnCompleted = false;
        boolean gameWon   = false;

        while (!turnCompleted && !gameWon) {
            gameWon = checkIfGameWon();
            // TODO: attacking and moving of armies
        }
    }

    private void deployReinforcements() {
        availableReinforcements = checkCountOfReinforcements();
        if (availableReinforcements > 0) {
            // TODO: deploy reinforcements
        }
    }

    private int checkCountOfReinforcements() {
        // TODO: count territories owned by this player and calculate resulting available reinforcements
        return 0;
    }

    private boolean checkIfGameWon() {
        // TODO: check if all territories are owned by this player
        return false;
    }
}


