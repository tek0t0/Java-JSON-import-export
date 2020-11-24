package softuni.cardealer.domain.dtos.importDtos;

import com.google.gson.annotations.Expose;

public class CarSeedDto {

    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private long travelledDistance;

    public CarSeedDto() {
    }

    public CarSeedDto(String make, String model, long travelledDistance) {
        this.make = make;
        this.model = model;
        this.travelledDistance = travelledDistance;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
