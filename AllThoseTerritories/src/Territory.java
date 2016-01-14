import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Florian on 13.01.2016.
 */
public class Territory {
    public String name;
    public Player owned_by;
    public int armyStrength;
    private Map<String, Territory> neighbors = new HashMap();
    private List<Polygon> polygons = new ArrayList<>();
    private List<Polyline> polylines = new ArrayList<>();
    private Point2D capital;

    public Territory(String name) {
        this.name = name;
        this.owned_by = null;
        this.armyStrength = 0;
    }

    public void addNeighbor(Territory neighbor) {
        neighbors.put(neighbor.name, neighbor);
    }

    public void addCapital(Point2D capital) {
        this.capital = capital;
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public void addPolyline(Polyline polyline) {
        polylines.add(polyline);
    }

    public void setBorderColor(Color borderColor) {
        for(Polyline p : polylines) {
            p.setStroke(borderColor);
        }
    }

    public Map<String, Territory> getNeighbors() {
        return neighbors;
    }

    public Point2D getCapital() {
        return capital;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public List<Polyline> getPolylines() {
        return polylines;
    }
}
