package softuni.cardealer.domain.dtos.exportDtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartsExportDto {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    public PartsExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
