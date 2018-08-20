package plantopia.sungshin.plantopia;

public class Plant {
    private String plantName;
    private String plantNumber;

    Plant(String plantName, String plantNumber)
    {
        this.plantName = plantName;
        this.plantNumber = plantNumber;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getPlantNumber() {
        return plantNumber;
    }
}
