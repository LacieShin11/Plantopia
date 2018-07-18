package plantopia.sungshin.plantopia;

import java.util.List;

import plantopia.sungshin.plantopia.PostItem;

public class PostData {
    private String title;
    private String source;
    private List<PostItem> postItems;

    public PostData() {
    }

    public PostData(String title, String source, List<PostItem> postItems) {
        this.title = title;
        this.source = source;
        this.postItems = postItems;
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

    public List<PostItem> getPostItems() {
        return postItems;
    }

    public void setPostItems(List<PostItem> postItems) {
        this.postItems = postItems;
    }
}
