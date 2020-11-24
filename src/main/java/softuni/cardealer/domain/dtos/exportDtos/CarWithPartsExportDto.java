package softuni.cardealer.domain.dtos.exportDtos;


import com.google.gson.annotations.Expose;

import java.util.Set;

public class CarWithPartsExportDto {

    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private long travelledDistance;

    @Expose
    private Set<PartsExportDto> parts;


    public CarWithPartsExportDto() {
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

    public Set<PartsExportDto> getParts() {
        return parts;
    }

    public void setParts(Set<PartsExportDto> parts) {
        this.parts = parts;
    }
}
