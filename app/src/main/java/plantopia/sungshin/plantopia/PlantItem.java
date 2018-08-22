package plantopia.sungshin.plantopia;

public class PlantItem {
    private String plantName;
    private String plantType;
    private String plantNumber;
    private String plantImg;
    private double maxTemp, minTemp, maxLight, minLignt, maxHumidity, minHumidity;

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxLight() {
        return maxLight;
    }

    public void setMaxLight(double maxLight) {
        this.maxLight = maxLight;
    }

    public double getMinLignt() {
        return minLignt;
    }

    public void setMinLignt(double minLignt) {
        this.minLignt = minLignt;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public String getPlantNumber() {
        return plantNumber;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public PlantItem() {
    }

    public PlantItem(String plantName, String plantNumber) {
        this.plantName = plantName;
        this.plantNumber = plantNumber;
    }

    public PlantItem(String plantName, String plantType, String plantImg, double maxTemp, double minTemp, double maxLight, double minLignt, double maxHumidity, double minHumidity) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantImg = plantImg;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.maxLight = maxLight;
        this.minLignt = minLignt;
        this.maxHumidity = maxHumidity;
        this.minHumidity = minHumidity;
    }

    public PlantItem(String plantName, String plantType, String plantNumber, String plantImg) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantNumber = plantNumber;
        this.plantImg = plantImg;
    }

    public PlantItem(String plantName, String plantNumber, String plantImg) {
        this.plantName = plantName;
        this.plantNumber = plantNumber;
        this.plantImg = plantImg;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantImg() {
        return plantImg;
    }

    public void setPlantImg(String plantImg) {
        this.plantImg = plantImg;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }
}
