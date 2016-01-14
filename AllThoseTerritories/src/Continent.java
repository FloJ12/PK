import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian on 13.01.2016.
 */
public class Continent {
    String c_name;
    Map<String, Territory> territories = new HashMap<>();
    int bonus;

    public Continent(String name, int bonus) {
        this.c_name = name;
        this.bonus = bonus;
    }

    public void addTerritory(Territory territory) {
        territories.put(territory.name, territory);
    }
}
