package plantopia.sungshin.plantopia.Diray;

public class DiaryItem {
    private int ownerID;
    private String diaryImg, diaryContent, diaryDate;

    public DiaryItem() {
    }

    public DiaryItem(int ownerID, String diaryImg, String diaryContent, String diaryDate) {
        this.ownerID = ownerID;
        this.diaryImg = diaryImg;
        this.diaryContent = diaryContent;
        this.diaryDate = diaryDate;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getDiaryImg() {
        return diaryImg;
    }

    public void setDiaryImg(String diaryImg) {
        this.diaryImg = diaryImg;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public String getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(String diaryDate) {
        this.diaryDate = diaryDate;
    }
}
