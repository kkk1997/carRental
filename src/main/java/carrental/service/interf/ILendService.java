package carrental.service.interf;

import carrental.domain.Lend;
import carrental.domain.Status;
import carrental.domain.User;
import carrental.domain.Car;
import carrental.service.LendVariablesNotValidException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ILendService {
    /**
     * Lezárt kölcsönzések listázása
     * @return Lezárt kölcsönzések listája
     */
    List listExpiredLend();

    /**
     * Aktuális kölcsönzések listázása
     * @return Aktuális kölcsönzések
     */
    List<Lend> listActualLend();

    /**
     * Kölcsönzés lezárása
     * @param lend Kölcsönzés amit le akar zárni
     * @return Lezárt kölcsönzés
     * @throws LendVariablesNotValidException - Nem aktuális a kölcsönzés
     */

    Lend expireLend(Lend lend)throws LendVariablesNotValidException;

    /**
     * Kölcsönzés lezárása
     * @param user Felhasználó, akinek a kölcsönzését le akarja zárni
     * @return Lezárt kölcsönzés
     */
    Lend expireLendByUser(User user);

    /**
     * A felhasználónak van-e aktuális kölcsönzése
     * @param user Felhasználó neve
     * @return true ha van aktuális kölcsönzése
     * @throws LendVariablesNotValidException - Nincs ilyen kölcsönzés
     */
    boolean isUserLend(String user) throws LendVariablesNotValidException;

    /**
     * Kölcsönzés árának lekérdezése
     * @param lend Kölcsönzés, amelynek az árát lekérdezi
     * @return Kölcsönzés ára
     */
    BigDecimal rentalPrice(Lend lend);

    /**
     * Lejárt kölcsönzés árának lekérdezése
     * @param lend Kölcsönzés, amelynek az árát kiszámolja
     * @return Lejárt kölcsönzés ára
     */
    BigDecimal expiredLendPriceCalc(Lend lend);

    /**
     * Új kölcsönzés létrehozása
     * @param user Felhasználó, akihez tartozik a kölcsönzés
     * @param car Autó, amihez tartozik a kölcsönzés
     * @param startDate Kölcsönzés kezdő dátuma
     * @param endDate Kölcsönzés lezárásának dátuma
     * @return Új kölcsönzés
     * @throws LendVariablesNotValidException Foglalt az autó
     */
    Lend createLend(User user, Car car, LocalDate startDate, LocalDate endDate) throws LendVariablesNotValidException;

    /**
     *  Új kölcsönzés létrehozása
     * @param user Felhasználó, akihez tartozik a kölcsönzés
     * @param car Autó, amihez tartozik a kölcsönzés
     * @param endDate Kölcsönzés lezárásának dátuma
     * @return Új kölcsönzés
     * @throws LendVariablesNotValidException Foglalt az autó
     */
    default Lend createLend(User user, Car car, LocalDate endDate) throws LendVariablesNotValidException{
        return createLend(user, car, LocalDate.now(), endDate);
    }

    /**
     * Kölcsönzés szerkesztése
     * @param updateLend Módosított kölcsönzés
     * @return Módosított kölcsönzés
     */
    Lend editLend(Lend updateLend);

    /**
     * Kölcsönzés törlése
     * @param user Felhasználó, akinek törli a kölcsönzését
     * @param startDate Kölcsönzés kezdődátuma, aminek törli a kölcsönzését
     * @return Törölt kölcsönzés
     * @throws  LendVariablesNotValidException Nincs ilyen kölcsönzés
     */
    Lend deleteLend(String user,LocalDate startDate) throws LendVariablesNotValidException;

    /**
     * Kölcsönzés törlése
     * @param user Felhasználó, akinek törli a kölcsönzését
     * @return Törölt kölcsönzés
     * @throws  LendVariablesNotValidException Nincs ilyen kölcsönzés
     */
    Lend deleteLend(User user) throws LendVariablesNotValidException;

    /**
     * Kölcsönzések listázása
     * @return Kölcsönzések listája
     */
    List<Lend> listLend();

    /**
     * Két dátum között eltelt idő napokban
     * @param startDate Kezdődátum
     * @param endDate Végdátum
     * @return Két dátum közötti eltelt napok
     */
    int daysCalc(LocalDate startDate, LocalDate endDate);

    /**
     * Kölcsönzés keresése tulajdonos alapján
     * @param name keresett felhasználó neve
     * @return Keresett kölcsönzés
     */
    Lend getLendOfUserName(String name);

    /**
     * Aktuális kölcsönzés keresése tulajdonos alapján
     * @param name keresett felhasználó neve
     * @return  Keresett kölcsönzés
     */
    Lend getActualLendOfUserName(String name);

    /**
     * Kölcsönzés ellenőrzése, e-mail küldése ha lejárt vagy le fog járni a kölcsönzés
     */
    Status updateLend(User user);

    /**
     * Egy ügyfél adatainak szöveges kilistázása, kölcsönzés lezárása esetén
     * @param user ügyfél, akinek az adatait kiírja
     * @return Ügyfél adatainak szöveges kimenete
     */
    String getUserData(User user);
}
