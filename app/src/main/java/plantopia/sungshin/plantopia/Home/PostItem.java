package plantopia.sungshin.plantopia.Home;

public class PostItem {
    private String url, title, source;
    private int img;

    public PostItem() {
    }

    public PostItem(String url, String title, String source, int img) {
        this.url = url;
        this.title = title;
        this.source = source;
        this.img = img;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}