import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian on 13.01.2016.
 */
public class Territory {
    String t_name;
    Player owned_by;
    int armyStrength;
    private Map<String, Territory> neighbors = new HashMap();

    public Territory(String t_name) {
        this.t_name = t_name;
        this.owned_by = null;
        this.armyStrength = 0;
    }

    public void addNeighbor(Territory neighbor) {
        neighbors.put(neighbor.t_name, neighbor);
    }
}
