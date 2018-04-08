package carrental.service.interf;

import carrental.domain.Lend;
import carrental.domain.Role;
import carrental.domain.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    /**
     * Felhasználó szerkesztése
     * @param updateUser Módosított felhasználó
     * @return Módosított felhasználó
     */
    User editUser(User updateUser);

    /**
     * Kölcsönzés fizetése részletekben
     * @param price Befizetni kívánt összeg
     * @param lend az aktuális kölcsönzés
     */
    BigDecimal payDetail(BigDecimal price, Lend lend);
    /**
     * Felhasználónév és jelszó ellenőrzése
     * @param account Felhasználónév
     * @param password Felhasználó jelszava
     * @return true A felhasználónév és a jelszó megfelelő
     */
    boolean controlPassword(String email, String password);

    /**
     * Számlára pénzfeltöltés
     * @param user Felhasználó, akihez érkezik a feltöltés
     * @param price Összeg, amely feltöltésre kerül
     * @return Felhasználó aktuális egyenlege
     */
    BigDecimal addToAccount(User user, BigDecimal price);

    /**
     * Kölcsönzés kifizetése
     * @param lend Kölcsönzés, ami kifizetésre kerülne
     */
    void pay(Lend lend);

    /**
     * Új felhasználó létrehozása
     * @param name Létrehozni kívánt felhasználó neve
     * @param address Létrehozni kívánt felhasználó címe
     * @param account Létrehozni kívánt felhasználó felhasználóneve
     * @param pw Létrehozni kívánt felhasználó jelszava
     * @return Az új felhasználó
     */
    User createUser(String name, String address, String email, String pw,LocalDate driverLicenseExp,String accountNumber);

    User createUser(String name, String address, String email, String pw,LocalDate driverLicenseExp,String accountNumber, Role role);

    /**
     * Új felhasználó létrehozása
     * @param user Létrehozni kívánt felhasználó
     * @return Az új felhasználó
     */
    User createUser(User user);
    /**
     * Felhasználó törlése
     * @param name Felhasználó, aki törlésre kerül
     * @return Törölt felhasználó
     */
    User deleteUser(String name);

    /**
     * Felhasználó törlése
     * @param user Felhasználó, aki törlésre kerül
     * @return Törölt felhasználó
     */
    User deleteUser(User user);

    /**
     * Felhasználók listázása
     * @return Felhasználók listája
     */
    List<User> listUser();

    /**
     * Bejelentkezés
     * @param username Bejelentkező felhasználó neve
     * @param password Bejelentkező felhasználó jelszava
     * @return A bejelentkezett felhasználó
     */
    User login(String username, String password);

    /**
     * Név alapján visszaadja a User-t
     * @param name Visszaadott User neve
     * @return Keresett User
     */
    List<User> getUserOfName(String name);

    /**
     * E-mail alapján visszaadja a Usert
     * @param email Keresett User e-mail címe
     * @return Keresett User
     */
    User getUserOfEmail(String email);


}
