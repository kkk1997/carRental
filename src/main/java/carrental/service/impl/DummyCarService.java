package carrental.service.impl;

import carrental.dao.interf.CarRepository;
import carrental.dao.interf.LendRepository;
import carrental.domain.Car;
import carrental.service.interf.ICarService;
import carrental.service.interf.ILendService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DummyCarService extends DummyCarServiceControl implements ICarService {

    @Autowired
    private CarRepository carDAO;

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private ILendService lendService;

    public DummyCarService(CarRepository carDAO) {
        this.carDAO = carDAO;
    }

    @Override
    public Car createCar(String lpn, String type, int age, BigDecimal price) throws RuntimeException {
        if (!checkLicensePlate(lpn)) {
            createCarControl(lpn, type, age, price);
            Car car = new Car(lpn, type, price, age);
            carDAO.save(car);
            return car;
        }
        throw new IllegalArgumentException("Már van ilyen rendszámú autó!");
    }

    private boolean checkLicensePlate(String lpn) throws RuntimeException {
        return carDAO.findAll()
                .stream()
                .anyMatch(c -> c.getLicensePlateNumber().equals(lpn));
    }

    @Override
    public Car editCar(Car updateCar) throws RuntimeException {
        editCarControl(updateCar, "Nem sikerült az autó szerkesztése!");
        return carDAO.saveAndFlush(updateCar);
    }

    @Override
    public Car deleteCar(Car car) throws RuntimeException {
        editCarControl(car, "Nem sikerült az autó törlése!");
        deleteLend(car);
        carDAO.delete(car);
        return car;
    }

    private void deleteLend(Car car) {
        lendRepository.findAll()
                .stream()
                .filter(lend -> lend.getCar().getID().equals(car.getID()))
                .forEach(lend -> lendService.deleteLend(lend.getUser().getName(), lend.getStartDate()));
    }

    @Override
    public List<Car> listCar() {
        return carDAO.findAll();
    }

    @Override
    public List<Car> listCarOfType(String type) {
        return listCar().stream()
                .filter(car -> car.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public Car getCarOfLicensePlateNumber(String lpn) {
        return carDAO.findByLicensePlateNumber(lpn);
    }
}