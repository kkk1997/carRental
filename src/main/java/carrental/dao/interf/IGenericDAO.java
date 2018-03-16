package carrental.dao.interf;

import java.util.List;


public interface IGenericDAO<T> {
    /**
     * Objektumok listázása
     * @return adott Objektumok listája
     */
    List<T> list();

    /**
     * Új objektumok hozzáadása
     * @param object Az objektum, amelyet létrehoz
     * @return A hozzáadott objektum
     */
    T create(T object);

    /**
     * Objektum törlése
     * @param object Az objektum, amit töröl
     * @return A törölt objektum
     */
    T delete(T object);

    /**
     * Objektum frissítése
     * @param update A frissített objektum
     * @return A frissített objektum
     */
    T update(T update);

    /**
     * Objektum keresése név alapján
     * @param name Az objektum neve, amit keres
     * @return A keresett objektum
     */
    T get(String name);
}
