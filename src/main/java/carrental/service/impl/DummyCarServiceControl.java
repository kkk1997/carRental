package carrental.service.impl;

import carrental.domain.Car;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class DummyCarServiceControl {
    protected void editCarControl(Car updateCar, String s) {
        if (updateCar == null || !isValidCar(updateCar)) {
            throw new IllegalArgumentException(s);
        }
    }

    protected void createCarControl(String lpn, String type, int age, BigDecimal price) {
        if(StringUtils.isEmpty(lpn) || StringUtils.isEmpty(type) || age == 0 || price.equals(BigDecimal.ZERO)){
            throw new IllegalArgumentException("Nem lehetnek üresek a mezők!");
        }else if(!lpn.matches("^[A-Za-z]{3}-[0-9]{3}$")){
            throw new IllegalArgumentException("Nem megfelelő a rendszám!");
        }else if(age < 1970 || age > 2017){
            throw new IllegalArgumentException("Nem megfelelő az évjárat!");
        }else if(price.compareTo(new BigDecimal(1000)) < 0 ||price.compareTo(new BigDecimal(20000)) > 0 ){
            throw new IllegalArgumentException("Nem megfelelő az ár!");
        }
    }

    private boolean isValidCar(Car car){
        return ( !StringUtils.isEmpty(car.getType())
                && !StringUtils.isEmpty(car.getLicensePlateNumber()) && car.getPrice().compareTo(BigDecimal.ZERO) > 0);
    }
}
