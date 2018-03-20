package carrental.dao.interf;

import carrental.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    Car findByLicensePlateNumber(String licensePlateNumber);
}