/**
 * Created by Florian on 13.01.2016.
 */
public class Player {
    int availableReinforcements = 0;
    String name;
    boolean isHuman;
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
    }

    public String toString() {
        return name;
    }

    public void occupy() {
        // TODO: acquire an uncontested territory
    }

    public void attackAndMove() {
        boolean turnCompleted = false;
        boolean gameWon   = false;

        while (!turnCompleted && !gameWon) {
            // TODO: attacking and moving of armies
            gameWon = checkIfGameWon();
        }
    }

    public void deployReinforcements() {
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
