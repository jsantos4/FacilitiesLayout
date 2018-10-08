package FacilitiesLayout;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    private GridPane gridPane;
    private Scene scene;

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Group group = new Group();
        scene = new Scene(group, 800, 800);
        gridPane = new GridPane();
        group.getChildren().add(gridPane);
        stage.setTitle("Facility Layout");
        stage.setScene(scene);
        stage.show();
        Timer t = new Timer();


        int threads = (Runtime.getRuntime().availableProcessors() > 32) ? 32 : Runtime.getRuntime().availableProcessors();
        Floor floor = new Floor(10, 10);
        GeneticAlgorithm generation = new GeneticAlgorithm(floor);
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.execute(generation);
        }

        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                Platform.runLater(() -> {
                    try {
                        updateGUI(generation.getBest(), stage);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 500, 500);

        executor.shutdown();

    }

    private void updateGUI(Floor f, Stage stage) throws InterruptedException{

        for (int row = 0; row < f.getRows(); row++) {
            for (int col = 0; col < f.getColumns(); col++) {
                Paint paint = f.getStations()[row][col].color;
                Rectangle rec = new Rectangle(60, 60, paint);
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);

                gridPane.getChildren().addAll(rec);

            }
        }

        stage.setTitle("Layout");
        stage.setScene(scene);

    }
}
