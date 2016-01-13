/**
 * Created by nam on 08.01.16.
 */
import java.io.BufferedReader;
import java.io.FileReader;
public class AllThoseTerritories {

    private Territory[] territories = {};
    private Player[] humanPlayers;
    private Player[] kiPlayers;

    public AllThoseTerritories(Player[] humanPlayers, Player[] kiPlayers, String pathToMap) {
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
}


