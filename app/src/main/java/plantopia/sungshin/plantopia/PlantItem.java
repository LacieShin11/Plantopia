package plantopia.sungshin.plantopia;

public class PlantItem {
    private String plantName;
    private String plantType;
    private String plantImg;
    //private double maxTemp, minTemp, maxLight, minLignt, maxHumidity, minHumidity;
    private Double Temp, Light, Humidity;

   /* public double getMaxTemp() {
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
*/
    public PlantItem(String plantName, String plantType, String plantImg, double Temp, double Light, double Humidity){//double maxTemp, double minTemp, double maxLight, double minLignt, double maxHumidity, double minHumidity) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantImg = plantImg;
        this.Temp = Temp;
        this.Light = Light;
        this.Humidity = Humidity;
        /*this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.maxLight = maxLight;
        this.minLignt = minLignt;
        this.maxHumidity = maxHumidity;
        this.minHumidity = minHumidity;*/
    }

    public PlantItem(String plantName, String plantType, String plantImg) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantImg = plantImg;
    }

    public double getTemp(){
        return Temp;
    }

    public double getLight(){
        return Light;
    }

    public double getHumidity(){
        return Humidity;
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
