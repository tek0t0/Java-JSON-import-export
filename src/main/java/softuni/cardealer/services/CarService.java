package softuni.cardealer.services;

import java.io.IOException;

public interface CarService {
    void seedCars() throws Exception;

    String findByToyota();

    String getCarsWithParts();
}
