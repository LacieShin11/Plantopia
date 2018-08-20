package plantopia.sungshin.plantopia.Home;

public class ProductItem {
    private String name, price, url, image;

    public ProductItem() {
    }

    public ProductItem(String name, String price, String url, String image) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
