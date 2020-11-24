package softuni.cardealer.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.cardealer.domain.entities.Customer;

import java.util.Set;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("SELECT c from Customer c order by c.birthDate, c.youngDriver")
    Set<Customer> getAllOrderByBirthDateAndYoungDriver();

    @Query("select c from Customer c where c.sales.size > 0")
    Set<Customer> findCustomersByCars();
}
