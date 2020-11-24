package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.exportDtos.SupplierExportDto;
import softuni.cardealer.domain.dtos.importDtos.SupplierSeedDto;
import softuni.cardealer.domain.entities.Supplier;
import softuni.cardealer.domain.repositories.SupplierRepo;
import softuni.cardealer.services.SupplierService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final static String SUPPLIERS_PATH = "src/main/resources/jsons/suppliers.json";
    private final SupplierRepo supplierRepo;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SupplierServiceImpl(SupplierRepo supplierRepo, ModelMapper modelMapper, Gson gson) {
        this.supplierRepo = supplierRepo;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (this.supplierRepo.findAll().size() > 0) {
        } else {
            //Read JSON
            String content = String.join("", Files.readAllLines(Path.of(SUPPLIERS_PATH)));
            //JSON->DTO
            SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);
            //DTO->DB
            for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                this.supplierRepo.saveAndFlush(supplier);
            }
        }
    }

    @Override
    public String getAllLocalSuppliers() {
        Set<Supplier> allByImporterIsTrue = this.supplierRepo.findAllByImporterIsFalseOrderById();
        Set<SupplierExportDto> supplierExportDtoSet = new LinkedHashSet<>();

        for (Supplier supplier : allByImporterIsTrue) {
            SupplierExportDto supplierExportDto = this.modelMapper.map(supplier, SupplierExportDto.class);
            supplierExportDto.setPartsCount(supplier.getParts().size());
            supplierExportDtoSet.add(supplierExportDto);
        }
        return this.gson.toJson(supplierExportDtoSet.toArray());
    }
}
