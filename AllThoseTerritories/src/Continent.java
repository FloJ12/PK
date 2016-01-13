/**
 * Created by Florian on 13.01.2016.
 */
public class Continent {
    String c_name;
    Territory[] territories;

    public Continent(String c_name, int tNum) {
        this.c_name = c_name;
        this.territories = new Territory[tNum];
    }
}
