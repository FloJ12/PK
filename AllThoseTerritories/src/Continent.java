/**
 * Contains all territories of
 * Created by Florian on 13.01.2016.
 */
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

public class Continent {
    private String c_name;
    public Map<String, Territory> territories = new HashMap<>();
    private int bonus;

    public Continent(String name, int bonus) {
        this.c_name = name;
        this.bonus = bonus;
    }

    public void addTerritory(Territory territory) {
        territories.put(territory.name, territory);
    }

    public void paintBorders(Color color) {
        for (Map.Entry<String, Territory> entry : territories.entrySet()) {
            Territory t = entry.getValue();
            t.setBorderColor(color);
        }
    }
}
