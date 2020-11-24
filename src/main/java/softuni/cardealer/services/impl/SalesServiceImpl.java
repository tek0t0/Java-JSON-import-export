package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.exportDtos.SaleExportTithCarDto;
import softuni.cardealer.domain.entities.Car;
import softuni.cardealer.domain.entities.Customer;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.entities.Sale;
import softuni.cardealer.domain.repositories.CarRepo;
import softuni.cardealer.domain.repositories.CustomerRepo;
import softuni.cardealer.domain.repositories.SalesRepo;
import softuni.cardealer.services.SaleService;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SalesServiceImpl implements SaleService {
    private final SalesRepo salesRepo;
    private final CarRepo carRepo;
    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SalesServiceImpl(SalesRepo salesRepo, CarRepo carRepo, CustomerRepo customerRepo, ModelMapper modelMapper, Gson gson) {
        this.salesRepo = salesRepo;
        this.carRepo = carRepo;
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSales() {
        if (this.salesRepo.findAll().size() > 0) {
        } else {
            Sale sale = new Sale();
            sale.setCar(getRandomCar());
            sale.setCustomer(getRandomCustomer());
            sale.setDiscount(getRandomDiscount());

            Sale sale1 = new Sale();
            sale1.setCar(getRandomCar());
            sale1.setCustomer(getRandomCustomer());
            sale1.setDiscount(getRandomDiscount());

            Sale sale2 = new Sale();
            sale2.setCar(getRandomCar());
            sale2.setCustomer(getRandomCustomer());
            sale2.setDiscount(getRandomDiscount());

            this.salesRepo.saveAndFlush(sale);
            this.salesRepo.saveAndFlush(sale1);
            this.salesRepo.saveAndFlush(sale2);
        }
    }

    @Override
    public String getAllSales() {
        Set<Sale> sales = this.salesRepo.getAllBy();
        Set<SaleExportTithCarDto> toExport = new LinkedHashSet<>();

        for (Sale sale : sales) {
            SaleExportTithCarDto saleExportTithCarDto = this.modelMapper.map(sale, SaleExportTithCarDto.class);
            saleExportTithCarDto.setDiscount(saleExportTithCarDto.getDiscount() / 100);
            BigDecimal price = new BigDecimal("0");
            Set<Part> parts = sale.getCar().getParts();
            for (Part part : parts) {
                price = price.add(part.getPrice());
            }
            saleExportTithCarDto.setPrice(price);
            saleExportTithCarDto.setPriceWithDiscount(saleExportTithCarDto.getPrice()
                    .multiply(BigDecimal.valueOf(1 - saleExportTithCarDto.getDiscount())));
            toExport.add(saleExportTithCarDto);
        }
        return this.gson.toJson(toExport.toArray());
    }

    private int getRandomDiscount() {
        List<Integer> discounts = Arrays.asList(0, 5, 10, 15, 20, 30, 40, 50);
        Random random = new Random();
        return discounts.get(random.nextInt(discounts.size()));
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.customerRepo.count()) + 1;
        return this.customerRepo.findById(id).get();
    }

    private Car getRandomCar() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.carRepo.count()) + 1;
        return this.carRepo.findById(id).get();
    }


}
