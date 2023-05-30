import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Main extends Application {
    private ProgressBar progressBar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MusicPlayer");

        // Load the custom icon image
        Image iconImage = new Image(getClass().getResourceAsStream("music-icon.png"));

        // Set the custom icon for the stage
        primaryStage.getIcons().add(iconImage);

        // create the buttons
        Button playButton = createButton("Play", "play-button");
        Button pauseButton = createButton("Pause", "pause-button");
        Button stopButton = createButton("Stop", "stop-button");

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 0.0, 20.0, 10.0, 0.0, 20.0);
        triangle.setFill(javafx.scene.paint.Color.WHITE);
        playButton.setGraphic(triangle);

        Polygon square = new Polygon();
        square.getPoints().addAll(0.0, 0.0, 20.0, 0.0, 20.0, 20.0, 0.0, 20.0);
        square.setFill(javafx.scene.paint.Color.WHITE);
        stopButton.setGraphic(square);

        // create the progress bar
        progressBar = new ProgressBar();
        progressBar.setProgress(0.5);
        progressBar.getStyleClass().add("progress-bar");
        progressBar.setPrefWidth(200); // Set the preferred width of the progress bar

        // set up the layout
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(playButton, pauseButton, stopButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.getChildren().addAll(progressBar, buttonContainer);
        section.setAlignment(Pos.CENTER);

        // Create the top section with the progress bar
        StackPane topSection = new StackPane();
        topSection.getChildren().add(progressBar);

        // Create the bottom section with the button container
        StackPane bottomSection = new StackPane();
        bottomSection.getChildren().add(section);

        // Create the SplitPane and set the orientation
        SplitPane splitPane = new SplitPane(topSection, bottomSection);
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);


        VBox layout = new VBox();
        layout.getChildren().add(splitPane);
        VBox.setVgrow(splitPane, javafx.scene.layout.Priority.ALWAYS);
        layout.setPadding(new Insets(10));



        Scene scene = new Scene(layout, 300, 250);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Apply CSS styles
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text, String styleClass) {
        Button button = new Button();
        button.getStyleClass().add(styleClass);
        button.setMinSize(40, 40); // Circular size
        button.setMaxSize(40, 40);
        button.setShape(new Circle(20));
        return button;
    }
}
