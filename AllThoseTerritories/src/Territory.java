import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian on 13.01.2016.
 */
public class Territory {
    String name;
    Player owned_by;
    int armyStrength;
    private Map<String, Territory> neighbors = new HashMap();

    public Territory(String name) {
        this.name = name;
        this.owned_by = null;
        this.armyStrength = 0;
    }

    public void addNeighbor(Territory neighbor) {
        neighbors.put(neighbor.name, neighbor);
    }
}
