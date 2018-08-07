package plantopia.sungshin.plantopia;

public class PlantItem {
    private String plantName;
    private int plantImg;

    public PlantItem(String plantName, int plantImg) {
        this.plantName = plantName;
        this.plantImg = plantImg;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public int getPlantImg() {
        return plantImg;
    }

    public void setPlantImg(int plantImg) {
        this.plantImg = plantImg;
    }
}
