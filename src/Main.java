import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class Main extends Application {
    private ProgressBar progressBar;
    private Song song;
    private Label songNameLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MusicPlayer");

        // Load the custom icon image
        Image iconImage = new Image(getClass().getResourceAsStream("music-icon.png"));

        song = new Song("Sample Song", "Sample Artist", "file:///E:/1_wedding_songs/english_songs/Ed_Sheeran_Perfect.mp3");

        // Set the custom icon for the stage
        primaryStage.getIcons().add(iconImage);

        // create the buttons
        Button playButton = createPlayButton("Play", "play-button");
        Button pauseButton = createPauseButton("Pause", "pause-button");
        Button stopButton = createStopButton("Stop", "stop-button");

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

        songNameLabel = new Label();
        songNameLabel.getStyleClass().add("song-name-label");

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
        topSection.getChildren().add(songNameLabel);
        topSection.getStyleClass().add("top-section");

        // Create the bottom section with the button container
        StackPane bottomSection = new StackPane();
        bottomSection.getChildren().add(section);
        bottomSection.getStyleClass().add("bottom-section");

        // Create the SplitPane and set the orientation
        SplitPane splitPane = new SplitPane(topSection, bottomSection);
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        splitPane.getStyleClass().add("both-sections");

        VBox layout = new VBox();
        layout.getChildren().add(splitPane);
        VBox.setVgrow(splitPane, javafx.scene.layout.Priority.ALWAYS);
        layout.setPadding(new Insets(0));
        layout.getStyleClass().add("container");


        Scene scene = new Scene(layout, 300, 250);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Apply CSS styles
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createPlayButton(String text, String styleClass) {
        Button playButton = new Button();
        playButton.getStyleClass().add(styleClass);
        playButton.setMinSize(40, 40); // Circular size
        playButton.setMaxSize(40, 40);
        playButton.setShape(new Circle(20));

        // Handle the click event of the button
        playButton.setOnAction(event -> {
            if (song != null) {
                // Check if the song is currently playing
                if (playButton.getStyleClass().contains("playing")) {
                    song.stop();
                    playButton.getStyleClass().remove("playing");
                } else {
                    song.play();
                    playButton.getStyleClass().add("playing");
                    updateSongNameLabel(song.getTitle());
                }
            }
        });

        return playButton;
    }

    private Button createPauseButton(String text, String styleClass) {
        Button pauseButton = new Button();
        pauseButton.getStyleClass().add(styleClass);
        pauseButton.setMinSize(40, 40); // Circular size
        pauseButton.setMaxSize(40, 40);
        pauseButton.setShape(new Circle(20));

        // Handle the click event of the button
        pauseButton.setOnAction(event -> {
            if (song != null) {
                // Check if the song is currently playing
                if (pauseButton.getStyleClass().contains("paused")) {
                    song.play();
                    pauseButton.getStyleClass().remove("paused");
                } else {
                    song.pause();
                    pauseButton.getStyleClass().add("paused");
                    updateSongNameLabel(song.getTitle());
                }
            }
        });

        return pauseButton;
    }

    private Button createStopButton(String text, String styleClass) {
        Button stopButton = new Button();
        stopButton.getStyleClass().add(styleClass);
        stopButton.setMinSize(40, 40); // Circular size
        stopButton.setMaxSize(40, 40);
        stopButton.setShape(new Circle(20));

        // Handle the click event of the button
        stopButton.setOnAction(event -> {
            if (song != null) {
                // Check if the song is currently playing
                if (stopButton.getStyleClass().contains("stopped")) {
                    song.play();
                    stopButton.getStyleClass().remove("stopped");
                } else {
                    song.stop();
                    stopButton.getStyleClass().add("stopped");
                    updateSongNameLabel(song.getTitle());
                }
            }
        });

        return stopButton;
    }

    private void updateSongNameLabel(String songName) {
        songNameLabel.setText(songName);
    }
}
