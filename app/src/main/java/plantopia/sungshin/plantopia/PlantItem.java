package plantopia.sungshin.plantopia;

public class PlantItem {
    private int ownerID;
    private String plantName;
    private String plantType;
    private String plantNumber; //xml에서 구분하는 데 필요한, 식물 구분 번호
    private String plantImg;
    private double maxTemp, minTemp, maxLight, minLignt, maxHumidity, minHumidity, Temp, Light, Humidity;

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    //Temp, Light, Humidity는 현재 식물의 정보
    public double getTemp(){
        return Temp;
    }

    public double getLight(){
        return Light;
    }

    public double getHumidity(){
        return Humidity;
    }

    public void setTemp(double Temp){
        this.Temp = Temp;
    }

    public void setLight(double Light){
        this.Light = Light;
    }

    public void setHumidity(double Humidity){
        this.Humidity = Humidity;
    }

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

    public PlantItem(String plantName, String plantType, String plantImg, double Temp, double Light, double Humidity){
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantImg = plantImg;
        this.Temp = Temp;
        this.Light = Light;
        this.Humidity = Humidity;
    }//챗봇용 생성자

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
