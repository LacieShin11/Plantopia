package plantopia.sungshin.plantopia.User;

public class Count {
    private int potCount;
    private int diaryCount;
    private int scrapCount;

    public Count(int potCount, int diaryCount, int scrapCount) {
        this.potCount = potCount;
        this.diaryCount = diaryCount;
        this.scrapCount = scrapCount;
    }

    public int getPotCount() {
        return potCount;
    }

    public void setPotCount(int potCount) {
        this.potCount = potCount;
    }

    public int getDiaryCount() {
        return diaryCount;
    }

    public void setDiaryCount(int diaryCount) {
        this.diaryCount = diaryCount;
    }

    public int getScrapCount() {
        return scrapCount;
    }

    public void setScrapCount(int scrapCount) {
        this.scrapCount = scrapCount;
    }
}
