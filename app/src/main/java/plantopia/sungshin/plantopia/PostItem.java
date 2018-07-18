package plantopia.sungshin.plantopia;

public class PostItem {
    private String url, title, source, img;

    public PostItem() {
    }

    public PostItem(String url, String title, String source, String img) {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
