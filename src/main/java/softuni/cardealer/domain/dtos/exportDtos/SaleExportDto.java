package softuni.cardealer.domain.dtos.exportDtos;

import com.google.gson.annotations.Expose;
import softuni.cardealer.domain.entities.Customer;

public class SaleExportDto {

    @Expose
    private int discount;

    @Expose
    private CarExportDto car;

    private Customer customer;

    public SaleExportDto() {
    }


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public CarExportDto getCar() {
        return car;
    }

    public void setCar(CarExportDto car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
