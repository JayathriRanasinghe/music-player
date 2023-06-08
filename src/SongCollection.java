import java.util.ArrayList;
import java.util.List;

public class SongCollection {
    private List<Song> songs;

    public SongCollection(List<Song> songList) {
        this.songs =songList;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public List<Song> getAllSongs() {
        return songs;
    }

    public List<Song> getSongsByArtist(String artist) {
        List<Song> matchingSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getArtist().equals(artist)) {
                matchingSongs.add(song);
            }
        }
        return matchingSongs;
    }

    public List<Song> getSongsByGenre(String genre) {
        List<Song> matchingSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getGenre().equals(genre)) {
                matchingSongs.add(song);
            }
        }
        return matchingSongs;
    }

    // Other methods for searching, sorting, etc. can be added here
}
