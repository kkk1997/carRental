package carrental.service.interf;

import carrental.domain.Car;

import java.math.BigDecimal;
import java.util.List;

public interface ICarService {
    /**
     * Új autó létrehozása
     * @param lpn autó rendszáma
     * @param type autó típusa
     * @param age autó évjárata
     * @param price autó napi kölcsönzési ára
     * @return Az új autó
     */
    Car createCar(String lpn, String type, int age, BigDecimal price);

    /**
     * Autó módosítása
     * @param updateCar Módosított autó
     * @return Módosított autó
     */
    Car editCar(Car updateCar);

    /**
     * Autó törlése
     * @param car Törölni kívánt autó
     * @return Törölt autó
     */
    Car deleteCar(Car car);

    /**
     * Autók listázása
     * @return Autók listája
     */
    List<Car> listCar();

    /**
     * Bizonyos tipusu autók listázása
     * @param type Keresett autók tipusa
     * @return Keresett autók
     */
    List<Car> listCarOfType(String type);

    /**
     * Rendszám alapján visszaadja az autót
     * @param lpn rendszám
     * @return A keresett autó
     */
    Car getCarOfLicensePlateNumber(String lpn);

    /**
     * Aktuális ajánlatok összegyűjtése (10 legolcsóbb aktuális autó)
     * @return Aktuális ajánlatok
     */
    List<Car> actualOffer();

}
