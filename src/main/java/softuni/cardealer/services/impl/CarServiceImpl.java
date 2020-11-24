package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.exportDtos.CarExportDto;
import softuni.cardealer.domain.dtos.exportDtos.CarWithPartsExportDto;
import softuni.cardealer.domain.dtos.importDtos.CarSeedDto;

import softuni.cardealer.domain.entities.Car;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.repositories.CarRepo;
import softuni.cardealer.domain.repositories.PartRepo;
import softuni.cardealer.services.CarService;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {
    private final static String CARS_PATH = "src/main/resources/jsons/cars.json";
    private final CarRepo carRepo;
    private final PartRepo partRepo;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepo carRepo, PartRepo partRepo, Gson gson, ModelMapper modelMapper) {

        this.carRepo = carRepo;
        this.partRepo = partRepo;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void seedCars() throws Exception {
        if (this.carRepo.findAll().size() > 0) {
        } else {
            String content = String.join("", Files.readAllLines(Path.of(CARS_PATH)));
            CarSeedDto[] carSeedDtos = this.gson.fromJson(content, CarSeedDto[].class);
            for (CarSeedDto carSeedDto : carSeedDtos) {
                Car car = this.modelMapper.map(carSeedDto, Car.class);
                car.setParts(getRandomParts());
                this.carRepo.saveAndFlush(car);
            }
        }
    }

    @Override
    public String findByToyota() {
        Set<Car> toyotaCars = this.carRepo.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        Set<CarExportDto> carExportDtoSet = new LinkedHashSet<>();

        for (Car car : toyotaCars) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);
            carExportDtoSet.add(carExportDto);
        }
        return this.gson.toJson(carExportDtoSet.toArray());
    }

    @Override
    public String getCarsWithParts() {
        List<Car> allCars = this.carRepo.findAll();
        List<CarWithPartsExportDto> toExport = new ArrayList<>();
        for (Car car : allCars) {
            CarWithPartsExportDto carWithPartsExportDto = this.modelMapper.map(car, CarWithPartsExportDto.class);
            toExport.add(carWithPartsExportDto);
        }
        return this.gson.toJson(toExport.toArray());
    }

    private Set<Part> getRandomParts() throws Exception {
        Set<Part> parts = new HashSet<>();
        int num = getRandomPartCount();
        for (int i = 10; i < num; i++) {
            Part part = this.getRandomPart();
            parts.add(part);
        }
        return parts;
    }

    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long index = (long) random.nextInt((int) this.partRepo.count()) + 1;
        Optional<Part> part = this.partRepo.findById(index);
        if (part.isPresent()) {
            return part.get();
        } else {
            throw new Exception("Part dont exists");
        }

    }

    private int getRandomPartCount() {
        Random random = new Random();
        return random.nextInt(10) + 11;
    }
}
