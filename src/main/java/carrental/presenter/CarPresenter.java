package carrental.presenter;


import carrental.domain.Car;
import carrental.service.interf.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarPresenter {
    @Autowired
    private ICarService carService;

    public Car createCar(Car car){
        return carService.createCar(car.getLicensePlateNumber(),car.getType(),car.getAge(),car.getPrice());
    }

    public Car editCar(Car updateCar){
        return carService.editCar(updateCar);
    }

    public Car deleteCar(Car car){
        return carService.deleteCar(car);
    }

    public List<Car> listCar(){
        return carService.listCar();
    }

    public Car getCarOfLicensePlateNumber(String lpn){
        return carService.getCarOfLicensePlateNumber(lpn);
    }

    public List<Car> actualOffer(){
        return carService.actualOffer();
    }
}
