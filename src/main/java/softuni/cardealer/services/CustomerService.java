package softuni.cardealer.services;

import java.io.IOException;

public interface CustomerService {
    void seedCustomers() throws IOException;

    String orderedCustomers();

    String getAllCustomersByTotalSales();
}
