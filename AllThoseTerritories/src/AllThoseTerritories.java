/**
 * Created by nam on 08.01.16.
 */
public class AllThoseTerritories {

    public static void main(String[] args) {
        startGame();
    }




    public static void startGame() {
        setupGame();
    }

    public static void setupGame() {

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


