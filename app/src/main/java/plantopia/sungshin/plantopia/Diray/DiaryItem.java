package plantopia.sungshin.plantopia.Diray;

public class DiaryItem {
    private int owner_id, diary_id;
    private String diary_img, diary_content, diary_date;

    public DiaryItem() {
    }

    public DiaryItem(int owner_id, String diary_img, String diary_content, String diary_date) {
        this.owner_id = owner_id;
        this.diary_img = diary_img;
        this.diary_content = diary_content;
        this.diary_date = diary_date;
    }

    public int getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(int diary_id) {
        this.diary_id = diary_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getDiary_img() {
        return diary_img;
    }

    public void setDiary_img(String diary_img) {
        this.diary_img = diary_img;
    }

    public String getDiary_content() {
        return diary_content;
    }

    public void setDiary_content(String diary_content) {
        this.diary_content = diary_content;
    }

    public String getDiary_date() {
        return diary_date;
    }

    public void setDiary_date(String diary_date) {
        this.diary_date = diary_date;
    }
}
