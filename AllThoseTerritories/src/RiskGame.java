/**
 * Created by Florian on 13.01.2016.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RiskGame extends Application {

    private static final String errmsg = "Usage: java RiskGame <Path to .map file>";
    private Label label;
    public String pathToMap;

    @Override public void init() {
        label = new Label("Risk Game");
        pathToMap = getParameters().getUnnamed().get(0);
    }

    @Override public void start(Stage stage) throws Exception {
        // Start new game
        Player user = new Player("User", true);
        Player ki = new Player("KI", false);
        AllThoseTerritories game = new AllThoseTerritories(new Player[] {user}, new Player[] {ki}, pathToMap);

        //Parent root2 = FXMLLoader.load(getClass().getResource("gui.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root, 1250, 650);
        stage.setTitle("Risk game");
        stage.setScene(scene);

        Group g = new Group();
        try {
            // Two buffered reader exceptions like the examples in the lecture
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(pathToMap));
                String line;
                Map<Polygon, Territory> territoryOfPolygon = new HashMap<>();

                // Create a mapping for each Territory to its polygon and paint it
                for(int i = 0; (line = in.readLine()) != null; i++){
                    String[] parts = line.split(" ");
                    String name = getName(parts);
                    int firstCoordinateIndex = getFirstCoordinateIndex(parts);
                    Territory territory = game.getTerritoriesMap().get(name);

                    if(parts[0].equals("patch-of")) {
                        // add polygon and polyline to territory
                        Polygon polygon = new Polygon();
                        Polyline polyline = new Polyline();

                        // add every point to the polygon and polyline
                        for(int j = firstCoordinateIndex; j < parts.length; j++) {
                            double coordinate =  Double.parseDouble(parts[j]);
                            polygon.getPoints().addAll(coordinate);
                            polyline.getPoints().addAll(coordinate);
                        }

                        // add the polygon to the Territorymap
                        territoryOfPolygon.put(polygon, territory);

                        // Set a few more parameters
                        polygon.setFill(Color.LIGHTGRAY);
                        polyline.setStrokeWidth(2.5);

                        // Create an event handler for the polygon
                        polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                game.territoryClicked(territoryOfPolygon.get(polygon));
                            }
                        });

                        // Add the resulting Polygon and Polylines to the Territory
                        territory.addPolygon(polygon);
                        territory.addPolyline(polyline);

                        // Add them to the interface
                        //g.getChildren().add(polygon);
                        //g.getChildren().add(polyline);
                    }
                    else if(parts[0].equals("capital-of")) {
                        // First add capital coordinates to the territory object
                        double xcoordinate = Double.parseDouble(parts[firstCoordinateIndex]);
                        double ycoordinate = Double.parseDouble(parts[firstCoordinateIndex+1]);
                        territory.addCapital(new Point2D(xcoordinate, ycoordinate));
                    }
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }
        catch(IOException ex) {
            System.err.println("I/O Error: " + ex.getMessage());
        }
        // Paint everything in the right order

        // First the connecting lines between neighbors, so that those of land-neighbors are later hidden
        // by the land-polygons
        for(Map.Entry<String, Territory> t_entry : game.getTerritoriesMap().entrySet()) {
            Territory t = t_entry.getValue();
            for (Map.Entry<String, Territory> n_entry : t.getNeighbors().entrySet()) {
                Territory n = n_entry.getValue();
                // Alaska and Kamtschatka connecting line should come from behind
                if(t.name.equals("Alaska") && n.name.equals("Kamchatka")) {
                    Polyline connection1 = new Polyline(t.getCapital().getX(), t.getCapital().getY(), 0, t.getCapital().getY());
                    Polyline connection2 = new Polyline(1250, n.getCapital().getY(), n.getCapital().getX(), n.getCapital().getY());
                    connection1.setStroke(Color.LIGHTGRAY);
                    connection2.setStroke(Color.LIGHTGRAY);
                    g.getChildren().addAll(connection1, connection2);
                } else {
                    Polyline connection = new Polyline(t.getCapital().getX(), t.getCapital().getY(), n.getCapital().getX(), n.getCapital().getY());
                    connection.setStroke(Color.LIGHTGRAY);
                    g.getChildren().add(connection);
                }
            }
        }

        // Then the polygons borders, and armystrength for every territory
        for(Map.Entry<String, Territory> t_entry : game.getTerritoriesMap().entrySet()) {
            Territory t = t_entry.getValue();
            // Add all polygons and polylines to the GUI
            for(Polygon p : t.getPolygons()) {
                g.getChildren().add(p);
            }
            for(Polyline p : t.getPolylines()) {
                g.getChildren().add(p);
            }
            // Add armystrength of territory
            Label lbl = new Label("0");
            t.addLabel(lbl);
            g.getChildren().add(lbl);
        }

        // Other colors for borders of territories of other continents
        Color[] colors = {Color.VIOLET, Color.GREEN, Color.ORANGE, Color.BLACK, Color.YELLOW, Color.BROWN};
        int colorIndex = 0;
        // For every continent paint borders of every territory
        for (Map.Entry<String, Continent> c_entry : game.getContinentsMap().entrySet()) {
            Continent continent = c_entry.getValue();
            for (Map.Entry<String, Territory> entry : continent.territories.entrySet()) {
                Territory t = entry.getValue();
                t.setBorderColor(colors[colorIndex]);
            }
            colorIndex++;
        }
        scene.setRoot(g);
        stage.show();
        game.start();
        //Following may be outdated, is now called via eventhandlers for GUI
        //game.phaseOccupy();
        //game.phaseConquer();
    }

    @Override public void stop() {}

    // Finds first index of an array which is a coordinate
    //Array is constructed from a line of a file formatted like world.map (split by " ")
    private static int getFirstCoordinateIndex(String[] parts) {
        String territoryName = parts[1];

        // find Territory name (can be 1, 2 or 3 Words)
        int firstCoordinateIndex = 2;
        for(; firstCoordinateIndex < parts.length && !parts[firstCoordinateIndex].matches("[0-9]+");
            firstCoordinateIndex++) {}
        return firstCoordinateIndex;
    }

    //Finds the name of a territory/continent out of an array.
    //Array is constructed from a line of a file formatted like world.map (split by " ")
    private static String getName(String[] parts) {
        String territoryName = parts[1];

        // find Territory name (can be 1, 2 or 3 Words)
        int firstCoordinateIndex = 2;
        for(; !parts[firstCoordinateIndex].matches("[0-9]+") && !parts[firstCoordinateIndex].equals(":");
            firstCoordinateIndex++) {

            // For every item that is not a coordinate concatenate the items
            // to get territoryName
            territoryName = territoryName + " " + parts[firstCoordinateIndex];

        }
        return territoryName;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(errmsg);
            return;
        }
        launch(args);
    }
}
