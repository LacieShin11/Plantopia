package plantopia.sungshin.plantopia;

import java.io.Serializable;

public class PlantItem implements Serializable{
    private int owner_id, plant_id;
    private String plant_name;
    private String plant_type;
    private String plantNumber; //xml에서 구분하는 데 필요한, 식물 구분 번호
    private String plant_img;
    private double winterMinTemp, temp_max, temp_min, light_max, light_min, humidity_max, humidity_min, temp, light, humidity;
    private int plant_connect;

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public int getPlant_connect() {
        return plant_connect;
    }

    public int isPlant_connect() {
        return plant_connect;
    }

    public void setPlant_connect(int plant_connect) {
        this.plant_connect = plant_connect;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    //temp, light, Humidity는 현재 식물의 정보
    public double getTemp(){
        return temp;
    }

    public double getLight(){
        return light;
    }

    public double getHumidity(){
        return humidity;
    }

    public void setTemp(double Temp){
        this.temp = Temp;
    }

    public void setLight(double Light){
        this.light = Light;
    }

    public void setHumidity(double Humidity){
        this.humidity = Humidity;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getLight_max() {
        return light_max;
    }

    public void setLight_max(double light_max) {
        this.light_max = light_max;
    }

    public double getLight_min() {
        return light_min;
    }

    public void setLight_min(double light_min) {
        this.light_min = light_min;
    }

    public double getHumidity_max() {
        return humidity_max;
    }

    public void setHumidity_max(double humidity_max) {
        this.humidity_max = humidity_max;
    }

    public double getHumidity_min() {
        return humidity_min;
    }

    public void setHumidity_min(double humidity_min) {
        this.humidity_min = humidity_min;
    }

    public String getPlantNumber() {
        return plantNumber;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public void setWinterMinTemp(double winterMinTemp) {
        this.winterMinTemp = winterMinTemp;
    }

    public double getWinterMinTemp() {
        return winterMinTemp;
    }

    public PlantItem() {
    }

    public PlantItem(String plantName, String plantType, String plantImg, double Temp, double Light, double Humidity){
        this.plant_name = plantName;
        this.plant_type = plantType;
        this.plant_img = plantImg;
        this.temp = Temp;
        this.light = Light;
        this.humidity = Humidity;
    }//챗봇용 생성자

    public PlantItem(String plantName, String plantNumber) {
        this.plant_name = plantName;
        this.plantNumber = plantNumber;
    }

    public PlantItem(int ownerID, String plantName, String plantType, String plantNumber, String plantImg, double winterMinTemp, double maxTemp, double minTemp, double maxLight, double minLignt, double maxHumidity, double minHumidity, double temp, double light, double humidity, int isConnected) {
        this.owner_id = ownerID;
        this.plant_name = plantName;
        this.plant_type = plantType;
        this.plantNumber = plantNumber;
        this.plant_img = plantImg;
        this.winterMinTemp = winterMinTemp;
        this.temp_max = maxTemp;
        this.temp_min = minTemp;
        this.light_max = maxLight;
        this.light_min = minLignt;
        this.humidity_max = maxHumidity;
        this.humidity_min = minHumidity;
        this.temp = temp;
        this.light = light;
        this.humidity = humidity;
        this.plant_connect = isConnected;
    }

    public PlantItem(String plantName, String plantType, String plantNumber, String plantImg) {
        this.plant_name = plantName;
        this.plant_type = plantType;
        this.plantNumber = plantNumber;
        this.plant_img = plantImg;
    }

    public PlantItem(String plantName, String plantNumber, String plantImg) {
        this.plant_name = plantName;
        this.plantNumber = plantNumber;
        this.plant_img = plantImg;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public String getPlant_img() {
        return plant_img;
    }

    public void setPlant_img(String plant_img) {
        this.plant_img = plant_img;
    }

    public String getPlant_type() {
        return plant_type;
    }

    public void setPlant_type(String plant_type) {
        this.plant_type = plant_type;
    }

    @Override
    public String toString() {
        return plant_name;
    }
}
