package plantopia.sungshin.plantopia;

public class PlantItem {
    private String plantName;
    private String plantType;
    private int plantImg;

    public PlantItem(String plantName, String plantType, int plantImg) {
        this.plantName = plantName;
        this.plantType = plantType;
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

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }
}
