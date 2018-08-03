package plantopia.sungshin.plantopia;

public class MusicListItem {
    private String songTitle;
    private String artist;
    private String songLength;

    public MusicListItem() {
    }

    public MusicListItem(String songTitle, String artist, String songLength) {
        this.songTitle = songTitle;
        this.artist = artist;
        this.songLength = songLength;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }
}
