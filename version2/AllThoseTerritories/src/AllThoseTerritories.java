/**
 * Created by nam on 08.01.16.
 */
public class AllThoseTerritories {

    public static void main(String[] args) { }


    public static boolean playerTurn(boolean startPhase) {
        if (startPhase) {
            // acquire an uncontested territory
            return false;
        }


        // normal turn
        int reinforcements = checkReinforcements(Possession.ofPlayer);
        boolean passTurn = false;
        boolean winCon   = false;

        while (!passTurn && !winCon) {
            if (reinforcements > 0) {
                // deploy reinforcements
            } else {
                // attack & move
                winCon = checkWinCon(Possession.ofPlayer);
            }
        }
        return winCon;
    }

    public static boolean opponentTurn(boolean startPhase) {
        if (startPhase) {
            // acquire an uncontested territory
            return false;
        }


        // normal turn
        int reinforcements = checkReinforcements(Possession.ofOpponent);
        boolean passTurn = false;
        boolean winCon   = false;

        while (!passTurn && !winCon) {
            if (reinforcements > 0) {
                // deploy reinforcements
            } else {
                // attack & move
                winCon = checkWinCon(Possession.ofOpponent);
            }
        }
        return winCon;
    }

    public static int checkReinforcements(Possession whichPlayer) {
        return 0;
    }

    public static boolean checkWinCon(Possession whichPlayer) {
        return false;
    }
}



class Continent {
    String c_name;
    int reinforce;
    Territory[] territories;

    Continent(String c_name, int reinforce, int tNum) {
        this.c_name = c_name;
        this.reinforce = reinforce;
        this.territories = new Territory[tNum];
    }
}

class Territory {
    String t_name;
    Possession state;
    int deploymentStrength;
    Territory[] adjacency;

    Territory(String t_name, int edgesNum) {
        this.t_name = t_name;
        this.state = Possession.noMansLand;
        this.deploymentStrength = 0;
        this.adjacency = new Territory[edgesNum];
    }
}

enum Possession {ofPlayer, ofOpponent, noMansLand}


