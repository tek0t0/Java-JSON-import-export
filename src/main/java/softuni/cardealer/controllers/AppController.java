package softuni.cardealer.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.cardealer.services.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class AppController implements CommandLineRunner {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public AppController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.supplierService.seedSuppliers();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomers();
        this.saleService.seedSales();

        System.out.println("Select query from Car Dealer Ex");
        System.out.println("1.Ordered Customers");
        System.out.println("2.Cars from Make Toyota");
        System.out.println("3.Local Suppliers");
        System.out.println("4.Cars with Their List of Parts");
        System.out.println("5.Total Sales by Customer");
        System.out.println("6.Sales with Applied Discount");
        System.out.println("0.Exit");
        String input = reader.readLine();
        while (true) {
            switch (input) {
                case "1":
                    System.out.println(this.customerService.orderedCustomers());
                    break;
                case "2":
                    System.out.println(this.carService.findByToyota());
                    break;
                case "3":
                    System.out.println(this.supplierService.getAllLocalSuppliers());
                    break;
                case "4":
                    System.out.println(this.carService.getCarsWithParts());
                    break;
                case "5":
                    System.out.println(this.customerService.getAllCustomersByTotalSales());
                    break;
                case "6":
                    System.out.println(this.saleService.getAllSales());
                    break;
                case "0":
                    System.out.println("Thank You and Good Luck!");
                    System.exit(0);
                    break;
            }
            System.out.println("Select query from Car Dealer Ex");
            input = reader.readLine();
        }
    }
}
