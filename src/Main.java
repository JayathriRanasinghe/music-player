import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private ProgressBar progressBar;
    private Song song;
    private Label songNameLabel;
    private List<Song> songList;
    private boolean isPlaying;
    private int currentSongIndex =0;
    private DoubleProperty progressProperty;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        isPlaying = false;

        primaryStage.setTitle("MusicPlayer");

        // Load the custom icon image
        Image iconImage = new Image(getClass().getResourceAsStream("music-icon.png"));

//        song = new Song("Perfect", "Ed Sheeran", "file:///E:/1_wedding_songs/english_songs/Ed_Sheeran_Perfect.mp3");

        // Set the custom icon for the stage
        primaryStage.getIcons().add(iconImage);

        songList = new ArrayList<>();

        SongCollection songCollection = new SongCollection(songList);
        // Create the side navigation bar
        SideNavBar sideNavBar = new SideNavBar(songCollection);


        System.out.println(songCollection);

        // Iterate through the list of songs using a loop


        // Add the side navigation bar to the main layout
        // Replace this with your own layout implementation
        // Example:
        // BorderPane mainLayout = new BorderPane();
        // mainLayout.setLeft(sideNavBar);

        //Scene scene = new Scene(sideNavBar, 800, 600);

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

        progressProperty = new SimpleDoubleProperty(0);
        progressBar.progressProperty().bind(progressProperty);

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
        topSection.getChildren().addAll(songNameLabel,sideNavBar);
        topSection.getStyleClass().add("top-section");

        // Create the bottom section with the button container
        StackPane bottomSection = new StackPane();
        bottomSection.getChildren().add(section);
        bottomSection.getStyleClass().add("bottom-section");

        // Create the SplitPane and set the orientation
        SplitPane splitPane = new SplitPane(topSection, bottomSection);
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        splitPane.setDividerPositions(0.5); // Set the initial divider position

        splitPane.getStyleClass().add("both-sections");
        splitPane.prefWidthProperty().bind(primaryStage.widthProperty());
        splitPane.prefHeightProperty().bind(primaryStage.heightProperty());

        VBox layout = new VBox();
        layout.prefHeightProperty().bind(primaryStage.heightProperty());
        layout.getChildren().addAll(splitPane);

        VBox.setVgrow(splitPane, javafx.scene.layout.Priority.ALWAYS);
        layout.setPadding(new Insets(0));
        layout.getStyleClass().add("container");


        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Apply CSS styles
        primaryStage.setScene(scene);
        // Bind the width of the progress bar to the width of the scene
        progressBar.prefWidthProperty().bind(Bindings.subtract(scene.widthProperty(), 20));
        primaryStage.show();
    }

    private void playSongs(DoubleProperty progressProperty) {
        Song currentSong;

        if (currentSongIndex < songList.size() && currentSongIndex >= 0) {

            currentSong = songList.get(currentSongIndex);
            song = currentSong;
            // Set the event handlers
            currentSong.getMediaPlayer().setOnPlaying(() -> System.out.println("Now playing: " + currentSong.getTitle()));
            currentSong.getMediaPlayer().setOnEndOfMedia(() -> {
                System.out.println("Finished playing: " + currentSong.getTitle());
                currentSongIndex++;
                playSongs(progressProperty);
            });

            // Update progressProperty based on current time and total duration
            currentSong.getMediaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue) ->
                    progressProperty.setValue(newValue.toSeconds() / currentSong.getMedia().getDuration().toSeconds()));


            currentSong.play();
        }
    }

    private void playSong(Song song) {
        song.play();
        System.out.println("in playsong!");
    }

    private void waitUntilSongEnds() {
        while (isPlaying) {
            try {
                Thread.sleep(100); // Wait for a short duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Button createPlayButton(String text, String styleClass) {
        Button playButton = new Button();
        playButton.getStyleClass().add(styleClass);
        playButton.setMinSize(40, 40); // Circular size
        playButton.setMaxSize(40, 40);
        playButton.setShape(new Circle(20));

        // Handle the click event of the button
        playButton.setOnAction(event -> {
            if (songList.size() != 0) {
                // Check if the song is currently playing
                if (playButton.getStyleClass().contains("playing")) {
                    song.pause();
                    playButton.getStyleClass().remove("playing");
                } else {
                    playSongs(progressProperty);
                    playButton.getStyleClass().add("playing");
                    updateSongNameLabel(song.getTitle());
                }
            }else{
                updateSongNameLabel("Select your playlist");
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

    private Button createForwardButton(String text, String styleClass) {
        Button forwardButton = new Button();
        forwardButton.getStyleClass().add(styleClass);
        forwardButton.setMinSize(40, 40); // Circular size
        forwardButton.setMaxSize(40, 40);
        forwardButton.setShape(new Circle(20));

        // Handle the click event of the button
        forwardButton.setOnAction(event -> {
            if (song != null) {
                // Check if the song is currently playing
                if (forwardButton.getStyleClass().contains("paused")) {
                    song.play();
                    forwardButton.getStyleClass().remove("paused");
                } else {
                    song.pause();
                    forwardButton.getStyleClass().add("paused");
                    updateSongNameLabel(song.getTitle());
                }
            }
        });

        return forwardButton;
    }

    private Button createBackwardButton(String text, String styleClass) {
        Button backwardButton = new Button();
        backwardButton.getStyleClass().add(styleClass);
        backwardButton.setMinSize(40, 40); // Circular size
        backwardButton.setMaxSize(40, 40);
        backwardButton.setShape(new Circle(20));

        // Handle the click event of the button
        backwardButton.setOnAction(event -> {
            if (song != null) {
                // Check if the song is currently playing
                if (backwardButton.getStyleClass().contains("paused")) {
                    song.play();
                    backwardButton.getStyleClass().remove("paused");
                } else {
                    song.pause();
                    backwardButton.getStyleClass().add("paused");
                    updateSongNameLabel(song.getTitle());
                }
            }
        });

        return backwardButton;
    }
    // Handle navigation actions from the side navigation bar


    private void updateSongNameLabel(String songName) {
        songNameLabel.setText(songName);
    }
}
