package plantopia.sungshin.plantopia.Home;

public class DIYItem {
    private String url, title, source, image;

    public DIYItem() {
    }

    public DIYItem(String url, String title, String source, String image) {
        this.url = url;
        this.title = title;
        this.source = source;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
