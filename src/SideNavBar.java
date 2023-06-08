import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class SideNavBar extends VBox {
    private Button homeButton;
    private Button playlistButton;
    private Button settingsButton;

    private SongCollection songCollection;


    // Constructor
    public SideNavBar(SongCollection songCollection) {
        initializeComponents();
        setupLayout();
        this.songCollection = songCollection;
    }

    public SongCollection getSongCollection() {
        return songCollection;
    }

    // Initialize the components
    private void initializeComponents() {
        homeButton = new Button("Home");
        playlistButton = new Button("Playlist");
        settingsButton = new Button("Settings");

        homeButton.getStyleClass().add("jfx-button");
        playlistButton.getStyleClass().add("jfx-button");
        settingsButton.getStyleClass().add("jfx-button");

        // Add event handlers for navigation actions
        homeButton.setOnAction(event -> {
            // Handle Home button click
            // Implement your logic here
            System.out.println("Home button clicked");
        });

        playlistButton.setOnAction(event -> {
            // Handle Playlist button click
            Stage primaryStage = (Stage) getScene().getWindow();


            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select a folder containing songs");

            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                String folderPath = selectedDirectory.getAbsolutePath();
                // Implement logic to retrieve song locations from the selected folder
                // You can iterate over the files in the folder and extract their locations
                File[] files = selectedDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            String songLocation = file.getAbsolutePath();
                            System.out.println("Song location: " + songLocation);
                            //"file:///E:/1_wedding_songs/english_songs/Ed_Sheeran_Perfect.mp3"
                            Song song = new Song("testTitle","testArtist", convertToUrlFormat(songLocation));
                            songCollection.addSong(song);
                            System.out.println("done");
                        }
                    }
                }
            }
        });

        settingsButton.setOnAction(event -> {
            // Handle Settings button click
            // Implement your logic here
            System.out.println("Settings button clicked");
        });
    }

    // Setup the layout
    private void setupLayout() {
        setPadding(new Insets(10));
        setSpacing(10);
        setAlignment(Pos.TOP_LEFT);

        getChildren().addAll(homeButton, playlistButton, settingsButton);
    }

    public static String convertToUrlFormat(String filePath) {
        String convertedPath = filePath.replace("\\", "/");
        convertedPath = "file:///" + convertedPath;
        return convertedPath;
    }


}
