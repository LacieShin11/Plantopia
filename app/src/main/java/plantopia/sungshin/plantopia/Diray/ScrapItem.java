package plantopia.sungshin.plantopia.Diray;

public class ScrapItem {
    private int owner_id, scrap_id;
    private String scrap_title, scrap_source, scrap_img, scrap_url;

    public ScrapItem() {
    }

    public ScrapItem(int owner_id, String scrap_title, String scrap_source, String scrap_img, String scrap_url) {
        this.owner_id = owner_id;
        this.scrap_title = scrap_title;
        this.scrap_source = scrap_source;
        this.scrap_img = scrap_img;
        this.scrap_url = scrap_url;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setScrap_id(int scrap_id) {
        this.scrap_id = scrap_id;
    }

    public void setScrap_title(String scrap_title) {
        this.scrap_title = scrap_title;
    }

    public void setScrap_source(String scrap_source) {
        this.scrap_source = scrap_source;
    }

    public void setScrap_img(String scrap_img) {
        this.scrap_img = scrap_img;
    }

    public void setScrap_url(String scrap_url) {
        this.scrap_url = scrap_url;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public int getScrap_id() {
        return scrap_id;
    }

    public String getScrap_title() {
        return scrap_title;
    }

    public String getScrap_source() {
        return scrap_source;
    }

    public String getScrap_img() {
        return scrap_img;
    }

    public String getScrap_url() {
        return scrap_url;
    }
}