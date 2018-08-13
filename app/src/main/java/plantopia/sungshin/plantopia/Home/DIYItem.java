package plantopia.sungshin.plantopia.Home;

public class DIYItem {
    private String url, title, source;

    public DIYItem() {
    }

    public DIYItem(String url, String title, String source) {
        this.url = url;
        this.title = title;
        this.source = source;
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
}
