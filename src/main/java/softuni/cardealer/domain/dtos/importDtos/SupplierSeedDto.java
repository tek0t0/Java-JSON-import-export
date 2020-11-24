package softuni.cardealer.domain.dtos.importDtos;

import com.google.gson.annotations.Expose;
import softuni.cardealer.domain.entities.Supplier;

public class SupplierSeedDto {

    @Expose
    private String name;

    @Expose
    private boolean isImporter;

    @Expose
    private Supplier supplier;

    public SupplierSeedDto() {
    }

    public SupplierSeedDto(String name, boolean isImporter, Supplier supplier) {
        this.name = name;
        this.isImporter = isImporter;
        this.supplier = supplier;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
