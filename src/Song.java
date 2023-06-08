import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaException;



public class Song {
    private String title;
    private String artist;
    private String filePath;
    private MediaPlayer mediaPlayer;
    private Media media;

    public Song(String title, String artist, String filePath) {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
        try {
            System.out.println(filePath);
            this.media = new Media(filePath);
            this.mediaPlayer = new MediaPlayer(media);

            System.out.println(media.getWidth());


        } catch (MediaException e) {
            System.out.println("Failed to create MediaPlayer: " + e.getMessage());
            // Handle the exception appropriately, such as displaying an error message or taking alternative actions
        }
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getFilePath() {
        return filePath;
    }

    public void play() {
        // Add code here to play the song
        mediaPlayer.play();
        System.out.println("Playing: " + title + " - " + artist);
    }

    public void stop() {
        // Add code here to stop the song
        mediaPlayer.stop();
        System.out.println("Stopping: " + title + " - " + artist);
    }

    public void pause() {
        // Add code here to play the song
        mediaPlayer.pause();
        System.out.println("Pausing: " + title + " - " + artist);
    }
}
