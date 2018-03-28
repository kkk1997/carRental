package carrental.presenter;

import carrental.domain.Car;
import carrental.domain.Lend;
import carrental.domain.Status;
import carrental.domain.User;
import carrental.service.LendVariablesNotValidException;
import carrental.service.interf.ILendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class LendPresenter {
    @Autowired
    private ILendService lendService;

    public List<Lend> listActualLend() { return lendService.listActualLend(); }

    public BigDecimal rentalPrice(Lend lend){
        return lendService.rentalPrice(lend);
    }

    public Lend createLend(User user, Car car, LocalDate startDate, LocalDate endDate) throws LendVariablesNotValidException{
        return lendService.createLend(user,car,startDate,endDate);
    }

    public Lend editLend(Lend updateLend){
        return lendService.editLend(updateLend);
    }

    public Lend deleteLend(String user,LocalDate startDate) throws LendVariablesNotValidException {
        return lendService.deleteLend(user,startDate);
    }

    public List<Lend> listLend(){
        return lendService.listLend();
    }

    public Lend getActualLendOfUserName(String name){
        return lendService.getActualLendOfUserName(name);
    }

    public Status updateLend(User user){
        return lendService.updateLend(user);
    }
}
