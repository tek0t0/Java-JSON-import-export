package softuni.cardealer.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.cardealer.domain.entities.Sale;

import java.util.Set;

@Repository
public interface SalesRepo extends JpaRepository<Sale, Long> {
    Set<Sale> getAllBy();
}
