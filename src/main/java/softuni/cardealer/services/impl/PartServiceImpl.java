package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import softuni.cardealer.domain.dtos.importDtos.PartSeedDto;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.entities.Supplier;
import softuni.cardealer.domain.repositories.PartRepo;
import softuni.cardealer.domain.repositories.SupplierRepo;
import softuni.cardealer.services.PartService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private final static String PARTS_PATH = "src/main/resources/jsons/parts.json";
    private final PartRepo partRepo;
    private final SupplierRepo supplierRepo;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public PartServiceImpl(PartRepo partRepo, SupplierRepo supplierRepo, ModelMapper modelMapper, Gson gson) {
        this.partRepo = partRepo;
        this.supplierRepo = supplierRepo;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedParts() throws Exception {
        if (this.partRepo.findAll().size() > 0) {
        } else {
            //Read JSON
            String content = String.join("", Files.readAllLines(Path.of(PARTS_PATH)));
            //JSON->DTO
            PartSeedDto[] partSeedDtos = this.gson.fromJson(content, PartSeedDto[].class);
            //DTO->DB
            for (PartSeedDto partSeedDto : partSeedDtos) {
                Part part = this.modelMapper.map(partSeedDto, Part.class);
                part.setSupplier(getRandomSupplier());
                this.partRepo.saveAndFlush(part);
            }
        }
    }

    private Supplier getRandomSupplier() throws Exception {
        Random random = new Random();
        long index = (long) random.nextInt((int) this.supplierRepo.count()) + 1;
        Optional<Supplier> supplier = this.supplierRepo.findById(index);
        if (supplier.isPresent()) {
            return supplier.get();
        } else {
            throw new Exception("Supplier dont exists");
        }
    }
}
