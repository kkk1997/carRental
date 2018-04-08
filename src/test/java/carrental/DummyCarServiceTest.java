package carrental.service.impl;

import carrental.dao.interf.CarRepository;
import java.util.List;

import carrental.service.interf.ICarService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.math.BigDecimal;
import carrental.domain.Car;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DummyCarServiceTest {

    private ICarService carService;
    private String type;
    private BigDecimal lendPrice;
    private int carAge;

    @Mock
    private CarRepository carDao;

    @Before
    public void setUp() {
        carService = new DummyCarService(carDao);
        type = "Suzuki";
        lendPrice = new BigDecimal(3000);
        carAge = 2000;
    }

    @Test
    public void newCarSuccessTest() {
        String lpn = "ABC-567";
        Car car = carService.createCar(lpn,type,carAge,lendPrice);

        Assert.assertEquals(car.getLicensePlateNumber(),lpn);
        Assert.assertEquals(car.getType(),type);
        Assert.assertEquals((long)car.getAge(),(long)carAge);
        Assert.assertEquals(car.getPrice(),lendPrice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newCarArgumentLpnFailTest() {
        String wrongLpn = "DEF468";

        carService.createCar(wrongLpn,type,carAge,lendPrice);

        Assert.fail("Hiba, rossz rendszámra is létrehozta az autót!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void newCarArgumentTypeFailTest() {
        String lpn = "KFH-221";
        String wrongType = "";
        carService.createCar(lpn,wrongType,carAge,lendPrice);

        Assert.fail("Hiba, üres típusra is létrehozta az autót!");
    }

    @Test
    public void editCarSuccessTest() {
        String lpn = "ABC-567";
        carService.createCar(lpn,type,carAge,lendPrice);
        String newLpn = "JHV-254";
        String newType = "Audi";
        int newAge = 2005;
        BigDecimal newPrice = new BigDecimal(2500);

        Car updateLpn = carService.createCar(newLpn,type,carAge,lendPrice);
        Car updateType = carService.createCar(lpn,newType,carAge,lendPrice);
        Car updateCarAge = carService.createCar(lpn,type,newAge,lendPrice);
        Car updateLendPrice = carService.createCar(lpn,type,carAge,newPrice);

        Mockito.when(carService.editCar(updateLpn)).thenReturn(updateLpn);
        Mockito.when(carService.editCar(updateType)).thenReturn(updateType);
        Mockito.when(carService.editCar(updateCarAge)).thenReturn(updateCarAge);
        Mockito.when(carService.editCar(updateLendPrice)).thenReturn(updateLendPrice);

        Assert.assertEquals(newLpn, updateLpn.getLicensePlateNumber());
        Assert.assertEquals(newType, updateType.getType());
        Assert.assertEquals((long)newAge, (long)updateCarAge.getAge());
        Assert.assertEquals(newPrice, updateLendPrice.getPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void editCarNullTest() {
        carService.editCar(null);

        Assert.fail("Hiba, null paraméterrel is tud autót szerkeszteni!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void editCarArgumentLpnFailTest() {
        String lpn = "ABC-567";
        Car updateLpn = new Car("",type,lendPrice,carAge);
        Mockito.when(carService.editCar(updateLpn)).thenReturn(updateLpn);

        Assert.fail("Hiba, az autó amit kapott tartalmaz üres rendszámot!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void editCarArgumentTypeFailTest() {
        String lpn = "KXB-982";
        Car updateType = new Car(lpn,"",lendPrice,carAge);
        Mockito.when(carService.editCar(updateType)).thenReturn(updateType);

        Assert.fail("Hiba, az autó amit kapott tartalmaz üres típust!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void editCarArgumentPriceFailTest() {
        String lpn = "JCB-543";
        BigDecimal wrongPrice = new BigDecimal(0);
        Car updatePrice = new Car(lpn, type, wrongPrice, carAge);
        Mockito.when(carService.editCar(updatePrice)).thenReturn(updatePrice);

        Assert.fail("Hiba, az autó amit kapott túl alacsony árú!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void editCarArgumentAgeFailTest() {
        String lpn = "NSK-552";
        int wrongAge = 1600;
        Car updateAge = new Car(lpn, type, lendPrice,wrongAge);
        Mockito.when(carService.editCar(updateAge)).thenReturn(updateAge);

        Assert.fail("Hiba, az autó amit kapott negatív évben készült!");
    }

    @Test
    public void deleteCarSuccessTest() {
        Car car = carService.createCar("ABC-567",type,carAge,lendPrice);

        carService.deleteCar(car);
        List<Car> cars = carService.listCar();
        for(Car c : cars){
            if(c.equals(car))
                Assert.fail("Hiba, nem törölte az autót!");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCarNullTest() {
        carService.deleteCar(null);

        Assert.fail("Hiba, lehet autót törölni null paraméterrel!");
    }

    @Test
    public void actualOfferTest(){
        BigDecimal badLendPrice = new BigDecimal(4000);

        carService.createCar("HGC-555",type,carAge,lendPrice);
        carService.createCar("BDJ-323",type,carAge,lendPrice);
        carService.createCar("KNS-837",type,carAge,lendPrice);
        carService.createCar("LDG-445",type,carAge,lendPrice);
        carService.createCar("BCJ-258",type,carAge,lendPrice);
        carService.createCar("LSN-843",type,carAge,lendPrice);
        carService.createCar("NCG-964",type,carAge,lendPrice);
        carService.createCar("SXM-813",type,carAge,lendPrice);
        carService.createCar("JCX-125",type,carAge,lendPrice);
        carService.createCar("NXI-791",type,carAge,lendPrice);
        carService.createCar("KSH-635",type,carAge,badLendPrice);

        List<Car> cheapCars= carService.actualOffer();

        for(Car car : cheapCars){
            if(car.getPrice().equals(badLendPrice))
                Assert.fail("Hiba, nem a 10 legolcsóbbat adta vissza!");
        }
    }
}