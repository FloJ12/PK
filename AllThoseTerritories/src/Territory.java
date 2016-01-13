/**
 * Created by Florian on 13.01.2016.
 */
public class Territory {
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
