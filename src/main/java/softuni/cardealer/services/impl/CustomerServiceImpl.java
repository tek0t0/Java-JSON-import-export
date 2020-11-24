package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.exportDtos.CustomerExportDto;
import softuni.cardealer.domain.dtos.exportDtos.CustomersWithNumberOfCarsAndMoneySpent;
import softuni.cardealer.domain.dtos.importDtos.CustomerSeedDto;
import softuni.cardealer.domain.entities.Customer;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.entities.Sale;
import softuni.cardealer.domain.repositories.CustomerRepo;
import softuni.cardealer.services.CustomerService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final static String CUSTOMERS_PATH = "src/main/resources/jsons/customers.json";
    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo, ModelMapper modelMapper, Gson gson) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedCustomers() throws IOException {
        if (this.customerRepo.findAll().size() > 0) {
        } else {
            String content = String.join("", Files.readAllLines(Path.of(CUSTOMERS_PATH)));
            CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content, CustomerSeedDto[].class);
            for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
                Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
                this.customerRepo.saveAndFlush(customer);
            }
        }
    }

    @Override
    public String orderedCustomers() {
        Set<Customer> allOrderByBirthDateAndYoungDriver = this.customerRepo.getAllOrderByBirthDateAndYoungDriver();
        Set<CustomerExportDto> toExport = new LinkedHashSet<>();
        for (Customer customer : allOrderByBirthDateAndYoungDriver) {
            CustomerExportDto customerExportDto = this.modelMapper.map(customer, CustomerExportDto.class);
            toExport.add(customerExportDto);
        }
        return this.gson.toJson(toExport.toArray());
    }

    @Override
    public String getAllCustomersByTotalSales() {
        Set<Customer> customersByCars = this.customerRepo.findCustomersByCars();
        Set<CustomersWithNumberOfCarsAndMoneySpent> toExport = new LinkedHashSet<>();
        for (Customer customerByCar : customersByCars) {
            CustomersWithNumberOfCarsAndMoneySpent customer = new CustomersWithNumberOfCarsAndMoneySpent();
            customer.setFullName(customerByCar.getName());
            customer.setBoughtCars(customerByCar.getSales().size());

            BigDecimal spentMoney = new BigDecimal("0");
            Set<Sale> sales = customerByCar.getSales();
            for (Sale sale : sales) {
                Set<Part> parts = sale.getCar().getParts();
                for (Part part : parts) {
                    spentMoney = spentMoney.add(part.getPrice());
                }
            }
            customer.setSpentMoney(spentMoney);
            toExport.add(customer);
        }
        return this.gson.toJson(toExport.toArray());
    }
}
