package carrental.service.impl;

import carrental.dao.interf.LendRepository;
import carrental.domain.Car;
import carrental.domain.Lend;
import carrental.domain.User;
import carrental.service.LendVariablesNotValidException;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class DummyLendServiceControl {
    private LendRepository lendDAO;

    protected void setLendDAO(LendRepository lendDAO){
        this.lendDAO = lendDAO;
    }

    protected Lend isUserLendControl(String user) {
        Lend lend = lendDAO.findAll().stream()
                                     .filter(l -> l.getUser().getName().equals(user))
                                     .findFirst()
                                     .get();
        if(lend == null){
            throw new LendVariablesNotValidException("Nincs ilyen kölcsönzés!");
        }
        return lend;
    }

    protected void rentalPriceControl(Lend lend) {
        if(lend == null || lend.getUser() == null || lend.getCar() == null) {
            throw new IllegalArgumentException("Nem lehetnek üresek a mezők!");
        }
    }

    protected void lendControl(User user, Car car, LocalDate startDate, LocalDate endDate) {
        if (user == null || car == null || endDate == null || startDate == null) {
            throw new IllegalArgumentException("Nem lehetnek üresek a mezők!");
        }
        if(startDate.isAfter(endDate)){
            throw new LendVariablesNotValidException("Rosszul adta meg a dátumot!");
        }
        if(user.getDriverLicenseExp().compareTo(LocalDate.now()) < 0){
            throw new LendVariablesNotValidException("user.service.control.expireLicense = Lejárt a jogsítvány!");
        }
    }

    protected void editLendControl(Lend updateLend) {
        if(updateLend == null || !isValidLend(updateLend)){
            throw new IllegalArgumentException("Rosszul adta meg az adatokat!");
        }
    }

    protected Lend deleteLendControl(String user, LocalDate startDate) {
        if(StringUtils.isEmpty(user)|| startDate == null){
            throw new IllegalArgumentException("Nem lehetnek üresek a mezők!");
        }
        Lend deleteLend = lendDAO.findAll().stream()
                                 .filter(lend -> lend.getUser().getName().equals(user))
                                 .findFirst()
                                 .get();
        if(deleteLend == null){
            throw new LendVariablesNotValidException("Nincs ilyen kölcsönzés!");
        }
        return deleteLend;
    }

    protected Lend deleteLendControl(User user) {
        if(user == null){
            throw new IllegalArgumentException("Nem lehetnek üresek a mezők!");
        }
        Lend deleteLend = lendDAO.findAll().stream()
                .filter(lend -> lend.getUser().equals(user))
                .findFirst().orElse(null);
        return deleteLend;
    }

    protected void daysCalcControl(LocalDate startDate, LocalDate endDate) {
        if(startDate == null || endDate == null){
            throw new LendVariablesNotValidException("Rosszul adta meg a dátumot!");
        }
    }

    private boolean isValidLend(Lend lend){
        return (lend.getUser() != null && lend.getCar() != null) &&
                lend.getStartDate().isBefore(lend.getEndDate());
    }
}
