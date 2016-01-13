/**
 * Created by Florian on 13.01.2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
        //Parent root2 = FXMLLoader.load(getClass().getResource("gui.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root, 1250, 650);
        stage.setTitle("Hello World Example");
        stage.setScene(scene);

        Group g = new Group();
        try {
            // Two buffered reader exceptions like the examples in the lecture
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(pathToMap));

                String line;
                Map<String, Polygon> territories = new HashMap<>();
                Map<String, Polyline> borders = new HashMap<>();

                // Create a mapping for each Territory to its polygon and paint it
                for(int i = 0; (line = in.readLine()) != null; i++){
                    String[] parts = line.split(" ");
                    if(parts[0].equals("patch-of")) {
                        String territoryName = parts[1];

                        // find Territory name (can be 1, 2 or 3 Words)
                        int firstCoordinateIndex = 2;
                        for(; !parts[firstCoordinateIndex].matches("[0-9]+");
                            firstCoordinateIndex++) {

                            // For every item that is not a coordinate concatenate the items
                            // to get territoryName
                            territoryName = territoryName + " " + parts[firstCoordinateIndex];

                        }

                        // map territoryName to corresponding polygon and polyline
                        territories.put(territoryName, new Polygon());
                        borders.put(territoryName, new Polyline());

                        // add every point to the polygon and polyline
                        for(int j = firstCoordinateIndex; j < parts.length; j++) {
                            double coordinate =  Double.parseDouble(parts[j]);
                            territories.get(territoryName).getPoints().addAll(coordinate);
                            borders.get(territoryName).getPoints().addAll(coordinate);
                        }

                        // Add the resulting Polygon and Polylines to the Interface
                        territories.get(territoryName).setFill(Color.LIGHTGRAY);
                        borders.get(territoryName).setStrokeWidth(3);
                        g.getChildren().add(borders.get(territoryName));
                        g.getChildren().add(territories.get(territoryName));
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
        scene.setRoot(g);
        stage.show();
    }

    @Override public void stop() {}

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(errmsg);
            return;
        }
        launch(args);
    }
}
