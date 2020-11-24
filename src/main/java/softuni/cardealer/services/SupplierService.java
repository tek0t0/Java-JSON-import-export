package softuni.cardealer.services;

import java.io.IOException;

public interface SupplierService {
    void seedSuppliers() throws IOException;

    String getAllLocalSuppliers();
}
